package io.github.ryosuke37.sylva.repository.entity;

import io.github.ryosuke37.sylva.common.enums.Country;
import io.github.ryosuke37.sylva.common.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_users")
@Data
public class DeletedUserEntity {
    @Id
    @Column
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
    private String phoneNumber;

    @Column
    private Country country;

    @Column
    private Language language;

    @Column
    private String selfIntroduction;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime deletedDate;
}