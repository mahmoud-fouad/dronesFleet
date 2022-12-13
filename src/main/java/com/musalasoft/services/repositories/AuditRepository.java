package com.musalasoft.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musalasoft.entities.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

}
