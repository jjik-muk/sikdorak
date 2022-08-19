package com.jjikmuk.sikdorak.user.user.domain;

import com.jjikmuk.sikdorak.user.user.exception.InvalidFollowingException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Followings {

    @ElementCollection
    @CollectionTable(
        name = "user_following",
        joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Long> following = new HashSet<>();

    public Followings(Set<Long> following) {

        if (Objects.isNull(following)) {
            throw new InvalidFollowingException();
        }

        this.following = following;
    }
}
