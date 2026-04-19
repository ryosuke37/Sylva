package io.github.ryosuke37.sylva.repository.entity;

import io.github.ryosuke37.sylva.repository.annotation.GenerateUuidV7;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_post_id")
    private PostEntity rootPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_post_id")
    private PostEntity parentPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quoted_post_id")
    private PostEntity quotedPost;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public boolean hasParent(){
        return parentPost != null;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}