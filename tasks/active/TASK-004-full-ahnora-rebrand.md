# Task: TASK-004 - Full Ahnora Rebrand

**Status:** Implemented on branch `feat/ahnora-full-rebrand` (pending review/merge)  
**Owner:** User (Review), AI Agent (Implementation)  
**Depends On:** —  
**Target:** Repository-wide  
**Framework Version:** Java 21, Spring Boot 4.1.1  

---

## 1. Description

Rename the product and repository identity from **AgentForge** to **Ahnora** across source code, documentation, configuration, tests, and developer tooling, without touching external resources.

Positioning: *Ahnora is an open-source platform for building, orchestrating, and operating AI agents.*  
Tagline: *Open intelligence. Built together.*

---

## 2. Changed identifiers

| Area | Before | After |
|---|---|---|
| Display name | AgentForge | Ahnora |
| Java packages | `com.agentforge.*` | `com.ahnora.*` |
| Maven coordinates | `com.agentforge:agentforge-*` | `com.ahnora:ahnora-*` |
| Modules | `agentforge-common`, `agentforge-identity` | `ahnora-common`, `ahnora-identity` |
| Core exception | `AgentForgeException` | `AhnoraException` |
| Auto-configuration | `AgentForge{Jackson,Web}AutoConfiguration` | `Ahnora{Jackson,Web}AutoConfiguration` |
| Spring property prefix | `agentforge.jwt.*` | `ahnora.jwt.*` |
| Default JWT key id | `agentforge-identity-key-1` | `ahnora-identity-key-1` |
| Redis key namespace (docs) | `agentforge:{tenantId}:...` | `ahnora:{tenantId}:...` |
| Dev database name | `POSTGRES_DB=agentforge` | `POSTGRES_DB=ahnora` |
| Subagent type names | `agentforge-*` | `ahnora-*` |
| Brand assets | none | `brand/*.svg` (logo, wordmark, favicon) |

---

## 3. Compatibility decisions (intentionally retained)

- **GitHub organization/repository URLs** (`github.com/agentforge-hq/agentforge`) are unchanged: the remote still points there and no external migration was performed.
- **Completed task records** (`tasks/completed/TASK-001*`, `TASK-002*`) keep historical identifiers and carry a provenance note instead of being rewritten.
- **Applied Flyway migration `V1__init_identity_schema.sql`** keeps its original header comment: editing an applied migration changes its checksum and breaks validation on existing databases. Future schema work happens in new migration files.
- **No public API fields, persisted database values, OAuth callback paths, or event/schema contracts** were renamed; no such contract carried the old brand.
- The Spring property prefix change (`agentforge.jwt.*` → `ahnora.jwt.*`) is safe only because nothing has shipped: any deployment overriding `AGENTFORGE_JWT_*` environment variables must switch to `AHNORA_JWT_*`.

## 4. Local migration notes

- Renaming `POSTGRES_DB` orphans an existing local `pgdata` volume; recreate development infrastructure with `docker compose down -v` (development data only).
- Subagent type names are documented in `.agents/`; any local agent configuration outside version control must follow the rename.

---

## 5. Checklist

- [x] Isolated branch created from exact HEAD (`feat/ahnora-full-rebrand`) in a separate worktree; original branch and uncommitted work untouched
- [x] Directory and file renames performed with `git mv`
- [x] Content migration across source, docs, config, tests, and tooling
- [x] Brand assets created (`brand/ahnora-logo.svg`, `brand/ahnora-wordmark.svg`, `brand/favicon.svg`)
- [x] Post-migration inventory: only historical records and live external URLs retain the old brand
- [x] Build and unit tests executed (`mvnw test`)
- [ ] External migration (GitHub org/repo rename, package/image publishing) — explicitly out of scope, not performed
