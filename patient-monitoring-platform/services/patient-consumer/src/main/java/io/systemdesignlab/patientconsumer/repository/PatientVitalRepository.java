package io.systemdesignlab.patientconsumer.repository;

import io.systemdesignlab.patientconsumer.entity.PatientVitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVitalRepository
        extends JpaRepository<PatientVitalEntity, Long> {

}