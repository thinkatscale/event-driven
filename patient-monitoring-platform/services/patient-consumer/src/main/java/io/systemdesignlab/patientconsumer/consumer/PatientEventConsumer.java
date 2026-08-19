package io.systemdesignlab.patientconsumer.consumer;

import io.systemdesignlab.patientconsumer.event.PatientVitalRecordedEvent;
import io.systemdesignlab.patientconsumer.service.PatientVitalProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PatientEventConsumer {
    private final PatientVitalProcessingService patientVitalProcessingService;
    public PatientEventConsumer(PatientVitalProcessingService patientVitalProcessingService) {
        this.patientVitalProcessingService = patientVitalProcessingService;
    }
   
    @KafkaListener(
            topics = "${kafka.topic.patient-vitals}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PatientVitalRecordedEvent event) {
        patientVitalProcessingService.processPatientVital(event);
    }

}
