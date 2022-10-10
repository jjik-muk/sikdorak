package com.jjikmuk.sikdorak.image.command.app;

import com.jjikmuk.sikdorak.image.command.domain.ImageMetaData;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import java.util.List;

public interface ImageMetaDataService {

	ImageMetaData initImageMetaData(String fileName, LoginUser loginUser);

	List<ImageMetaData> updateImageMetaData(List<String> images, LoginUser loginUser);
}
