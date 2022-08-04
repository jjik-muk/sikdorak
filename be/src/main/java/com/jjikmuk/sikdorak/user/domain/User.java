package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
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

    public User(Long uniqueId, String nickname, String profileImage, String email) {
        this(null, uniqueId, nickname, profileImage, email);
    }

    public User(Long id, Long uniqueId, String nickname, String profileImage, String email) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.nickname = new Nickname(nickname);
        this.profileImage = new ProfileImage(profileImage);
        this.email = new Email(email);
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
}
