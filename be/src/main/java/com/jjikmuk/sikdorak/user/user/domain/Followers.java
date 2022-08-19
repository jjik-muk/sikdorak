package com.jjikmuk.sikdorak.user.user.domain;

import com.jjikmuk.sikdorak.user.user.exception.InvalidFollowersException;
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
public class Followers {

    @ElementCollection
    @CollectionTable(
        name = "user_followers",
        joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Long> follower = new HashSet<>();

    public Followers(Set<Long> follower) {
        if (Objects.isNull(follower)) {
            throw new InvalidFollowersException();
        }

        this.follower = follower;
    }
}
