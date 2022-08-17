package com.jjikmuk.sikdorak.user.user.service;

import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRespository userRespository;

    public long createUser(User user) {

        if (isExistingByUniqueId(user.getUniqueId())) {
            throw new DuplicateUserException();
        }
        userRespository.save(user);
        return user.getId();
    }

    public User searchById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new NotFoundUserException();
        }

        return userRespository.findById(userId)
            .orElseThrow(NotFoundUserException::new);
    }

    public User searchByUniqueId(long uniqueId) {
        return userRespository.findByUniqueId(uniqueId)
            .orElseThrow(NotFoundUserException::new);
    }

    public boolean isExistingById(long userId) {
        return userRespository.existsById(userId);
    }

    public boolean isExistingByUniqueId(long userUniqueId) {
        return userRespository.existsByUniqueId(userUniqueId);
    }
}
