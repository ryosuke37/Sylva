package io.github.ryosuke37.sylva.repository.entity;

import io.github.ryosuke37.sylva.common.enums.Country;
import io.github.ryosuke37.sylva.common.enums.Language;
import io.github.ryosuke37.sylva.repository.annotation.GenerateUuidV7;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Accessors(chain = true)
public class UserEntity {
    @Id
    @Column
    @GenerateUuidV7
    private String id;

    @Column
    private String handle;

    @Column
    private String nickname;

    @Column
    private String emailAddress;

    @Column
    private String hashedPassword;

    @Column
    private Country country;

    @Column
    private Language language;

    @Column
    private String selfIntroduction;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts;
}