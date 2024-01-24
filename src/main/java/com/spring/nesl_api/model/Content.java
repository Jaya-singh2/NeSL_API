package com.spring.nesl_api.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public
class Content {
    private String key1;
    private String key2;
}
