# Copilot instructions for patient-monitoring-platform

This repository contains a single service "patient-api" (Spring Boot / Java 21) that publishes patient vitals to Kafka.

Build, test, lint, and run

- Build: ./services/patient-api/mvnw -f services/patient-api/pom.xml clean package
- Run locally (dev): ./services/patient-api/mvnw -f services/patient-api/pom.xml spring-boot:run
- Run tests: ./services/patient-api/mvnw -f services/patient-api/pom.xml test
- Run a single test class: ./services/patient-api/mvnw -f services/patient-api/pom.xml -Dtest=PatientApiApplicationTests test
- Run with Docker Compose (Kafka + UI): docker compose -f services/patient-api/compose.yml up

High-level architecture

- service: services/patient-api
  - Spring Boot application (io.systemdesignlab.patientapi.PatientApiApplication)
  - REST API: POST /patients/{patientId}/vitals accepts JSON matching PatientVitalRequest (heartRate, oxygen, temperature)
  - Service layer: PatientVitalService constructs a PatientVitalRecordedEvent and delegates to PatientEventPublisher
  - Publisher: PatientEventPublisher serializes events to JSON (Jackson) and sends them to a Kafka topic configured via kafka.topic.patient-vitals
  - Kafka: A NewTopic bean declares topic "patient-vitals"; application.properties configures bootstrap servers and serializers
  - Tests: A basic SpringBootTest exists that validates context loads

Key conventions & repository specifics

- Maven wrapper present: Use the included mvnw / mvnw.cmd to ensure consistent Maven across environments.
- Java version: project uses Java 21 (pom.xml property java.version)
- Kafka topic name is wired both in application.properties (kafka.topic.patient-vitals) and via a NewTopic bean (patient-vitals). Keep those in sync when changing names.
- DTOs / Events use Java record types for immutable payloads (e.g., PatientVitalRequest, PatientVitalRecordedEvent).
- Publisher uses KafkaTemplate<String,String> and Jackson ObjectMapper; messages are produced as serialized JSON strings.
- Service / controller wiring relies on constructor injection (no field injection).

Files to consult for deeper context

- services/patient-api/HELP.md — local run & reference notes
- services/patient-api/pom.xml — dependencies and build plugin
- services/patient-api/compose.yml — local Kafka + kafka-ui config
- services/patient-api/src/main/resources/application.properties — runtime config (ports, kafka bootstrap, topic key)

AI assistant hints

- When suggesting code changes that touch messaging contracts, update both the record event class and any consumers (not present in this repo) and application.properties.
- Prefer edit only files under services/patient-api unless a repo-level change is required.
- For running tests that depend on Kafka, use the compose.yml to bring up Kafka first; spring-kafka-test is available for unit/integration tests.
- Keep commits small and focused. Include the Co-authored-by trailer if an automated agent created the changes.

MCP Servers

- Would you like an MCP server configured for Playwright or other test runners? This repo appears backend-only with Kafka; consider configuring a Kafka-enabled MCP server if desired.

Summary

Created .github/copilot-instructions.md describing build/test/run commands, high-level architecture, and key repository conventions. Want changes or additional coverage (e.g., CI, deployment, or consumer contracts)?
