package com.jjikmuk.sikdorak.review.command.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "likes",
        joinColumns = @JoinColumn(name = "review_id")
    )
    @OrderColumn(name = "idx")
    @Column(name = "like_user")
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

    public long size() {
        return likeUsers.size();
    }
}
