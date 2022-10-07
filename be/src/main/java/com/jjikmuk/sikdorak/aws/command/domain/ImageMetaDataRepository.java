package com.jjikmuk.sikdorak.aws.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetaDataRepository extends JpaRepository<ImageMetaData, Long> {

	boolean existsByFileName(String fileName);

}
