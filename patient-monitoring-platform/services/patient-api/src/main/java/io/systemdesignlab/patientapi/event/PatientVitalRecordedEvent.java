package io.systemdesignlab.patientapi.event;

public record PatientVitalRecordedEvent(

        Long patientId,

        Integer heartRate,

        Integer oxygen,

        Double temperature

) {
}