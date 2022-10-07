package com.jjikmuk.sikdorak.aws.command.app;

import com.jjikmuk.sikdorak.aws.command.domain.ImageMetaData;
import com.jjikmuk.sikdorak.aws.command.domain.ImageMetaDataRepository;
import com.jjikmuk.sikdorak.aws.exception.DuplicateImageMetaDataException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
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

