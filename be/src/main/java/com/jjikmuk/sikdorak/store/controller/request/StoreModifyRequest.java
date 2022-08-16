package com.jjikmuk.sikdorak.store.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class StoreModifyRequest {

	@NotNull
	private Long id;

	@NotBlank
	@Length(min = 50)
	private String storeName;

	@NotBlank
	private String contactNumber;

	@NotBlank
	@Length(max = 255)
	private String baseAddress;

	@Length(max = 50)
	private String detailAddress;

	@Min(-90)
	@Max(90)
	private Double latitude;

	@Min(-180)
	@Max(180)
	private Double longitude;

	public StoreModifyRequest(Long id, String storeName, String contactNumber, String baseAddress,
		String detailAddress, Double latitude, Double longitude) {
		this.id = id;
		this.storeName = storeName;
		this.contactNumber = contactNumber;
		this.baseAddress = baseAddress;
		this.detailAddress = detailAddress;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
