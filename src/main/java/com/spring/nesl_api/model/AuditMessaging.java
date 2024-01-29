package com.spring.nesl_api.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="auditMessage")
public class AuditMessaging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String actionName;
    private String controllerName;
    private String senderName;
    private String clientId;
    private String companyId;
    @Embedded
    private Content content;
}
