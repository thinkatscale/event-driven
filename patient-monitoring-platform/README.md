# Patient Monitoring Platform

Monorepo for services that publish and consume patient vital events over Kafka.

Services
- services/patient-api — Spring Boot (Java 21) service that accepts patient vitals and publishes JSON events to Kafka (topic: `patient-vitals`). See services/patient-api/HELP.md for details.
- services/patient-consumer — Consumer service for processing patient-vitals events.

Quick start (patient-api)
- Build: .\services\patient-api\mvnw -f services\patient-api\pom.xml clean package
- Run (dev): .\services\patient-api\mvnw -f services\patient-api\pom.xml spring-boot:run
- Tests: .\services\patient-api\mvnw -f services\patient-api\pom.xml test
- Single test class: .\services\patient-api\mvnw -f services\patient-api\pom.xml -Dtest=PatientApiApplicationTests test
- Docker Compose (Kafka + UI): docker compose -f services\patient-api\compose.yml up

Notes
- Kafka topic name: `patient-vitals` (configured via kafka.topic.patient-vitals and a NewTopic bean in the patient-api service).
- Use the included Maven wrapper (mvnw/mvnw.cmd) and Java 21 as configured in the project.

See services/*/HELP.md for more service-specific instructions.
