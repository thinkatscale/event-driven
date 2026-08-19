package io.systemdesignlab.patientauditconsumer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_events")
@Getter
@Setter
@NoArgsConstructor
public class AuditEventEntity {

    @Id
    private UUID eventId;

    private Long patientId;

    private Integer heartRate;

    private Integer oxygen;

    private Double temperature;

    private LocalDateTime receivedAt;

    public AuditEventEntity(
            UUID eventId,
            Long patientId,
            Integer heartRate,
            Integer oxygen,
            Double temperature,
            LocalDateTime receivedAt) {

        this.eventId = eventId;
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.receivedAt = receivedAt;
    }
}