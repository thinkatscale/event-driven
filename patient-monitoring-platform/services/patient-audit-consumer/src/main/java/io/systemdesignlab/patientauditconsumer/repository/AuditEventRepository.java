package io.systemdesignlab.patientauditconsumer.repository;

import io.systemdesignlab.patientauditconsumer.entity.AuditEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditEventRepository
        extends JpaRepository<AuditEventEntity, UUID> {
}