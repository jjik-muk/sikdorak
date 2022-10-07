package com.jjikmuk.sikdorak.aws.command.app;

import com.jjikmuk.sikdorak.aws.command.domain.ImageMetaData;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;

public interface ImageMetaDataService {

	ImageMetaData initImageMetaData(String fileName, LoginUser loginUser);
}
