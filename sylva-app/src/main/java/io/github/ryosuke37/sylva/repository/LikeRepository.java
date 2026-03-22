package io.github.ryosuke37.sylva.repository;

import io.github.ryosuke37.sylva.repository.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, String> {
}

