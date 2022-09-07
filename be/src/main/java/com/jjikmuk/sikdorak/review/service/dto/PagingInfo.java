package com.jjikmuk.sikdorak.review.service.dto;

import org.springframework.data.domain.Pageable;

public record PagingInfo (
    long cursor,
    Pageable pageable
) {

    public static PagingInfo from(long cursor, int size) {
        return new PagingInfo(cursor, Pageable.ofSize(size));
    }
}
