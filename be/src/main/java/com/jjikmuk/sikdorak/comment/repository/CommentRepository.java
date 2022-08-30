package com.jjikmuk.sikdorak.comment.repository;

import com.jjikmuk.sikdorak.comment.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// TODO : 쿼리 성능 최적화를 위해서 Comment 테이블의 createdAt 컬럼에 index 생성 필요
	@Query(value = "select c from Comment c where c.reviewId = :reviewId order by c.createdAt desc")
	List<Comment> findCommentsByReviewIdWithFirstPage(
		@Param("reviewId") long reviewId,
		Pageable pageable);

	@Query(value = "select c from Comment c where c.reviewId = :reviewId and c.id < :after order by c.createdAt desc")
	List<Comment> findCommentsByReviewIdWithPagingAfter(
		@Param("reviewId") long reviewId,
		@Param("after") long after,
		Pageable pageable);

	@Query(value = "select c from Comment c where c.reviewId = :reviewId and c.id > :before order by c.createdAt")
	List<Comment> findCommentsByReviewIdWithPagingBefore(
		@Param("reviewId") long reviewId,
		@Param("before") long before,
		Pageable pageable);
}
