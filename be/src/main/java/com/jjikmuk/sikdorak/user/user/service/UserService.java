package com.jjikmuk.sikdorak.user.user.service;

import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.controller.response.FollowUserProfile;
import com.jjikmuk.sikdorak.user.user.controller.response.UserDetailProfileResponse;
import com.jjikmuk.sikdorak.user.user.controller.response.UserProfileRelationStatusResponse;
import com.jjikmuk.sikdorak.user.user.controller.response.UserSimpleProfileResponse;
import com.jjikmuk.sikdorak.user.user.domain.RelationType;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateFollowingException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateSendAcceptUserException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundFollowException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<UserSimpleProfileResponse> searchUsersByNickname(String nickname) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            return new ArrayList<>();
        }

        List<User> users = userRepository.findAllByNickname(nickname);

        return users.stream()
            .map(UserSimpleProfileResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewDetailResponse> searchUserReviewsByUserIdAndRelationType(Long searchUserId, LoginUser loginUser) {
        log.debug("searchByUserReviews: searchUserId={}, loginUser.id={}, loginUser.authority={}", searchUserId, loginUser.getId(), loginUser.getAuthority());

        User searchUser = userRepository.findById(searchUserId)
            .orElseThrow(NotFoundUserException::new);

        List<Review> userReviews = findUserReviews(loginUser, searchUser);

        return getReviewsResponse(userReviews);
    }

    @Transactional(readOnly = true)
    public UserSimpleProfileResponse searchSelfProfile(LoginUser loginUser) {

        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        return UserSimpleProfileResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserDetailProfileResponse searchUserDetailProfile(Long userId, LoginUser loginUser) {
        User searchUser = userRepository.findById(userId)
            .orElseThrow(NotFoundUserException::new);
        RelationType relationType = searchUser.relationTypeTo(loginUser);
        int reviewCount = reviewRepository.countByUserId(searchUser.getId());
        int followingCount = userRepository.findFollowingsByUserId(userId).size();
        int followerCount = userRepository.findFollowersByUserId(userId).size();


        return UserDetailProfileResponse.of(
            searchUser,
            UserProfileRelationStatusResponse.of(relationType),
            followingCount,
            followerCount,
            reviewCount
        );
    }

    @Transactional
    public long createUser(User user) {
        if (isExistingByUniqueId(user.getUniqueId())) {
            throw new DuplicateUserException();
        }
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long modifyUser(LoginUser loginUser, UserModifyRequest userModifyRequest) {
        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        user.editAll(
            userModifyRequest.getNickname(),
            userModifyRequest.getEmail(),
            userModifyRequest.getProfileImage());

        return user.getId();
    }

    @Transactional
    public void followUser(LoginUser loginUser, UserFollowAndUnfollowRequest userFollowAndUnfollowRequest) {
        User sendUser = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);
        User acceptUser = userRepository.findById(userFollowAndUnfollowRequest.getUserId())
            .orElseThrow(NotFoundUserException::new);

        validateFollowUsers(sendUser, acceptUser);

        sendUser.follow(acceptUser);
    }


    @Transactional
    public void unfollowUser(LoginUser loginUser, UserFollowAndUnfollowRequest userFollowAndUnfollowRequest) {
        User sendUser = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);
        User acceptUser = userRepository.findById(userFollowAndUnfollowRequest.getUserId())
            .orElseThrow(NotFoundUserException::new);

        validateUnfollowUsers(sendUser, acceptUser);

        sendUser.unfollow(acceptUser);
    }

    @Transactional
    public void deleteUser(LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);
        List<Review> reviews = reviewRepository.findByUserId(user.getId());

        reviews.forEach(Review::delete);
        user.delete();
    }

    @Transactional(readOnly = true)
    public List<FollowUserProfile> searchFollowersByUserId(long targetUserId, LoginUser loginUser) {
        User targetUser = userRepository.findById(targetUserId)
            .orElseThrow(NotFoundUserException::new);

        List<User> followers = userRepository.findFollowersByUserId(targetUser.getId());

        if(followers.isEmpty()) return new ArrayList<>();

        return followers.stream()
            .map(user -> FollowUserProfile.of(
                UserSimpleProfileResponse.from(user),
                UserProfileRelationStatusResponse.of(user.relationTypeTo(loginUser))))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<FollowUserProfile> searchFollowingsByUserId(long targetUserId, LoginUser loginUser) {
        User targetUser = userRepository.findById(targetUserId)
            .orElseThrow(NotFoundUserException::new);

        List<User> followings = userRepository.findFollowingsByUserId(targetUser.getId());

        if(followings.isEmpty()) return new ArrayList<>();

        return followings.stream()
            .map(user -> FollowUserProfile.of(
                UserSimpleProfileResponse.from(user),
                UserProfileRelationStatusResponse.of(user.relationTypeTo(loginUser))))
            .toList();
    }

    @Transactional(readOnly = true)
    public User searchById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new NotFoundUserException();
        }

        return userRepository.findById(userId)
            .orElseThrow(NotFoundUserException::new);
    }

    @Transactional(readOnly = true)
    public User searchByUniqueId(long uniqueId) {
        return userRepository.findByUniqueId(uniqueId)
            .orElseThrow(NotFoundUserException::new);
    }

    @Transactional(readOnly = true)
    public boolean isExistingById(long userId) {
        return userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public boolean isExistingByUniqueId(long userUniqueId) {
        return userRepository.existsByUniqueId(userUniqueId);
    }


    private void validateFollowUsers(User sendUser, User acceptUser) {
        validateSendAndAcceptUser(sendUser, acceptUser);

        if (sendUser.isFollowing(acceptUser)) {
            throw new DuplicateFollowingException();
        }
    }

    private void validateUnfollowUsers(User sendUser, User acceptUser) {
        validateSendAndAcceptUser(sendUser, acceptUser);

        if (!sendUser.isFollowing(acceptUser)) {
            throw new NotFoundFollowException();
        }
    }

    private void validateSendAndAcceptUser(User sendUser, User acceptUser) {
        if (sendUser.equals(acceptUser)) {
            throw new DuplicateSendAcceptUserException();
        }
    }

    private List<Review> findUserReviews(LoginUser loginUser, User searchUser) {
        return switch (searchUser.relationTypeTo(loginUser)) {
            case SELF -> reviewRepository.findByUserId(searchUser.getId());
            case CONNECTION -> reviewRepository.findByUserIdAndConnection(searchUser.getId());
            case DISCONNECTION -> reviewRepository.findByUserIdAndDisconnection(searchUser.getId());
        };
    }

    private Map<Long, Store> getReviewStores(List<Long> storeIds) {
        return storeRepository.findAllById(storeIds).stream()
            .collect(Collectors.toMap(Store::getId, Function.identity()));
    }

    private Map<Long, User> getReviewAuthors(List<Long> authorIds) {
        return userRepository.findAllById(authorIds).stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    private List<Long> getStoreIds(List<Review> reviews) {
        return reviews.stream()
            .map(Review::getStoreId)
            .toList();
    }

    private List<Long> getAuthorIds(List<Review> reviews) {
        return reviews.stream()
            .map(Review::getUserId)
            .toList();
    }

    private List<ReviewDetailResponse> getReviewsResponse(List<Review> reviews) {
        Map<Long, User> authors = getReviewAuthors(getAuthorIds(reviews));
        Map<Long, Store> stores = getReviewStores(getStoreIds(reviews));

        if (reviews.isEmpty()) {
            return new ArrayList<>();
        }

        return reviews.stream()
            .map(review -> ReviewDetailResponse.of(review,
                stores.get(review.getStoreId()),
                authors.get(review.getUserId())))
            .toList();
    }
}
