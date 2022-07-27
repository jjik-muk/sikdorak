package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter

public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId")
    private Long id;
}
