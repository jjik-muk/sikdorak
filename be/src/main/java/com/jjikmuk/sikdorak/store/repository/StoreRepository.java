package com.jjikmuk.sikdorak.store.repository;

import com.jjikmuk.sikdorak.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s where s.storeName.storeName like %:storeName%")
    List<Store> findStoresByStoreNameContaining(@Param("storeName") String storeName);
}
