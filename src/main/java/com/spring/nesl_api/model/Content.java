package com.spring.nesl_api.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public
class Content {
    private String key1;
    private String key2;
}
