package io.systemdesignlab.patientconsumer.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import io.systemdesignlab.patientconsumer.event.PatientVitalRecordedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public DefaultErrorHandler errorHandler(
            KafkaTemplate<String, PatientVitalRecordedEvent> dlqKafkaTemplate)
    {


        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        dlqKafkaTemplate,
                        (record, ex) -> {

                            System.out.println("DLQ Recoverer Invoked");

                            return new TopicPartition(
                                    "patient-vitals-dlt",
                                    record.partition());
                        });

        recoverer.setFailIfSendResultIsError(true);
        recoverer.setWaitForSendResultTimeout(Duration.ofSeconds(10));
        return new DefaultErrorHandler(
                recoverer,
                new FixedBackOff(2000L,3));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PatientVitalRecordedEvent>
    kafkaListenerContainerFactory(
            ConsumerFactory<String, PatientVitalRecordedEvent> consumerFactory,
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, PatientVitalRecordedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    @Bean
    public ProducerFactory<String, PatientVitalRecordedEvent> dlqProducerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);

        props.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        props.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, PatientVitalRecordedEvent> dlqKafkaTemplate() {

        return new KafkaTemplate<>(dlqProducerFactory());

    }
}