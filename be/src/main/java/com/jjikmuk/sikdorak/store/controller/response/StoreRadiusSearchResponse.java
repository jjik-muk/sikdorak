package com.jjikmuk.sikdorak.store.controller.response;

public record StoreRadiusSearchResponse(

    long id,
    String storeName,
    String contactNumber,
    String addressName,
    String roadAddressName,
    double x,
    double y

) {
}
