package com.jjikmuk.sikdorak.review.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @ElementCollection
    @CollectionTable(
        name = "likes",
        joinColumns = @JoinColumn(name = "review_id")
    )
    private Set<Long> likes = new HashSet<>();

    public boolean add(Long userId) {
        return likes.add(userId);
    }

    public boolean remove(Long userId) {
        return likes.remove(userId);
    }

    public boolean contains(Long userId) {
        return likes.contains(userId);
    }

}
