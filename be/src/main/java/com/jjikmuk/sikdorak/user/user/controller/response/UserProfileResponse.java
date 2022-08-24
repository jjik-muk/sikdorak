package com.jjikmuk.sikdorak.user.user.controller.response;

import com.jjikmuk.sikdorak.user.user.domain.User;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record UserProfileResponse(

    @NotNull
    long id,

    @NotNull
    @Length(min = 1, max = 30)
    String nickname,

    @NotNull
    @URL
    String profileImage,

    String email,

    UserProfileRelationStatusResponse relationStatus,

    @NotNull
    @Min(0)
    int followingCount,

    @NotNull
    @Min(0)
    int followersCount,

    @NotNull
    @Min(0)
    int reviewCount

) {

    public static UserProfileResponse of(User user, UserProfileRelationStatusResponse userProfileRelationStatusResponse, int reviewCount) {
        return new UserProfileResponse(
            user.getId(),
            user.getNickname(),
            user.getProfileImage(),
            user.getEmail(),
            userProfileRelationStatusResponse,
            user.getFollowers().size(),
            user.getFollowings().size(),
            reviewCount
        );
    }
}
