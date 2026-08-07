# patient-api

Spring Boot (Java 21) service that accepts patient vitals and publishes JSON events to Kafka (topic: `patient-vitals`). See HELP.md for more details and references.

Quick start
- Build: .\services\patient-api\mvnw -f services\patient-api\pom.xml clean package
- Run (dev): .\services\patient-api\mvnw -f services\patient-api\pom.xml spring-boot:run
- Tests: .\services\patient-api\mvnw -f services\patient-api\pom.xml test
- Single test class: .\services\patient-api\mvnw -f services\patient-api\pom.xml -Dtest=PatientApiApplicationTests test
- Docker Compose (Kafka + UI): docker compose -f services\patient-api\compose.yml up

Notes
- Kafka topic name: `patient-vitals` (configured via kafka.topic.patient-vitals and a NewTopic bean).
- Messages are produced as JSON strings using Jackson and KafkaTemplate<String,String>.
- Use the included Maven wrapper (mvnw/mvnw.cmd) and Java 21 as configured in the project.

For deeper info and references, read services/patient-api/HELP.md.
