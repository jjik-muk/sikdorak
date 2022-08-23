package com.jjikmuk.sikdorak.review.repository;

import com.jjikmuk.sikdorak.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByUserId(Long userId);

	@Query("select r from Review as r where r.userId = :userId and not r.reviewVisibility = 'PRIVATE'")
	List<Review> findByUserIdAndConnection(Long userId);

	@Query("select r from Review as r where r.userId = :userId and r.reviewVisibility = 'PUBLIC'")
	List<Review> findByUserIdAndDisconnection(Long userId);

	Integer countByUserId(Long userId);
}
