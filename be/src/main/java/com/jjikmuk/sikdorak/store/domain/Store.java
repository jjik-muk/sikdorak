package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter

public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Embedded
    private StoreName storeName;

    @Embedded
    private ContactNumber contactNumber;

    @Embedded
    private Address address; // baseAddress, detailAddress

    @Embedded
    private StoreLocation location; // Float latitude, Float longitude

}
