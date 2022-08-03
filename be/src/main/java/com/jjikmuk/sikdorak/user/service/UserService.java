package com.jjikmuk.sikdorak.user.service;

import com.jjikmuk.sikdorak.user.domain.User;
import com.jjikmuk.sikdorak.user.domain.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRespository userRespository;

    public long createUser(User user) {
        userRespository.save(user);
        return user.getId();
    }

    public boolean isExistingUser(long userUniqueId) {

        return userRespository.existsByUniqueId(userUniqueId);
    }

}
