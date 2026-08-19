package io.systemdesignlab.patientapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.systemdesignlab.patientapi.dto.PatientVitalRequest;
import io.systemdesignlab.patientapi.event.PatientVitalRecordedEvent;
import io.systemdesignlab.patientapi.publisher.PatientEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientVitalService {
    private final PatientEventPublisher producer;

    //dep0 inj
    public PatientVitalService(PatientEventPublisher patientProducer) {
        this.producer = patientProducer;

    }

    //recordVital
    public void recordVital(Long patientId,PatientVitalRequest request) throws JsonProcessingException {
        PatientVitalRecordedEvent event = new PatientVitalRecordedEvent(
                UUID.randomUUID(),
                //UUID.fromString("11111111-1111-1111-1111-111111111111"),
                patientId,
                request.heartRate(),
                request.oxygen(),
                request.temperature()
        );
        producer.publish(event);
    }
}