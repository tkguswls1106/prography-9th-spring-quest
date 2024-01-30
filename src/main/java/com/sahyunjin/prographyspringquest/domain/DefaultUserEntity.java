package com.sahyunjin.prographyspringquest.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultUserEntity {

    @CreatedDate
    private String created_at;

    @LastModifiedDate
    protected String updated_at;


    @PrePersist  // 해당 엔티티를 저장하기 이전에 실행된다.
    public void onPrePersist(){
        this.created_at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.forLanguageTag("ko")));
        this.updated_at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.forLanguageTag("ko")));
    }

    @PreUpdate  // 해당 엔티티를 업데이트 하기 이전에 실행된다.
    public void onPreUpdate(){
        this.updated_at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.forLanguageTag("ko")));
    }
}
