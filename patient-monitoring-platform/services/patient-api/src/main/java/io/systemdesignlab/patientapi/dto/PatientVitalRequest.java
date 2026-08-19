package io.systemdesignlab.patientapi.dto;

public record PatientVitalRequest(

        Integer heartRate,

        Integer oxygen,

        Double temperature

) {
}