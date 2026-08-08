package io.systemdesignlab.patientconsumer.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.systemdesignlab.patientconsumer.entity.PatientVitalEntity;
import io.systemdesignlab.patientconsumer.entity.ProcessedEventEntity;
import io.systemdesignlab.patientconsumer.event.PatientVitalRecordedEvent;
import io.systemdesignlab.patientconsumer.repository.PatientVitalRepository;
import io.systemdesignlab.patientconsumer.repository.ProcessedEventRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PatientVitalProcessor
{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String OUTPUT_BASE_DIR =
            "C:\\success\\study\\Claude assisted system design prep\\data-engineering\\patient-monitoring-platform\\services\\patient-consumer\\patient-vitals";
    private static final DateTimeFormatter FOLDER_TS_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");


    public static final int MIN_HEART_RATE = 1;
    public static final int MAX_HEART_RATE = 220;
    public static final int MIN_OXYGEN = 1;
    public static final int MAX_OXYGEN = 100;
    public static final double MIN_TEMPERATURE = 35.0;
    public static final double MAX_TEMPERATURE = 42.0;

    private final PatientVitalRepository repository;

    private final ProcessedEventRepository processedEventRepository;
    public PatientVitalProcessor(PatientVitalRepository repository, ProcessedEventRepository processedEventRepository) {
        this.repository = repository;
        this.processedEventRepository = processedEventRepository;
    }
    public void process(PatientVitalRecordedEvent event)
    {
        //validate the event
        PatientVitalValidationStatus validationStatus = validate(event);
        System.out.println("Validation Status = " + validationStatus);
        if (validationStatus != PatientVitalValidationStatus.VALID) {
            System.out.println("Invalid event: " + validationStatus);
            return;
        }
        //classify the event
        PatientVitalStatus status = classify(event);

        //act on the event
        takeAction(status, event);
    }

    private void takeAction(PatientVitalStatus vitalStatus, PatientVitalRecordedEvent event)
    {
        //possible status as normal(persist), warning(persiste, audit), critical(persiste, audit,alert)
        switch (vitalStatus)
        {
            case NORMAL:
                persist(event,vitalStatus);
                break;
            case WARNING:
                persist(event,vitalStatus);
                audit(event);
                break;
            case CRITICAL:
                persist(event,vitalStatus);
                audit(event);
                alert(event);
                break;
        }

    }

    private void alert(PatientVitalRecordedEvent event) {
        
    }

    private void audit(PatientVitalRecordedEvent event) {
    }

    private void persist_old(PatientVitalRecordedEvent event) {
        System.out.println("inside persist method" + event);
        writeEventToFile(event);
    }

    private void persist(
            PatientVitalRecordedEvent event,
            PatientVitalStatus status)
    {
        if (processedEventRepository.existsById(event.eventId())) {

            System.out.println("--------------------------------");
            System.out.println("Duplicate Event Detected");
            System.out.println("Event Id : " + event.eventId());
            System.out.println("--------------------------------");

            return;
        }
        PatientVitalEntity entity = new PatientVitalEntity();

        entity.setPatientId(event.patientId());
        entity.setHeartRate(event.heartRate());
        entity.setOxygen(event.oxygen());
        entity.setTemperature(event.temperature());
        entity.setStatus(status);
        entity.setReceivedAt(LocalDateTime.now());

        repository.save(entity);
        ProcessedEventEntity processedEvent = new ProcessedEventEntity();

        processedEvent.setEventId(event.eventId());
        processedEvent.setProcessedAt(LocalDateTime.now());

        processedEventRepository.save(processedEvent);

        System.out.println("--------------------------------");
        System.out.println("Event Processed Successfully");
        System.out.println("Event Id : " + event.eventId());
        System.out.println("--------------------------------");

    }

    /**
     * Serialises the event to JSON and writes it under:
     * baseDir/patientId/yyyyMMdd-HHmmss/patient-vitals.txt
     */
    private void writeEventToFile(PatientVitalRecordedEvent event)
    {
        try
        {
            String json = OBJECT_MAPPER.writeValueAsString(event);
            String patientFolder = String.valueOf(event.patientId());
            String timeFolder = LocalDateTime.now().format(FOLDER_TS_FORMATTER);
            Path outputDir = Paths.get(OUTPUT_BASE_DIR, timeFolder,patientFolder);
            Files.createDirectories(outputDir);

            Path outputFile = outputDir.resolve("patient-vitals.txt");
            try (BufferedWriter writer = Files.newBufferedWriter(
                    outputFile,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND))
            {
                writer.write(json);
                writer.newLine();
            }
            System.out.println("Event written to file: " + outputFile + " | " + json);
            System.out.flush();
        }
        catch (IOException e)
        {
            System.err.println("Failed to write event to file: " + e.getMessage());
        }
    }

    private PatientVitalValidationStatus validate(PatientVitalRecordedEvent event)
    {
        if (event == null || event.patientId() == null || event.patientId() <= 0)
        {
            return  PatientVitalValidationStatus.INVALID_PATIENT_ID;
        }

        if (event.temperature() == null
                || event.temperature() < MIN_TEMPERATURE
                || event.temperature() > MAX_TEMPERATURE)
        {
            return PatientVitalValidationStatus.INVALID_TEMPERATURE;
        }

        if (event.heartRate() == null
            || event.heartRate() < MIN_HEART_RATE
            || event.heartRate() > MAX_HEART_RATE)
        {
            return PatientVitalValidationStatus.INVALID_HEART_RATE;
        }

        if (event.oxygen() == null
            || event.oxygen() < MIN_OXYGEN
            || event.oxygen() > MAX_OXYGEN)
        {
            return PatientVitalValidationStatus.INVALID_OXYGEN;
        }

        return PatientVitalValidationStatus.VALID;
    }

    private PatientVitalStatus classify(PatientVitalRecordedEvent event)
    {
        return maxSeverity(
                classifyOxygen(event),
                classifyHeartRate(event),
                classifyTemperature(event)
        );
    }

    private PatientVitalStatus classifyOxygen(PatientVitalRecordedEvent event)
    {
        if (event.oxygen() < 90)
        {
            return PatientVitalStatus.CRITICAL;
        }
        if (event.oxygen() <= 94)
        {
            return PatientVitalStatus.WARNING;
        }
        return PatientVitalStatus.NORMAL;
    }

    private PatientVitalStatus classifyHeartRate(PatientVitalRecordedEvent event)
    {
        if (event.heartRate() < 50 || event.heartRate() > 120)
        {
            return PatientVitalStatus.CRITICAL;
        }
        if ((event.heartRate() >= 50 && event.heartRate() <= 59)
            || (event.heartRate() >= 101 && event.heartRate() <= 120))
        {
            return PatientVitalStatus.WARNING;
        }
        return PatientVitalStatus.NORMAL;
    }

    private PatientVitalStatus classifyTemperature(PatientVitalRecordedEvent event)
    {
        if (event.temperature() < 36 || event.temperature() > 39)
        {
            return PatientVitalStatus.CRITICAL;
        }
        if (event.temperature() > 38 && event.temperature() <= 39)
        {
            return PatientVitalStatus.WARNING;
        }
        return PatientVitalStatus.NORMAL;
    }

    private PatientVitalStatus maxSeverity(
            PatientVitalStatus... statuses)
    {
        PatientVitalStatus maxSeverity = PatientVitalStatus.NORMAL;
        for (PatientVitalStatus status : statuses)
        {
            if (status.ordinal() > maxSeverity.ordinal())
            {
                maxSeverity = status;
            }
        }
        return maxSeverity;
    }
}
