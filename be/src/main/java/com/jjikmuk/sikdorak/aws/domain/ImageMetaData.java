package com.jjikmuk.sikdorak.aws.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ImageMetaData extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_meta_data_id")
	private Long imageMetaDataId;

	@Column(name = "owner_user_id", nullable = false)
	private long userId;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Column(name = "image_size", nullable = false)
	private long imageSize;

	public ImageMetaData(long userId, String fileName, long imageSize) {
		this.userId = userId;
		this.fileName = fileName;
		this.imageSize = imageSize;
	}
}
