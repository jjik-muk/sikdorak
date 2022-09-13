package com.jjikmuk.sikdorak.store.repository;

import com.jjikmuk.sikdorak.store.domain.Store;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s where s.storeName.storeName like %:storeName%")
    List<Store> findStoresByStoreNameContaining(@Param("storeName") String storeName);

    Optional<Store> findStoreByPlaceId(@Param("placeId") Long placeId);

    @Query("""
        select s from Store s
        where s.location.x between :minX and :maxX
        and s.location.y between :minY and :maxY
        and s.id > :targetId
        order by s.id
        """)
    List<Store> findStoresByRadius(@Param("maxX") double maxX,
        @Param("maxY")double maxY,
        @Param("minX")double minX,
        @Param("minY")double minY,
        @Param("targetId")long targetId,
        Pageable pageable);
}
