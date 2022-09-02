package com.jjikmuk.sikdorak.review.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
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
    @Column(name = "likeUser")
    private List<Long> likeUsers = new ArrayList<>();

    public boolean add(Long userId) {
        return likeUsers.add(userId);
    }

    public boolean remove(Long userId) {
        return likeUsers.remove(userId);
    }

    public boolean contains(Long userId) {
        return likeUsers.contains(userId);
    }

}
