package com.jjikmuk.sikdorak.user.user.command.domain;

import com.jjikmuk.sikdorak.user.user.exception.InvalidUserNicknameException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class UserNickname {

    private static final int MIN_LENGTH = 0;
    private static final int MAX_LENGTH = 30;
    private String nickname;

    public UserNickname(String nickname) {
        if (Objects.isNull(nickname)
            || !isValidNickname(nickname)) {
            throw new InvalidUserNicknameException();
        }
        this.nickname = nickname;
    }

    private boolean isValidNickname(String nickname) {
        return nickname.length() > MIN_LENGTH && nickname.length() < MAX_LENGTH;
    }
}
