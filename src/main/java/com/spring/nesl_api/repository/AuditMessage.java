package com.spring.nesl_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditMessage extends JpaRepository<AuditMessage,Long> {
}
