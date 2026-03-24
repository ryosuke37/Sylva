package io.github.ryosuke37.sylva.repository.specification;

import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class PostSpecification {
    public static Specification<PostEntity> parentPostIdIs(String parentPostId) {
        return new Specification<PostEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    @NonNull Root<PostEntity> root,
                    @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder criteriaBuilder
            ) {
                if (query != null && (query.getResultType() != Long.class)) {
                    root.fetch("user", JoinType.LEFT);
                }
                return parentPostId == null ?
                        criteriaBuilder.isNull(root.get("parentPost").get("id"))
                        : criteriaBuilder.equal(root.get("parentPost").get("id"), parentPostId);
            }
        };
    }

    public static Sort oderByCreatedDate(Sort.Direction direction) {
        return Sort.by(direction, "createdDate");
    }
}
