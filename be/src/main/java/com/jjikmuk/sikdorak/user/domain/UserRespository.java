package com.jjikmuk.sikdorak.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
    boolean existsByUniqueId(long userUniqueId);
}
