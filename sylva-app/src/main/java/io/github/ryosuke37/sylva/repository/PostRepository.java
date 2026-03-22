package io.github.ryosuke37.sylva.repository;

import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String>, JpaSpecificationExecutor<PostEntity> {
}
