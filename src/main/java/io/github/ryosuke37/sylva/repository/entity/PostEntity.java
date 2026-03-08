package io.github.ryosuke37.sylva.repository.entity;

import io.github.ryosuke37.sylva.repository.annotation.GenerateUuidV7;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class PostEntity {
    @Id
    @Column
    @GenerateUuidV7
    private String id;

    @Column
    private String content;

    @Column
    private String userId;

    @Column
    private String rootPostId;

    @Column
    private String parentPostId;

    @Column
    private String quotedPostId;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updatedDate;
}