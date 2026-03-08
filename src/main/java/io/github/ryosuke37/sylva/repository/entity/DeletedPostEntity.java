package io.github.ryosuke37.sylva.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_posts")
@Data
public class DeletedPostEntity {
    @Id
    @Column
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

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime deletedDate;
}