package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import com.jjikmuk.sikdorak.user.user.domain.RelationType;
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

	@Embedded
	private Images images = new Images();

	@Embedded
	private Likes likes = new Likes();

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
		this.images = new Images(images);
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
		return images.getImages();
	}

	public Set<Long> getLikes() { return likes.getLikes(); }

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
		this.images = new Images(images);
	}

	public void delete() {
		this.deleted = true;
	}

	public boolean isReadable(RelationType relationType) {
		return reviewVisibility.isRead(relationType);
	}

	public void like(User user) {
		likes.add(user.getId());
	}

	public void unlike(User user) {
		likes.remove(user.getId());
	}

	public boolean isLikedBy(Long userId) {
		return likes.contains(userId);
	}
}
