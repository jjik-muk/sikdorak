package com.jjikmuk.sikdorak.user.user.command.app;

import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.app.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.command.app.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateFollowingException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateSendAcceptUserException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundFollowException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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
    public User searchByUniqueId(String uniqueId) {
        return userRepository.findByUniqueId(uniqueId)
            .orElseThrow(NotFoundUserException::new);
    }

    @Transactional(readOnly = true)
    public boolean isExistingById(long userId) {
        return userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public boolean isExistingByUniqueId(String userUniqueId) {
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

}
