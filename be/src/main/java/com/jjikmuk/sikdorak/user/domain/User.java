package com.jjikmuk.sikdorak.user.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private Long kakaoUniqueId;
    private Nickname nickname;

    @Embedded
    private ProfileImage profileImage;

    public User(Long kakaoUniqueId, String nickname, String profileImage) {
        this(null, kakaoUniqueId, nickname, profileImage);
    }

    public User(Long id, Long kakaoUniqueId, String nickname, String profileImage) {
        this.id = id;
        this.kakaoUniqueId = kakaoUniqueId;
        this.nickname = new Nickname(nickname);
        this.profileImage = new ProfileImage(profileImage);
    }

    public Long getId() {
        return id;
    }

    public Long getKakaoUniqueId() {
        return kakaoUniqueId;
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getProfileImage() {
        return profileImage.getProfileImageUrl();
    }
}
