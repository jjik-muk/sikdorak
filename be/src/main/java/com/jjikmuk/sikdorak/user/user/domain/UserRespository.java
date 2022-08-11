package com.jjikmuk.sikdorak.user.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {

    Optional<User> findByUniqueId(long uniqueId);

    boolean existsById(long id);

    boolean existsByUniqueId(long uniqueId);


}
