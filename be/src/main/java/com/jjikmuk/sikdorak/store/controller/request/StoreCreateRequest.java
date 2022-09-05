package com.jjikmuk.sikdorak.store.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class StoreCreateRequest {

	@NotBlank
	@Length(min = 50)
	private String storeName;

	@NotBlank
	private String contactNumber;

	@NotBlank
	@Length(max = 255)
	private String addressName;

	@NotBlank
	@Length(max = 255)
	private String roadAddressName;

	@Min(-180)
	@Max(180)
	private Double x;

	@Min(-90)
	@Max(90)
	private Double y;

	public StoreCreateRequest(String storeName, String contactNumber, String addressName,
		String roadAddressName, Double x, Double y) {
		this.storeName = storeName;
		this.contactNumber = contactNumber;
		this.addressName = addressName;
		this.roadAddressName = roadAddressName;
		this.x = x;
		this.y = y;
	}
}
