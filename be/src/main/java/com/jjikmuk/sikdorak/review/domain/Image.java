package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewImageException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    @Column(name = "image_url")
    private String url;

    public Image(String url) {
        if (Objects.isNull(url)
            || url.isBlank()
            || !validateUrlForm(url)) {
            throw new InvalidReviewImageException();
        }
        this.url = url;
    }

    // TODO : 특정 s3 주소만 image로 들어올 수 있도록 수정 가능하다.
    private boolean validateUrlForm(String url) {
        return urlValidator.isValid(url);
    }
}
