package com.jjikmuk.sikdorak.user.user.domain;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUniqueId(long uniqueId);

    boolean existsById(long id);

    boolean existsByUniqueId(long uniqueId);

    @Query(value = "select f.follower_id "
        + "from (select uf.follower_id from user_followers as uf where uf.user_id = :userId) as f "
        + "join user u on f.follower_id = u.user_id "
        + "where u.deleted = false", nativeQuery = true)
    Set<Long> findFollowers(@Param("userId") long userId);

    @Query(value = "select f.following_id "
        + "from (select uf.following_id from user_following as uf where uf.user_id = :userId) as f "
        + "join user u on f.following_id = u.user_id "
        + "where u.deleted = false", nativeQuery = true)
    Set<Long> findFollowings(@Param("userId")long userId);

}
