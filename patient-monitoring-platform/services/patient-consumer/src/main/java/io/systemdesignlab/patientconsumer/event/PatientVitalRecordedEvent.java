package io.systemdesignlab.patientconsumer.event;

public record PatientVitalRecordedEvent
(
    Long patientId,
    Integer heartRate,
    Integer oxygen,
    Double temperature
)
{
}



