package com.jjikmuk.sikdorak.user.user.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "unique_id")
    private Long uniqueId;

    @Embedded
    private Nickname nickname;

    @Embedded
    private ProfileImage profileImage;

    @Embedded
    private Email email;

    @Embedded
    private Followings followings;

    @Embedded
    private Followers followers;

    public User(Long uniqueId, String nickname, String profileImage, String email) {
        this(null, uniqueId, nickname, profileImage, email);
    }

    public User(Long id, Long uniqueId, String nickname, String profileImage, String email) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.nickname = new Nickname(nickname);
        this.profileImage = new ProfileImage(profileImage);
        this.email = new Email(email);
        this.followings = new Followings();
        this.followers = new Followers();
    }

    public Long getId() {
        return id;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getProfileImage() {
        return profileImage.getProfileImageUrl();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public Set<Long> getFollowings() {
        return followings.getFollowing();
    }

    public Set<Long> getFollowers() {
        return followers.getFollower();
    }

    public void editAll(String nickname, String email, String profileImage) {
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
        this.profileImage = new ProfileImage(profileImage);
    }

    public void follow(User acceptUser) {
        acceptUser.addFollower(this);
        this.addFollowing(acceptUser);
    }

    public void unfollow(User acceptUser) {
        acceptUser.removeFollower(this);
        this.removeFollowing(acceptUser);
    }

    public boolean isFollowing(User acceptUser) {
        return this.getFollowings().contains(acceptUser.getId());
    }

    private void addFollower(User sendUser) {
        this.getFollowers().add(sendUser.id);
    }

    private void addFollowing(User acceptUser) {
        this.getFollowings().add(acceptUser.id);
    }

    private void removeFollower(User sendUser) {
        this.getFollowers().remove(sendUser.getId());
    }

    private void removeFollowing(User acceptUser) {
        this.getFollowings().remove(acceptUser.getId());
    }

}
