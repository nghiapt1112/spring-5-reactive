package com.nghia.reactive.web.ng.reactive.web.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseEntity implements Serializable {
    @Id
    private String id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Date updatedDate;

    @Version
    private Long version;

    private Boolean delete = Boolean.FALSE;

}
