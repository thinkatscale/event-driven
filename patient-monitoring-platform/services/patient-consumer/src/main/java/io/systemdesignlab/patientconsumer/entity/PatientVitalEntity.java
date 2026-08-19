package io.systemdesignlab.patientconsumer.entity;

import io.systemdesignlab.patientconsumer.processor.PatientVitalStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import  lombok.NoArgsConstructor;

@Entity
@Table(name = "patient_vitals")
@Getter
@Setter
@NoArgsConstructor

public class PatientVitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Integer heartRate;

    @Column(nullable = false)
    private Integer oxygen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientVitalStatus status;

    @Column(nullable = false)
    private LocalDateTime receivedAt;

    // Getters & Setters
}