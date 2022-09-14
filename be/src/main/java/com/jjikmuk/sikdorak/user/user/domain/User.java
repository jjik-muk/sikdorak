package com.jjikmuk.sikdorak.user.user.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.exception.InvalidAuthorityException;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@NoArgsConstructor
@SQLDelete(sql = "update user set deleted = true where user_id = ?")
@Where(clause = "deleted = false")
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

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private boolean deleted = false;

    public User(Long uniqueId, String nickname, String profileImage, String email) {
        this(null, uniqueId, nickname, profileImage, email);
    }

    public User(Long uniqueId, String nickname, String profileImage, String email, Authority authority) {
        this(null, uniqueId, nickname, profileImage, email, authority);
    }

    public User(Long id, Long uniqueId, String nickname, String profileImage, String email) {
        this(id, uniqueId, nickname, profileImage, email, Authority.USER);
    }

    /*
        검증 로직이 여기 있기 때문에 다른 생성자를 추가할 경우
        최종적으로 이 생성자를 호출하도록 유도 해야함
     */
    public User(Long id, Long uniqueId, String nickname, String profileImage, String email, Authority authority) {
        validateAuthority(authority);

        this.id = id;
        this.uniqueId = uniqueId;
        this.nickname = new Nickname(nickname);
        this.profileImage = new ProfileImage(profileImage);
        this.email = Objects.isNull(email) ? new Email() : new Email(email);
        this.followings = new Followings();
        this.followers = new Followers();
        this.authority = authority;
    }

    private void validateAuthority(Authority authority) {
        if (authority.equals(Authority.ANONYMOUS)) {
            throw new InvalidAuthorityException();
        }
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

    public Authority getAuthority() {
        return this.authority;
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

    public void delete() {
        this.deleted = true;
    }

    public boolean isFollowing(User acceptUser) {
        return this.getFollowings().contains(acceptUser.id) && acceptUser.getFollowers().contains(this.id);
    }

    public boolean isFollowedBy(User sendUser) {
        return this.getFollowers().contains(sendUser.id) && sendUser.getFollowings().contains(this.id);
    }

    public boolean isDeleted() {
        return deleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) && uniqueId.equals(user.uniqueId)
            && nickname.equals(user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueId, nickname);
    }

    /**
     * 유저 - 로그인 유저와의 관계
     * @param loginUser : 로그인한 유저
     * @return : 친구 관계 여부 (SELF : 자기 자신, CONNECTION : 친구, DISCONNECTION : 친구 아님)
     */
    public RelationType relationTypeTo(LoginUser loginUser) {
        if (this.id.equals(loginUser.getId())) {
            return RelationType.SELF;
        }
        else if (loginUser.isAnonymous()) {
            return RelationType.DISCONNECTION;
        }
        else if (!this.followers.isConnected(loginUser.getId())) { // 위 조건문, loginUser.isAnonymous()와 별도로 나눈 조회를 늦게하기 위해서이다.
            return RelationType.DISCONNECTION;
        }
        return RelationType.CONNECTION;
    }
}
