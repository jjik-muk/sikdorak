package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidTagException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "text")
public class Tag {

    public static final int LIMIT_LENGTH = 50;
    @Column(name = "tag_text")
    private String text;

    public Tag(String text) {

        if (Objects.isNull(text) ||
                text.isBlank() ||
                text.length() > LIMIT_LENGTH ||
                hasSymbols(text)) {
            throw new InvalidTagException();
        }

        this.text = text.toLowerCase(Locale.ROOT);
    }

    private boolean hasSymbols(String tag) {
        Pattern compile = Pattern.compile("[\\s!@#$%^&*\\+\\-(),.?\":{}|<>]");
        Matcher matcher = compile.matcher(tag);
        return matcher.find();
    }
    public String getText() {
        return text;
    }
}
