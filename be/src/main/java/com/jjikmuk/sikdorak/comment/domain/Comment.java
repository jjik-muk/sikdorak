package com.jjikmuk.sikdorak.comment.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import com.jjikmuk.sikdorak.common.domain.Deleted;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@NoArgsConstructor
@SQLDelete(sql = "update comment set deleted = true where comment_id = ?")
@Where(clause = "deleted = false")
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Column(name = "review_id", nullable = false)
	private long reviewId;

	@Column(name = "user_id", nullable = false)
	private long userId;

	@Embedded
	private CommentContent commentContent;

	@Embedded
	private Deleted deleted = Deleted.FALSE;

	public Comment(long reviewId, long userId, String commentContent) {
		this.reviewId = reviewId;
		this.userId = userId;
		this.commentContent = new CommentContent(commentContent);
	}

	public Long getId() {
		return id;
	}

	public long getReviewId() {
		return reviewId;
	}

	public long getUserId() {
		return userId;
	}

	public String getCommentContent() {
		return commentContent.getReviewContent();
	}

	public boolean isAuthor(long userId) {
		return this.userId == userId;
	}

	public void updateComment(String content) {
		this.commentContent = new CommentContent(content);
	}

	public void delete() {
		this.deleted.delete();
	}
}
