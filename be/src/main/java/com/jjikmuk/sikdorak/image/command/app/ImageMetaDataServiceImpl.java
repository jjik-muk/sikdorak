package com.jjikmuk.sikdorak.image.command.app;

import com.jjikmuk.sikdorak.image.command.domain.ImageFileName;
import com.jjikmuk.sikdorak.image.command.domain.ImageMetaData;
import com.jjikmuk.sikdorak.image.command.domain.ImageMetaDataRepository;
import com.jjikmuk.sikdorak.image.exception.DuplicateImageMetaDataException;
import com.jjikmuk.sikdorak.image.exception.NotFoundImageMetaDataException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageMetaDataServiceImpl implements ImageMetaDataService {

	private final UserRepository userRepository;
	private final ImageMetaDataRepository imageMetaDataRepository;
	private final GeneratePreSignedURLService generatePreSignedURLService;

	/**
	 * 이미지 메타 데이터 초기 생성을 합니다. 초기 image 데이터 사이즈는 0KB로 초기화 됩니다. 메타데이터 크기는 리뷰 작성, 수정할 떄 수정됩니다.
	 *
	 * @param fileName  s3에 저장된 이미지 파일 이름
	 * @param loginUser 유저
	 * @return ImageMetaData 객체
	 */
	@Override
	@Transactional
	public ImageMetaData initImageMetaData(String fileName, LoginUser loginUser) {
		if (imageMetaDataRepository.existsByFileName(fileName)) {
			throw new DuplicateImageMetaDataException();
		}
		User user = userRepository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);

		ImageMetaData imageMetaData = new ImageMetaData(user.getId(),
			fileName,
			0L);

		return imageMetaDataRepository.save(imageMetaData);
	}

	/**
	 * 이미지 메타 데이터 사이즈를 업데이트합니다.
	 * 해당 메서드는 Review가 생성 될 때 실행됩니다.
	 *
	 * @param images 이미지가 저장된 s3 링크
	 * @param loginUser 로그인 유저 정보
	 * @return 업데이트된 이미지 메타 데이터 리스트
	 */
	@Override
	@Transactional
	public List<ImageMetaData> updateImageMetaData(List<String> images, LoginUser loginUser) {
		User user = userRepository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);

		List<ImageFileName> imageFileNames = images.stream()
			.map(ImageFileName::new).toList();

		return imageFileNames.stream()
			.map(imageFileName -> {
				ImageMetaData imageMetaData = imageMetaDataRepository.findByFileName(imageFileName.getFileName())
					.orElseThrow(NotFoundImageMetaDataException::new);

				imageMetaData.updateSize(
					user, generatePreSignedURLService.getS3ObjectSize(imageMetaData.getFileName()));

				return imageMetaData;
			})
			.toList();
	}

}

