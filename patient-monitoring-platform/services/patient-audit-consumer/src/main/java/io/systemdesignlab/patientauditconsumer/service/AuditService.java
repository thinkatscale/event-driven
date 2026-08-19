package io.systemdesignlab.patientauditconsumer.service;

import io.systemdesignlab.patientauditconsumer.entity.AuditEventEntity;
import io.systemdesignlab.patientauditconsumer.event.PatientVitalRecordedEvent;
import io.systemdesignlab.patientauditconsumer.repository.AuditEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditEventRepository repository;

    public AuditService(AuditEventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void record(PatientVitalRecordedEvent event) {

        AuditEventEntity auditEvent = new AuditEventEntity(
                event.eventId(),
                event.patientId(),
                event.heartRate(),
                event.oxygen(),
                event.temperature(),
                LocalDateTime.now()
        );

        repository.save(auditEvent);

        System.out.println("--------------------------------");
        System.out.println("AUDIT EVENT PERSISTED");
        System.out.println("Event Id : " + event.eventId());
        System.out.println("--------------------------------");
    }
}