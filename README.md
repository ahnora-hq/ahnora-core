<p align="center">
  <img src="brand/ahnora-logo.svg" alt="Ahnora logo" width="96">
</p>

# ⚡ Ahnora

> **Open intelligence. Built together.**
>
> **Ahnora is an open-source platform for building, orchestrating, and operating AI agents.**

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.7-black.svg)](https://kafka.apache.org/)
[![Organization](https://img.shields.io/badge/GitHub-agentforge--hq-blueviolet.svg)](https://github.com/agentforge-hq)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

---

## 🎯 Overview

**Ahnora** enables organizations to define, version, deploy, and monitor AI agents through a controlled, multi-tenant platform rather than building bespoke agent infrastructure for every use case.

> 🚧 **Project status:** Ahnora is under active development (Phase 0/1 of the [Roadmap](docs/01-product/ROADMAP.md)).
> The goals below describe the platform's target architecture; consult the roadmap for what is implemented today.

### Design goals

* 🏢 **True Multi-Tenancy:** Hardened tenant isolation enforced at the context holder, database queries, and messaging boundaries.
* 📜 **Immutable Agent Versions:** Published agent configurations are frozen and reproducible—never mutating in place.
* 📡 **Event-Driven Architecture:** Asynchronous event pipelines adhering to CloudEvents envelopes over Apache Kafka.
* 🛡️ **Zero-Trust Security & Tool Guardrails:** Server-side authorization, SSRF protection for HTTP tools, and defense against prompt injection.
* 🔍 **RAG & Knowledge Integration:** Modular search and vector indexing abstractions.
* ⚡ **Streaming Execution:** Real-time token and step telemetry delivered via Server-Sent Events (SSE).

---

## 🏗️ Architecture & Modules

The platform is organized into clean, decoupled bounded contexts:

```text
src/
├── ahnora-parent/          # Root BOM and build configuration
├── ahnora-common/          # Shared kernel: TenantContext, CloudEvents, errors & filters
├── ahnora-identity/        # Organizations, Users, Memberships, API Keys, and Auth
├── ahnora-agent/           # Agent definitions, versions, and prompt templates
├── ahnora-execution/       # Workflow execution runtime & state machines
└── ahnora-knowledge/       # Document ingestion and vector search pipelines
```

---

## 🚀 Quick Start (Local Development)

### Prerequisites
* **Java 21** or later
* **Maven 3.9+**
* **Docker & Docker Compose**

### 1. Start Infrastructure
```bash
docker compose up -d
```
Starts PostgreSQL, Redis, and Apache Kafka locally.

### 2. Build and Run Tests
```bash
mvn clean test
```

---

## 📚 Source of Truth & Documentation

Ahnora enforces strict architectural governance. The documentation hierarchy defines system invariants:

1. **Constitutional Invariants:** [`docs/00-constitution/PROJECT_CONSTITUTION.md`](docs/00-constitution/PROJECT_CONSTITUTION.md)
2. **Engineering Principles:** [`docs/00-constitution/ENGINEERING_PRINCIPLES.md`](docs/00-constitution/ENGINEERING_PRINCIPLES.md)
3. **Security Architecture:** [`docs/02-architecture/SECURITY_ARCHITECTURE.md`](docs/02-architecture/SECURITY_ARCHITECTURE.md)
4. **REST API Contracts:** [`docs/04-contracts/REST_API.md`](docs/04-contracts/REST_API.md)
5. **Event Contracts:** [`docs/04-contracts/EVENTS.md`](docs/04-contracts/EVENTS.md)
6. **Error Codes:** [`docs/04-contracts/ERROR_CODES.md`](docs/04-contracts/ERROR_CODES.md)
7. **Task Tracking Roadmap:** [`tasks/README.md`](tasks/README.md)

---

## 🌐 Ecosystem Roadmap

> Repository names below follow the Ahnora naming. The project is currently hosted at
> [`github.com/agentforge-hq/agentforge`](https://github.com/agentforge-hq/agentforge);
> the organization and repository names will only change when that external migration is performed.

* **`ahnora`** (Current): Core platform backend and orchestration engine.
* **`ahnora-studio`**: Visual Web UI for agent design, prompt tuning, and trace inspection.
* **`ahnora-python`**: Python SDK for data scientists and AI pipelines.
* **`ahnora-node`**: TypeScript / JavaScript client library.
* **`ahnora-helm`**: Kubernetes deployment charts and Terraform infrastructure.
