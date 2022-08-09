package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.user.exception.InvalidUserProfileImageUrlException;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class ProfileImage {

    private static final String URL_REGEX = "^(((https?|http?)://)(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][:blank])?$";

    private String profileImageUrl;


    public ProfileImage(String profileImageUrl) {
        if (Objects.isNull(profileImageUrl) ||
                !validateUrlForm(profileImageUrl)) {
            throw new InvalidUserProfileImageUrlException();
        }

        this.profileImageUrl = profileImageUrl;
    }

    private boolean validateUrlForm(String profileImageUrl) {
        return Pattern.matches(URL_REGEX, profileImageUrl);
    }

}
