package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewImagesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Images {

	private static final int LIMIT_SIZE = 10;

	@ElementCollection
	@CollectionTable(
		name = "review_images",
		joinColumns = @JoinColumn(name = "review_id")
	)
	@OrderColumn
	private List<Image> images;

	public Images(List<String> images) {
		if (Objects.isNull(images)) {
			this.images = new ArrayList<>();
			return;
		}

		List<Image> filteredImages = images.stream()
			.distinct()
			.map(Image::new)
			.toList();

		if (filteredImages.size() > LIMIT_SIZE) {
			throw new InvalidReviewImagesException();
		}
		this.images = filteredImages;
	}

	public List<String> getImages() {
		return images.stream()
			.map(Image::getUrl)
			.toList();
	}

	public int size() {
		return images.size();
	}
}
