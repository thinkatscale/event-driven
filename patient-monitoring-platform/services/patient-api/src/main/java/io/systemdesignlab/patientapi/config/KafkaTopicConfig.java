package io.systemdesignlab.patientapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic patientVitalTopic() {
        return new NewTopic("patient-vitals", 1, (short) 1);
    }

}