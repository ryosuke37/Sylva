package io.github.ryosuke37.sylva.repository.specification;

import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class PostSpecification {
    public static Specification<PostEntity> fetchUser() {
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
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<PostEntity> idIs(String id) {
        return new Specification<PostEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    @NonNull Root<PostEntity> root,
                    @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder criteriaBuilder
            ) {
                return id == null ?
                        criteriaBuilder.isNull(root.get("id"))
                        : criteriaBuilder.equal(root.get("id"), id);
            }
        };
    }

    public static Specification<PostEntity> parentPostIdIs(String parentPostId) {
        return new Specification<PostEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    @NonNull Root<PostEntity> root,
                    @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder criteriaBuilder
            ) {
                return parentPostId == null ?
                        criteriaBuilder.isNull(root.get("parentPost").get("id"))
                        : criteriaBuilder.equal(root.get("parentPost").get("id"), parentPostId);
            }
        };
    }

    public static Specification<PostEntity> rootPostIdIs(String rootPostId) {
        return new Specification<PostEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    @NonNull Root<PostEntity> root,
                    @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder criteriaBuilder
            ) {
                return rootPostId == null ?
                        criteriaBuilder.isNull(root.get("rootPost").get("id"))
                        : criteriaBuilder.equal(root.get("rootPost").get("id"), rootPostId);
            }
        };
    }

    public static Specification<PostEntity> createdDateBetween(LocalDateTime since, LocalDateTime until) {
        LocalDateTime sinceDateTime = since == null ? LocalDateTime.MIN : since;
        LocalDateTime untilDateTime = until == null ? LocalDateTime.MAX : until;
        return new Specification<PostEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    @NonNull Root<PostEntity> root,
                    @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder criteriaBuilder
            ) {
                return criteriaBuilder.between(root.get("createdDate"), sinceDateTime, untilDateTime);
            }
        };
    }

    public static Sort oderByCreatedDate(Sort.Direction direction) {
        return Sort.by(direction, "createdDate");
    }
}
