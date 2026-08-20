# 🏥 Patient Monitoring Platform – Event-Driven Architecture with Spring Boot & Kafka-v1

A hands-on event-driven backend application demonstrating how patient vital data flows through a modern distributed architecture using **Spring Boot**, **Apache Kafka**, **Spring Data JPA**, and **PostgreSQL**.

The project is designed as a learning lab for developers transitioning from traditional CRUD applications to event-driven microservices.

---
## Why this project?

Many Spring Boot tutorials stop after demonstrating CRUD operations. This repository explores the next step—building a simple but realistic event-driven backend that illustrates how modern systems process events asynchronously while keeping business logic modular and maintainable.

---

## Architecture

```text
                REST API
                   │
                   ▼
         Spring Boot Producer
                   │
           Apache Kafka Topic
                   │
                   ▼
         Spring Boot Consumer
                   │
                   ▼
        Patient Vital Processor
                   │
      ┌────────────┼────────────┐
      ▼            ▼            ▼
 Validation   Classification   Actions
                                   │
                                   ▼
                              PostgreSQL
```

---

## Features

* REST API for publishing patient vital events
* Apache Kafka Producer & Consumer
* Topic-based asynchronous messaging
* Event validation
* Patient health classification
* Business processing pipeline
* Spring Data JPA persistence
* PostgreSQL integration
* Clean separation between Consumer, Processor, Repository, and Entity layers

---

## Tech Stack

* Java 21
* Spring Boot 3.5
* Apache Kafka 4.x
* Spring Kafka
* Spring Data JPA (Hibernate)
* PostgreSQL 17
* Docker Compose
* Maven

---

## Current Workflow

1. Client submits patient vital information
2. Producer publishes an event to Kafka
3. Consumer receives the event
4. Business processor

   * Validates data
   * Determines patient health status
   * Executes business actions
5. Patient data is persisted into PostgreSQL

---

## Current Status

✅ Kafka Producer

✅ Kafka Consumer

✅ Event Validation

✅ Business Processing

✅ Health Classification

✅ PostgreSQL Persistence

---

## Planned Enhancements

* Dead Letter Queue (DLQ)
* Retry mechanism
* Audit Service
* Alert Service
* Notification Service
* OpenTelemetry tracing
* Prometheus & Grafana monitoring
* Kafka Streams
* Idempotent consumers
* Integration testing with Testcontainers

---

## Learning Objectives

This project focuses on understanding:

* Event-Driven Architecture
* Asynchronous messaging
* Kafka fundamentals
* Consumer Groups & Partitions
* Separation of concerns
* Spring Boot best practices
* Repository pattern
* Scalable backend design

---

## Repository Structure

```text
services/
├── patient-producer
│
└── patient-consumer
    ├── consumer
    ├── processor
    ├── event
    ├── entity
    ├── repository
    ├── service
    └── config
```
