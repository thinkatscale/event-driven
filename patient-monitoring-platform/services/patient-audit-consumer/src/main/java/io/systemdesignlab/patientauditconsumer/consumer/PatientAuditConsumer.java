package io.systemdesignlab.patientauditconsumer.consumer;

import io.systemdesignlab.patientauditconsumer.event.PatientVitalRecordedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PatientAuditConsumer {

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
    }
}