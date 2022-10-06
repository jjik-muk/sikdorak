package com.jjikmuk.sikdorak.tool;

import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataConfigurator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public TUser createTUser(Long uniqueId, String nickname, String profileImage, String email) {
        return new TUser(uniqueId, nickname, profileImage, email);
    }

    public class TUser {

        User user;
        String accessToken;
        String refreshToken;

        public TUser(Long uniqueId, String nickname, String profileImage, String email) {
            this.user = userRepository.save(new User(uniqueId, nickname, profileImage, email));
            this.accessToken = jwtProvider.createAccessToken(String.valueOf(user.getId()),
                new Date(new Date().getTime() + 1800000));
            this.refreshToken = jwtProvider.createRefreshToken(String.valueOf(user.getId()),
                new Date(new Date().getTime() + 8000000));
        }

        public void follow(TUser acceptUser) {
            this.user.follow(acceptUser.user);
            userRepository.save(this.user);
            userRepository.save(acceptUser.user);
        }

        public Review publishReview(long storeId, String reviewContent, float reviewScore,
            String reviewVisibility, LocalDate date, List<String> tags, List<String> images) {
            return reviewRepository.save(new Review(this.user.getId(),
                storeId,
                reviewContent,
                reviewScore,
                reviewVisibility,
                date,
                tags,
                images));
        }


        public User getUser() {
            return user;
        }

        public String getAuthorizationHeader() {
            return "Bearer " + accessToken;
        }

        public long getId() {
            return this.user.getId().intValue();
        }

        public long getUniqueId() {
            return this.user.getUniqueId();
        }

        public String getNickname() {
            return this.user.getNickname();
        }

        public String getProfileImage() {
            return this.user.getProfileImage();
        }

        public String getEmail() {
            return this.user.getEmail();
        }

        public Set<Long> getFollowers() {
            return this.user.getFollowers();
        }

        public Set<Long> getFollowings() {
            return this.user.getFollowings();
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getExpiredRefreshToken() {
            return jwtProvider.createRefreshToken(String.valueOf(user.getId()),
                new Date(new Date().getTime() - 1000));
        }
        public String getInvalidRefreshToken() {
            return refreshToken+"invalidToken";
        }



        public boolean isFollowing(TUser tUser) {
            return this.user.isFollowing(tUser.user);
        }
    }
}
