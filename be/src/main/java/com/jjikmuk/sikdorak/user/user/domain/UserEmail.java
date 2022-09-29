package com.jjikmuk.sikdorak.user.user.domain;

import com.jjikmuk.sikdorak.user.user.exception.InvalidUserEmailException;
import javax.persistence.Embeddable;
import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;

@Getter
@Embeddable
public class UserEmail {

    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    private String email;

    public UserEmail() {
        this.email = "";
    }

    public UserEmail(String email) {
        if (!emailValidator.isValid(email)) {
            throw new InvalidUserEmailException();
        }
        this.email = email;
    }

}
