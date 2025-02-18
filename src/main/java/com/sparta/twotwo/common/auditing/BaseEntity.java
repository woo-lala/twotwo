package com.sparta.twotwo.common.auditing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity extends AuditableEntity{
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @Column(name = "is_hidden")
    private Boolean isHidden = false;
}
