package com.jjikmuk.sikdorak.comment.service;

import com.jjikmuk.sikdorak.comment.controller.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.comment.repository.CommentRepository;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRespository userRespository;
	private final ReviewRepository reviewRepository;

	public Comment createComment(
		long reviewId,
		LoginUser loginUser,
		CommentCreateRequest commentCreateRequest) {
		User user = userRespository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);

		Comment comment = new Comment(
			review.getId(),
			user.getId(),
			commentCreateRequest.getContent());

		return commentRepository.save(comment);
	}
}
