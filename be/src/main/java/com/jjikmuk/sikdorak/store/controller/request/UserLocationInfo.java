package com.jjikmuk.sikdorak.store.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record UserLocationInfo(

    @NotNull
    @Min(-180)
    @Max(180)
    double x,

    @NotNull
    @Min(-90)
    @Max(90)
    double y,

    @NotNull
    @Min(0)
    @Max(20000)
    int radius
) {

}
