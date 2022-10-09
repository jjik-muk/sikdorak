package com.jjikmuk.sikdorak.image.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetaDataRepository extends JpaRepository<ImageMetaData, Long> {

	boolean existsByFileName(String fileName);

	Optional<ImageMetaData> findByFileName(String fileName);
}
