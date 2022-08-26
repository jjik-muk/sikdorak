package com.jjikmuk.sikdorak.comment.service;

import com.jjikmuk.sikdorak.comment.controller.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.comment.controller.request.CommentModifyRequest;
import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.comment.exception.NotFoundCommentException;
import com.jjikmuk.sikdorak.comment.repository.CommentRepository;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;

	@Transactional
	public Comment createComment(long reviewId, LoginUser loginUser,
		CommentCreateRequest commentCreateRequest) {

		// 검증
		User user = userRepository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);

		// 댓글 생성
		Comment comment = new Comment(
			review.getId(),
			user.getId(),
			commentCreateRequest.getContent());

		return commentRepository.save(comment);
	}

	@Transactional
	public void modifyComment(long reviewId, long commentId, LoginUser loginUser,
		CommentModifyRequest modifyRequest) {

		// 검증
		loginUser.ifAnonymousThrowException();
		validateReviewExists(reviewId);
		User currentUser = userRepository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(NotFoundCommentException::new);
		validateModifiableUserOrThrow(comment, currentUser);

		// 댓글 수정
		comment.updateComment(modifyRequest.getContent());
	}

	private void validateReviewExists(long reviewId) {
		if (!reviewRepository.existsById(reviewId)) {
			throw new NotFoundReviewException();
		}
	}

	private void validateModifiableUserOrThrow(Comment comment, User currentUser) {
		if (!comment.isAuthor(currentUser.getId())) {
			throw new UnauthorizedUserException();
		}
	}

	@Transactional
	public void removeComment(Long reviewId, Long commentId, LoginUser loginUser) {

	}
}
