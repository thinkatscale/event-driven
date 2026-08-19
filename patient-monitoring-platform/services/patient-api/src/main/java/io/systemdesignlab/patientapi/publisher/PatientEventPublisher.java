package io.systemdesignlab.patientapi.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.systemdesignlab.patientapi.event.PatientVitalRecordedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientEventPublisher
{
    @Value("${kafka.topic.patient-vitals}")
    private String TOPIC;

    private final KafkaTemplate<String, PatientVitalRecordedEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public PatientEventPublisher(KafkaTemplate<String, PatientVitalRecordedEvent> kafkaTemplate, ObjectMapper objectMapper)
    {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(PatientVitalRecordedEvent event) throws JsonProcessingException {
        int nPartitionKey=
        Integer.parseInt (String.valueOf(event.patientId()%3));
        //kafkaTemplate.send(TOPIC, String.valueOf(nPartitionKey), objectMapper.writeValueAsString(event));
        kafkaTemplate.send(TOPIC,String.valueOf(nPartitionKey),event);
    }
}