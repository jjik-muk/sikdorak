package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public User(Long uniqueId, String nickname, String profileImage) {
        this(null, uniqueId, nickname, profileImage);
    }

    public User(Long id, Long uniqueId, String nickname, String profileImage) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.nickname = new Nickname(nickname);
        this.profileImage = new ProfileImage(profileImage);
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
}
