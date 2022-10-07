package com.jjikmuk.sikdorak.aws.service;

import com.jjikmuk.sikdorak.aws.domain.ImageMetaData;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;

public interface ImageMetaDataService {

	ImageMetaData initImageMetaData(String fileName, LoginUser loginUser);
}
