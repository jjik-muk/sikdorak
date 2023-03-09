package com.jjikmuk.sikdorak.user.user.command.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUniqueId(String uniqueId);

    boolean existsById(long id);

    boolean existsByUniqueId(String uniqueId);

    @Query(value = "select u.* "
        + "from (select uf.follower_id from user_followers as uf where uf.user_id = :userId) as f "
        + "join user u on f.follower_id = u.user_id "
        + "where u.deleted = false", nativeQuery = true)
    List<User> findFollowersByUserId(@Param("userId") long userId);

    @Query(value = "select u.* "
        + "from (select uf.following_id from user_following as uf where uf.user_id = :userId) as f "
        + "join user u on f.following_id = u.user_id "
        + "where u.deleted = false", nativeQuery = true)
    List<User> findFollowingsByUserId(@Param("userId")long userId);

    @Query("select u from User u where u.nickname.nickname like %:nickname%")
    List<User> findAllByNickname(@Param("nickname")String nickname);
}
