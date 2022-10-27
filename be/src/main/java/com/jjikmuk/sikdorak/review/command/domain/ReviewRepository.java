package com.jjikmuk.sikdorak.review.command.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUserId(Long userId);

    @Query("""
        select r from Review as r
        where r.userId = :userId and r.id <= :targetId
        order by r.id desc
        """)
    Slice<Review> findByUserIdWithPageable(@Param("userId") long userId,
        @Param("targetId") long targetId,
        Pageable pageable);

    @Query("""
        select r from Review as r
        where r.userId = :userId and not r.reviewVisibility = 'PRIVATE' and r.id <= :targetId
        order by r.id desc
        """)
    Slice<Review> findByUserIdAndConnection(@Param("userId") long userId,
        @Param("targetId") long targetId,
        Pageable pageable);

    @Query("""
        select r from Review as r
        where r.userId = :userId and r.reviewVisibility = 'PUBLIC' and r.id <= :targetId
        order by r.id desc
        """)
    Slice<Review> findByUserIdAndDisconnection(@Param("userId") Long userId,
        @Param("targetId") long targetId,
        Pageable pageable);

    Integer countByUserId(Long userId);

    @Query(value = """
        select * from review as r
        where r.review_visibility = 'PUBLIC' and r.review_id <= :targetId
        order by (select count(*) from likes as l where l.review_id = r.review_id) desc, r.review_id desc 
        """,
        nativeQuery = true)
    Slice<Review> findPublicRecommendedReviewsInRecentOrder(
        @Param("targetId") long targetId,
        Pageable pageable);

    @Query("""
        select r from Review r
        where not r.reviewVisibility = 'PRIVATE'
        and r.id <= :targetId and r.userId <> :authorId
        order by r.id desc
        """)
    Slice<Review> findPublicAndProtectedRecommendedReviewsInRecentOrder(
        @Param("authorId") long authorId,
        @Param("targetId") long targetId,
        Pageable pageable);

    Integer countByStoreId(@Param("storeId") long storeId);

    @Query(value = """
        SELECT avg(r.reviewScore.reviewScore) FROM Review r
         WHERE r.storeId = :storeId AND r.deleted = false""")
    Optional<Double> findReviewScoreAverageByStoreId(@Param("storeId") long storeId);

    @Query("""
        SELECT r FROM Review r
         WHERE r.storeId = :storeId
           AND r.id <= :targetId
           AND r.reviewVisibility = 'PUBLIC'
         ORDER BY r.id DESC
        """)
    Slice<Review> findPublicReviewsOfStore(
        @Param("storeId") long storeId,
        @Param("targetId") long targetId,
        Pageable pageable);

    @Query(value = "select max(review_id) from review", nativeQuery = true)
    Optional<Long> findMaxId();


    @Query(value = """
        select * from review as r
        join store as s on s.store_id = r.store_id
        where r.review_id <= :targetId and r.user_id = :authorId
        and r.review_visibility = 'PUBLIC'
        and s.x between :minX and :maxX
        and s.y between :minY and :maxY  
        order by (select count(*) from likes as l where l.review_id = r.review_id) desc, r.review_id desc 
        """,
        nativeQuery = true)
    Slice<Review> findPublicReviewsByRadius(
        @Param("authorId") long authorId,
        @Param("targetId") long targetId,
        Pageable pageable,
        double maxX, double maxY,
        double minX, double minY);

    @Query("""
        select r from Review r
        join Store as s on s.id = r.storeId
        where r.id <= :targetId and r.userId = :authorId
        and not r.reviewVisibility = 'PRIVATE'
        and s.location.x between :minX and :maxX
        and s.location.y between :minY and :maxY
        order by r.id desc
        """)
    Slice<Review> findPublicAndProtectedReviewsByRadius(
        @Param("authorId") long authorId,
        @Param("targetId") long targetId,
        Pageable pageable,
        double maxX, double maxY,
        double minX, double minY);

}
