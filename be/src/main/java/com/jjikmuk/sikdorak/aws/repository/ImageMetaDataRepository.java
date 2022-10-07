package com.jjikmuk.sikdorak.aws.repository;

import com.jjikmuk.sikdorak.aws.domain.ImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetaDataRepository extends JpaRepository<ImageMetaData, Long> {

	boolean existsByFileName(String fileName);

}
