package com.getir.readingisgood.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable
{
    @Column(nullable = false, name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Version
    @Column(nullable = false, name = "VERSION")
    private Integer version;

    @Column(nullable = false, name = "ACTIVE")
    @Builder.Default
    private Boolean active = true;

    @PreUpdate
    private void preUpdate()
    {
        setUpdateTime(LocalDateTime.now());
    }

    @PrePersist
    private void prePersist()
    {
        setCreateTime(LocalDateTime.now());
    }

}
