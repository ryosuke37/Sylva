package io.github.ryosuke37.sylva.repository;

import io.github.ryosuke37.sylva.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  UserRepository extends JpaRepository<UserEntity, String> {
    public List<UserEntity> findByEmailAddress(String emailAddress);

    public List<UserEntity> findByHandle(String handle);
}
