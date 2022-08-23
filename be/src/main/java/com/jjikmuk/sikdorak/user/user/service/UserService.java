package com.jjikmuk.sikdorak.user.user.service;

import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.controller.response.UserProfileResponse;
import com.jjikmuk.sikdorak.user.user.controller.response.UserReviewResponse;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateFollowingException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateSendAcceptUserException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundFollowException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRespository userRespository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<UserReviewResponse> searchUserReviewsByUserIdAndRelationType(Long searchUserId, LoginUser loginUser) {
        log.debug("searchByUserReviews: searchUserId={}, loginUser.id={}, loginUser.authority={}", searchUserId, loginUser.getId(), loginUser.getAuthority());

        User searchUser = userRespository.findById(searchUserId)
            .orElseThrow(NotFoundUserException::new);

        return switch (searchUser.relationTypeTo(loginUser)) {
            case SELF -> reviewRepository.findByUserId(searchUserId)
                .stream()
                .map(UserReviewResponse::from)
                .toList();
            case CONNECTION -> reviewRepository.findByUserIdAndConnection(searchUserId)
                .stream()
                .map(UserReviewResponse::from)
                .toList();
            case DISCONNECTION -> reviewRepository.findByUserIdAndDisconnection(searchUserId)
                .stream()
                .map(UserReviewResponse::from)
                .toList();
        };
    }

    @Transactional(readOnly = true)
    public UserProfileResponse searchUserProfile(Long userId, LoginUser loginUser) {

        User searchUser = userRespository.findById(userId)
            .orElseThrow(NotFoundUserException::new);
        int reviewCount = reviewRepository.countByUserId(searchUser.getId());
        boolean isViewer = false, followStatus = false;

        switch (searchUser.relationTypeTo(loginUser)) {
            case SELF -> isViewer = true;
            case CONNECTION ->  followStatus = true;
            case DISCONNECTION -> {}
        }

        return new UserProfileResponse(
            searchUser.getId(),
            searchUser.getNickname(),
            searchUser.getProfileImage(),
            searchUser.getEmail(),
            isViewer,
            followStatus,
            searchUser.getFollowers().size(),
            searchUser.getFollowings().size(),
            reviewCount
        );
    }

    @Transactional
    public long createUser(User user) {

        if (isExistingByUniqueId(user.getUniqueId())) {
            throw new DuplicateUserException();
        }
        userRespository.save(user);
        return user.getId();
    }

    @Transactional
    public Long modifyUser(LoginUser loginUser, UserModifyRequest userModifyRequest) {
        User user = userRespository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        user.editAll(
            userModifyRequest.getNickname(),
            userModifyRequest.getEmail(),
            userModifyRequest.getProfileImage());

        return user.getId();
    }

    @Transactional
    public void followUser(LoginUser loginUser, UserFollowAndUnfollowRequest userFollowAndUnfollowRequest) {

        User sendUser = userRespository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);
        User acceptUser = userRespository.findById(userFollowAndUnfollowRequest.getUserId())
            .orElseThrow(NotFoundUserException::new);

        validateFollowUsers(sendUser, acceptUser);

        sendUser.follow(acceptUser);
    }


    @Transactional
    public void unfollowUser(LoginUser loginUser, UserFollowAndUnfollowRequest userFollowAndUnfollowRequest) {

        User sendUser = userRespository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);
        User acceptUser = userRespository.findById(userFollowAndUnfollowRequest.getUserId())
            .orElseThrow(NotFoundUserException::new);

        validateUnfollowUsers(sendUser, acceptUser);

        sendUser.unfollow(acceptUser);
    }

    @Transactional(readOnly = true)
    public User searchById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new NotFoundUserException();
        }

        return userRespository.findById(userId)
            .orElseThrow(NotFoundUserException::new);
    }

    @Transactional(readOnly = true)
    public User searchByUniqueId(long uniqueId) {
        return userRespository.findByUniqueId(uniqueId)
            .orElseThrow(NotFoundUserException::new);
    }

    @Transactional(readOnly = true)
    public boolean isExistingById(long userId) {
        return userRespository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public boolean isExistingByUniqueId(long userUniqueId) {
        return userRespository.existsByUniqueId(userUniqueId);
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
}
