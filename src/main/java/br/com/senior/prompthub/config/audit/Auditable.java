package br.com.senior.prompthub.config.audit;

import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedDate
    @NoUpdateMapping
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedDate
    @NoUpdateMapping
    @Column(name = "updated_at")
    protected LocalDateTime lastModifiedDate;

}