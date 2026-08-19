package io.systemdesignlab.patientauditconsumer.consumer;

import io.systemdesignlab.patientauditconsumer.event.PatientVitalRecordedEvent;
import io.systemdesignlab.patientauditconsumer.service.AuditService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PatientAuditConsumer {

    private final AuditService auditService;

    public PatientAuditConsumer(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(
            topics = "${kafka.topic.patient-vitals}"
    )
    public void consume(PatientVitalRecordedEvent event) {

        System.out.println("--------------------------------");
        System.out.println("AUDIT EVENT RECEIVED");
        System.out.println("--------------------------------");

        System.out.println("Event Id   : " + event.eventId());
        System.out.println("Patient Id : " + event.patientId());
        System.out.println("Heart Rate : " + event.heartRate());
        System.out.println("Oxygen     : " + event.oxygen());
        System.out.println("Temperature: " + event.temperature());

        auditService.record(event);
    }
}