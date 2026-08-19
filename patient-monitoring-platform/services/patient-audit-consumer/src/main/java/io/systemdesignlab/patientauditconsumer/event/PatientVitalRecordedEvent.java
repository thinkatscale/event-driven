package io.systemdesignlab.patientauditconsumer.event;

import java.util.UUID;

public record PatientVitalRecordedEvent(
        UUID eventId,
        Long patientId,
        Integer heartRate,
        Integer oxygen,
        Double temperature
) {
}