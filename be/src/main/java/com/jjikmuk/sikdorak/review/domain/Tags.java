package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidTagsException;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor
public class Tags {

    private static final int LIMIT_SIZE = 30;

    private Set<Tag> tags;

    public Tags(List<String> tags) {
        if (Objects.isNull(tags)) {
            throw new InvalidTagsException();
        }

        Set<Tag> filteredTags = tags.stream()
                .map(Tag::new)
                .collect(Collectors.toSet());

        if (filteredTags.size() > LIMIT_SIZE) {
            throw new InvalidTagsException();
        }

        this.tags = filteredTags;
    }

    public int size() {
        return tags.size();
    }
}
