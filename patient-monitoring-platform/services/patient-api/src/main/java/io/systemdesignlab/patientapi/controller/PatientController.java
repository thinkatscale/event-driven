package io.systemdesignlab.patientapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.systemdesignlab.patientapi.dto.PatientVitalRequest;
import io.systemdesignlab.patientapi.service.PatientVitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {


    private final PatientVitalService service;
    public PatientController( PatientVitalService service) {
        this.service=service;
    }

    @PostMapping("/{patientId}/vitals")
    public ResponseEntity<Void> publish(
            @PathVariable Long patientId,
            @RequestBody PatientVitalRequest request) throws JsonProcessingException {

        service.recordVital(patientId,request);

        return ResponseEntity.accepted().build();
    }
}