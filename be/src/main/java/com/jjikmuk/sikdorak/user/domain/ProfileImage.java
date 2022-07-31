package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.auth.exception.InvalidUserProfileImageUrlException;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewScoreException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@Embeddable
public class ProfileImage {

    private static final String URL_REGEX = "^((((https?|http?)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
    private String profileImageUrl;


    public ProfileImage(String profileImageUrl) {
        if (Objects.isNull(profileImageUrl) ||
                !validateUrlForm(profileImageUrl)) {
            throw new InvalidUserProfileImageUrlException();
        }

        this.profileImageUrl = profileImageUrl;
    }

    private boolean validateUrlForm(String profileImageUrl) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(profileImageUrl);
        return matcher.matches();
    }

}
