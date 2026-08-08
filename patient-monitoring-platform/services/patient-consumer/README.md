# patient-consumer

Consumer service that subscribes to the `patient-vitals` Kafka topic and processes patient vital events.

Quick start
- Build: .\services\patient-consumer\mvnw -f services\patient-consumer\pom.xml clean package
- Run (dev): .\services\patient-consumer\mvnw -f services\patient-consumer\pom.xml spring-boot:run
- Tests: .\services\patient-consumer\mvnw -f services\patient-consumer\pom.xml test

Notes
- This project uses package name `io.systemdesignlab.patientconsumer` (the original dashed package name was invalid).
- Configure Kafka bootstrap servers and topic in application.properties to match the producer (patient-api).
- Use the included Maven wrapper (mvnw/mvnw.cmd) and Java 21 as configured in the project.

See services/patient-consumer/HELP.md for more details.
