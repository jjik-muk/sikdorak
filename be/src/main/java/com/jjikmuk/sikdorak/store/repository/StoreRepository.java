package com.jjikmuk.sikdorak.store.repository;

import com.jjikmuk.sikdorak.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
