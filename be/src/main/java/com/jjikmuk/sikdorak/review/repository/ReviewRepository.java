package com.jjikmuk.sikdorak.review.repository;

import com.jjikmuk.sikdorak.review.domain.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUserId(Long userId);

    @Query("select r from Review as r where r.userId = :userId and not r.reviewVisibility = 'PRIVATE'")
    List<Review> findByUserIdAndConnection(@Param("userId") Long userId);

    @Query("select r from Review as r where r.userId = :userId and r.reviewVisibility = 'PUBLIC'")
    List<Review> findByUserIdAndDisconnection(@Param("userId") Long userId);

    Integer countByUserId(Long userId);

    @Query(value = """
        select * from review as r
        where r.review_visibility = 'PUBLIC' and r.review_id <= :targetId
        order by (select count(*) from likes as l where l.review_id = r.review_id) desc, r.review_id desc 
        """,
        nativeQuery = true)
    List<Review> findPublicRecommendedReviewsInRecentOrder(
        @Param("targetId") long targetId,
        Pageable pageable);

    @Query("""
        	select r from Review r
        	where r.reviewVisibility = 'PROTECTED' or r.reviewVisibility = 'PUBLIC'
        	and r.id <= :targetId and r.userId <> :authorId
        	order by r.id desc
        """)
    List<Review> findPublicAndProtectedRecommendedReviewsInRecentOrder(
        @Param("authorId") long authorId,
        @Param("targetId") long targetId,
        Pageable pageable);

}
