package com.jjikmuk.sikdorak.user.user.domain;

import java.util.HashSet;
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

    public boolean isConnected(Long userId) {
        return follower.contains(userId);
    }
}
