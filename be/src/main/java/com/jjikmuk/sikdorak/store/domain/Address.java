package com.jjikmuk.sikdorak.store.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Address {

    /**
     * 해당 클래스는 카카오 로컬 API 의 지번주소(Address), 도로명주소(RoadAddress) 스키마를
     * 참고하였습니다.
     * https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord-documents-address
     * https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord-documents-road-address
     * <br>
     * 그리고 각 컬럼들의 길이 제한은, '한국지역정보개발원'에서 제공한 자료를 참고하였습니다.
     * 자세한 내용은 아래 링크의 '주소DB활용' 참고하시기 바랍니다.
     * https://www.juso.go.kr/addrlink/attrbDBDwld/attrbDBDwldList.do?cPath=99MD&menu=주소DB
     */

    @Column(length = 255, nullable = false) // 지번 주소
    private String addressName;

    @Column(length = 255, nullable = false) // 도로명 주소
    private String roadAddressName;

    @Column(length = 20) // 지번.시도명
    private String region1DepthName;

    @Column(length = 20) // 지번.시군구명
    private String region2DepthName;

    @Column(length = 20) // 지번.법정읍면동명
    private String region3DepthName;

    @Column(length = 20) // 부가정보.행정동명
    private String region3DepthHName;

    @Column(length = 4) // 지번.지번본번(번지)
    private String mainAddressNo;

    @Column(length = 4) // 지번.지번부번(호)
    private String subAddressNo;

    @Column(length = 80) // 도로명코드.도로명
    private String roadName;

    @Column(length = 5) // 도로명주소.건물본번
    private String mainBuildingNo;

    @Column(length = 5) // 도로명주소.건물부번
    private String subBuildingNo;

    public static Address of(String addressName, String roadAddressName) {
        return new Address(
            addressName, roadAddressName,
            null, null, null, null, null, null, null, null, null
        );
    }
}
