package io.systemdesignlab.patientconsumer.service;
import org.springframework.stereotype.Component;
import io.systemdesignlab.patientconsumer.event.PatientVitalRecordedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Component
@Service
public class PatientVitalProcessingService
{
    private final io.systemdesignlab.patientconsumer.processor.PatientVitalProcessor processor;

    public PatientVitalProcessingService(io.systemdesignlab.patientconsumer.processor.PatientVitalProcessor processor) {
        this.processor = processor;
    }
    @Transactional
    public void processPatientVital(PatientVitalRecordedEvent event)
    {

        System.out.println("--------------------------------");
        System.out.println("Patient Event Received");
        System.out.println("--------------------------------");
        System.out.println("Event Id   : " + event.eventId());
        System.out.println("Patient Id : " + event.patientId());
        System.out.println("Heart Rate : " + event.heartRate());
        System.out.println("Oxygen     : " + event.oxygen());
        System.out.println("Temperature: " + event.temperature());
        processor.process(event);


    }
}
