package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.user.exception.InvalidUserEmailException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@Embeddable
public class Email {

    private static final String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    @javax.validation.constraints.Email
    private String email;

    public Email(String email) {
        if (!validateEmail(email)) throw new InvalidUserEmailException();
        this.email = email;
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
