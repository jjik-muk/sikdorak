package com.jjikmuk.sikdorak.user.service;

import com.jjikmuk.sikdorak.user.domain.User;
import com.jjikmuk.sikdorak.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRespository userRespository;

    public long createUser(User user) {

        if (isExistingUserByUniqueId(user.getUniqueId())) {
            throw new DuplicateUserException();
        }
        userRespository.save(user);
        return user.getId();
    }

    public User searchUserById(long userId) {
        return userRespository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    }

    public User searchUserByUniqueId(long uniqueId) {
        return userRespository.findByUniqueId(uniqueId)
            .orElseThrow(UserNotFoundException::new);
    }

    public boolean isExistingUserId(long userId) {
        return userRespository.existsById(userId);
    }

    public boolean isExistingUserByUniqueId(long userUniqueId) {
        return userRespository.existsByUniqueId(userUniqueId);
    }
}
