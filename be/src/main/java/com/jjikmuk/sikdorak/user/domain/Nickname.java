package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.auth.exception.InvalidUserNicknameException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class Nickname {

    private static final int MIN_LENGTH = 0;
    private static final int MAX_LENGTH = 30;
    private String nickname;

    public Nickname(String nickname) {
        if (Objects.isNull(nickname) ||
                !validateNickname(nickname)) {
            throw new InvalidUserNicknameException();
        }
        this.nickname = nickname;
    }

    private boolean validateNickname(String nickname) {
        return nickname.length() > MIN_LENGTH && nickname.length() < MAX_LENGTH;
    }
}
