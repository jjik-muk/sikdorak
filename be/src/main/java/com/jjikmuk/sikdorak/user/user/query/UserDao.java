package com.jjikmuk.sikdorak.user.user.query;

import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.RelationType;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.query.response.FollowUserProfile;
import com.jjikmuk.sikdorak.user.user.query.response.UserDetailProfileResponse;
import com.jjikmuk.sikdorak.user.user.query.response.UserProfileRelationStatusResponse;
import com.jjikmuk.sikdorak.user.user.query.response.UserSimpleProfileResponse;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDao {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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

    @Transactional(readOnly = true)
    public List<FollowUserProfile> searchFollowersByUserId(long targetUserId, LoginUser loginUser) {
        User targetUser = userRepository.findById(targetUserId)
            .orElseThrow(NotFoundUserException::new);

        List<User> followers = userRepository.findFollowersByUserId(targetUser.getId());

        if (followers.isEmpty()) {
            return new ArrayList<>();
        }

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

        if (followings.isEmpty()) {
            return new ArrayList<>();
        }

        return followings.stream()
            .map(user -> FollowUserProfile.of(
                UserSimpleProfileResponse.from(user),
                UserProfileRelationStatusResponse.of(user.relationTypeTo(loginUser))))
            .toList();
    }

}
