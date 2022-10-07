package com.jjikmuk.sikdorak.image.command.app;

import com.jjikmuk.sikdorak.image.command.domain.ImageMetaData;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;

public interface ImageMetaDataService {

	ImageMetaData initImageMetaData(String fileName, LoginUser loginUser);
}
