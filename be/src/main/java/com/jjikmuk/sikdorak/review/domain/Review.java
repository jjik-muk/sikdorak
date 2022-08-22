package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import com.jjikmuk.sikdorak.user.user.domain.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@SQLDelete(sql = "update review set deleted = true where review_id = ?")
@Where(clause = "deleted = false")
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reviewId")
	private Long id;

	private Long userId;

	private Long storeId;

	@Embedded
	private ReviewContent reviewContent;

	@Embedded
	private ReviewScore reviewScore;

	@Enumerated(EnumType.STRING)
	private ReviewVisibility reviewVisibility;

	@Embedded
	private ReviewVisitedDate visitedDate;

	@Embedded
	private Tags tags = new Tags();

	@Transient
	private List<String> images;

	private boolean deleted = Boolean.FALSE;

	public Review(Long id, Long userId, Long storeId, String reviewContent, Float reviewScore,
		String reviewVisibility,
		LocalDate visitedDate, List<String> tags, List<String> images) {
		this.id = id;
		this.userId = userId;
		this.storeId = storeId;
		this.reviewContent = new ReviewContent(reviewContent);
		this.reviewScore = new ReviewScore(reviewScore);
		this.reviewVisibility = ReviewVisibility.create(reviewVisibility);
		this.visitedDate = new ReviewVisitedDate(visitedDate);
		this.tags = new Tags(tags);
		this.images = images;
	}


	public Review(Long userId, Long storeId, String reviewContent, Float reviewScore, String reviewVisibility,
		LocalDate visitedDate, List<String> tags, List<String> images) {
		this(null, userId, storeId, reviewContent, reviewScore, reviewVisibility, visitedDate, tags,
			images);
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public float getReviewScore() {
		return reviewScore.getReviewScore();
	}

	public String getReviewVisibility() {
		return reviewVisibility.name();
	}

	public LocalDate getVisitedDate() {
		return visitedDate.getReviewVisitedDate();
	}

	public Set<String> getTags() {
		return tags.getTags();
	}

	public List<String> getImages() {
		return images;
	}

	public String getReviewContent() {
		return reviewContent.getReviewContent();
	}

	public boolean isAuthor(User user) {
		return this.userId.equals(user.getId());
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void editAll(Long storeId, String reviewContent, Float reviewScore,
		String reviewVisibility, LocalDate visitedDate, List<String> tags, List<String> images) {
		this.storeId = storeId;
		this.reviewContent = new ReviewContent(reviewContent);
		this.reviewScore = new ReviewScore(reviewScore);
		this.reviewVisibility = ReviewVisibility.create(reviewVisibility);
		this.visitedDate = new ReviewVisitedDate(visitedDate);
		this.tags = new Tags(tags);
		this.images = images;
	}

	public void delete() {
		this.deleted = true;
	}

}
