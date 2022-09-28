package com.jjikmuk.sikdorak.user.user.domain;

import com.jjikmuk.sikdorak.user.user.exception.InvalidUserProfileImageUrlException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;

@Getter
@NoArgsConstructor
@Embeddable
public class ProfileImage {

    private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
    private String profileImageUrl;


    public ProfileImage(String profileImageUrl) {
        if (Objects.isNull(profileImageUrl)
            || !validateUrlForm(profileImageUrl)) {
            throw new InvalidUserProfileImageUrlException();
        }

        this.profileImageUrl = profileImageUrl;
    }

    private boolean validateUrlForm(String profileImageUrl) {
        return urlValidator.isValid(profileImageUrl);
    }

}
