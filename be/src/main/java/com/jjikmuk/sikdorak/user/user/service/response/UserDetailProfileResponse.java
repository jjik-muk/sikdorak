package com.jjikmuk.sikdorak.user.user.service.response;

import com.jjikmuk.sikdorak.user.user.domain.User;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record UserDetailProfileResponse(

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

    public static UserDetailProfileResponse of(User user,
        UserProfileRelationStatusResponse userProfileRelationStatusResponse,
        int followingCount,
        int followersCount,
        int reviewCount) {
        return new UserDetailProfileResponse(
            user.getId(),
            user.getNickname(),
            user.getProfileImage(),
            user.getEmail(),
            userProfileRelationStatusResponse,
            followingCount,
            followersCount,
            reviewCount
        );
    }
}
