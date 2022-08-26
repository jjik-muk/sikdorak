package com.jjikmuk.sikdorak.user.user.controller.response;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record FollowUserProfile(

    @NotNull
    long id,

    @NotNull
    @Length(min = 1, max = 30)
    String nickname,

    @NotNull
    @URL
    String profileImage,

    boolean isViewer,
    boolean followStatus


) {

    public static FollowUserProfile of(UserSimpleProfileResponse userProfile,
        UserProfileRelationStatusResponse relationStatus) {
        return new FollowUserProfile(
            userProfile.id(),
            userProfile.nickname(),
            userProfile.profileImage(),
            relationStatus.isViewer(),
            relationStatus.followStatus());
    }
}
