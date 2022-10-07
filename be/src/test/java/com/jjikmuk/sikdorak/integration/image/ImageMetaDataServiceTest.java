package com.jjikmuk.sikdorak.integration.image;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.jjikmuk.sikdorak.image.command.app.ImageMetaDataService;
import com.jjikmuk.sikdorak.image.command.domain.ImageMetaData;
import com.jjikmuk.sikdorak.image.exception.DuplicateImageMetaDataException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO
 * [x]. 이미지 메타데이터 생성 테스트
 * [x]. 이미지 메타데이터 중복 생성 테스트
 * [x]. 유효하지 않은 유저가 이미지 메타데이터 초기화 실패 테스트
 * [ ]. 0KB 배치 처리 삭제 로직
 * [ ]. 이미지 파일 크기 메타 정보 수정 성공
 * [ ]. 이미지 파일 크기 메타 정보 수정 실패
 */
@DisplayName("통합 : Image 메타정보 생성")
class ImageMetaDataServiceTest extends InitIntegrationTest {

	@Autowired
	private ImageMetaDataService imageMetaDataService;

	@Nested
	@DisplayName("initImageMetaData 메서드")
	class Describe_initImageMetaData {

		@Nested
		@DisplayName("만약 유효한 유저와 파일 이름이 주어진다면")
		class Context_with_valid_user_and_file_name {

			@Test
			@DisplayName("ImageMetaData 객체를 생성한다")
			void It_returns_a_valid_object() {
				String fileName = "0f755dc8-739b-44e7-9c83-4569ze2a7e36.png";
				LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);

				ImageMetaData initImageMetaData = imageMetaDataService.initImageMetaData(fileName,
					loginUser);

				assertAll(
					() -> assertThat(initImageMetaData.getFileName()).isEqualTo(fileName),
					() -> assertThat(initImageMetaData.getUserId()).isEqualTo(loginUser.getId()),
					() -> assertThat(initImageMetaData.getImageSize()).isZero()
				);
			}
		}

		@Nested
		@DisplayName("만약 유효하지 않은 유저 id가 주어진다면")
		class Context_with_invalid_user_id {

			@Test
			@DisplayName("NotFoundUserException 예외를 던진다")
			void It_throws_NotFoundUserException() {
				String fileName = "0f755dc8-739b-44e7-9c83-4569ze2a7e36.png";
				long invalidUserId = Long.MAX_VALUE;
				LoginUser loginUser = new LoginUser(invalidUserId, Authority.USER);

				assertThatThrownBy(() ->
					imageMetaDataService.initImageMetaData(fileName, loginUser))
					.isInstanceOf(NotFoundUserException.class);
			}
		}

		@Nested
		@DisplayName("만약 이미 생성된 이미지 메타 데이터 파일 이름이 주어진다면")
		class Context_with_duplicated_image_meta_data_file_name {

			@Test
			@DisplayName("DuplicateImageMetaDataException 예외를 던진다")
			void It_throws_DuplicateImageMetaDataException() {
				String fileName = "0f755dc8-739b-44e7-9c83-abcde.png";
				LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
				imageMetaDataService.initImageMetaData(fileName,
					new LoginUser(testData.kukim.getId(),
						Authority.USER));

				assertThatThrownBy(() ->
					imageMetaDataService.initImageMetaData(fileName, loginUser))
					.isInstanceOf(DuplicateImageMetaDataException.class);
			}
		}
	}

	@Nested
	@DisplayName("updateImageMetaData 메서드")
	class Describe_updateImageMetaData {

		@BeforeEach
		void setUp() {
			amazonS3.createBucket(awsProperties.getBucket());
			amazonS3.putObject(awsProperties.getBucket(),
				"origin/abcde-1234-testimage-jjikmuk.png",
				new File("src/test/resources/images/jjikmuk.png"));
		}

		@AfterEach
		void tearDown() {
			amazonS3.deleteObject(awsProperties.getBucket(),
				"origin/abcde-1234-testimage-jjikmuk.png");
			amazonS3.deleteBucket(awsProperties.getBucket());
		}

		@Nested
		@DisplayName("만약 유효한 유저와 이미지 파일 리스트가 주어진다면")
		class Context_with_valid_user_and_image_file_lists {

			@Test
			@DisplayName("ImageMetaData 파일 크기 업데이트를 하고 ImageMetaData 객체 리스트를 반환한다")
			void It_returns_a_valid_Object_list() {
				imageMetaDataService.initImageMetaData(
					testData.kukimImageFileName,
					new LoginUser(testData.kukim.getId(), Authority.USER));
				List<String> images = List.of("https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/abcde-1234-testimage-jjikmuk.png");

				List<ImageMetaData> imageMetaDataList = imageMetaDataService.updateImageMetaData(images,
					new LoginUser(testData.kukim.getId(), Authority.USER));

				assertAll(
					() -> assertThat(imageMetaDataList).hasSize(1),
					() -> assertThat(imageMetaDataList.get(0).getFileName()).isEqualTo(testData.kukimImageFileName),
					() -> assertThat(imageMetaDataList.get(0).getImageSize()).isNotZero());
			}
		}
	}


}
