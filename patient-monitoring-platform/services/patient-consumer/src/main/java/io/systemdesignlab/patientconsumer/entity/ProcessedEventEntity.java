package io.systemdesignlab.patientconsumer.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import  lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_events")
@Getter
@Setter
@NoArgsConstructor
public class ProcessedEventEntity {

    @Id
    private UUID eventId;

    private LocalDateTime processedAt;

}
