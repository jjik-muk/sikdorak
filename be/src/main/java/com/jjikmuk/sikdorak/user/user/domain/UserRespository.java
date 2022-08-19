package com.jjikmuk.sikdorak.user.user.domain;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRespository extends JpaRepository<User, Long> {

    Optional<User> findByUniqueId(long uniqueId);

    boolean existsById(long id);

    boolean existsByUniqueId(long uniqueId);

    @Query("select f from User as u join u.followers.follower f where u.id = :userId")
    Set<Long> findFollowers(long userId);

    @Query("select f from User as u join u.followings.following f where u.id = :userId")
    Set<Long> findFollowings(long userId);

}
