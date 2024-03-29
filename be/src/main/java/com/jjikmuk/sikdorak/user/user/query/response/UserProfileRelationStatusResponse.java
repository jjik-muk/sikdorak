package com.jjikmuk.sikdorak.user.user.query.response;

import com.jjikmuk.sikdorak.user.user.command.domain.RelationType;

public record UserProfileRelationStatusResponse (

    boolean isViewer,
    boolean followStatus

) {

    public static UserProfileRelationStatusResponse of(RelationType relationType) {
        boolean isViewer = relationType.equals(RelationType.SELF);
        boolean followStatus = relationType.equals(RelationType.CONNECTION);

        return new UserProfileRelationStatusResponse(isViewer, followStatus);
    }
}
