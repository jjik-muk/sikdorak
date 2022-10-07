package com.jjikmuk.sikdorak.aws.service;

import com.jjikmuk.sikdorak.aws.domain.ImageMetaData;
import com.jjikmuk.sikdorak.aws.repository.ImageMetaDataRepository;
import com.jjikmuk.sikdorak.aws.service.exception.DuplicateImageMetaDataException;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageMetaDataServiceImpl implements ImageMetaDataService {

	private final UserRepository userRepository;
	private final ImageMetaDataRepository imageMetaDataRepository;

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
}

