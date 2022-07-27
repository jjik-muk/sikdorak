package com.jjikmuk.sikdorak.review.repository;

import com.jjikmuk.sikdorak.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
