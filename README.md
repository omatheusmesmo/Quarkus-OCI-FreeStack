# 🚀 Quarkus OCI FreeStack: The Ultimate Cloud-Native Showcase

> **Building a State-of-the-Art Cloud-Native Backend on Oracle Cloud Always Free Tier**

This project demonstrates how to build a high-performance, **Cloud-Native Backend** using **Quarkus** (Java 21) that leverages the full power of **Oracle Cloud Infrastructure (OCI)**. 

Unlike traditional architectures that fragment data across multiple specialized databases (SQL for relations, MongoDB for documents, PostGIS for spatial), this project embraces the **Converged Database** paradigm—using a single, powerful Oracle Autonomous Database for **SQL, JSON, and Spatial data**.

---

## 🏗️ Project Architecture & Goals

The goal is to prove that you can build enterprise-grade architecture for **$0/month** using OCI's **Always Free** services.

### Key Pillars
1.  **Polyglot Persistence (Converged DB):** One database engine for Relational (User profiles), Document (JSON Articles), and Spatial (Geotagging) data. No more "database sprawl."
2.  **Serverless Media Management:** Secure direct-to-cloud file uploads (articles covers) using **OCI Object Storage** and **Pre-Authenticated Requests (PARs)**, keeping the backend lightweight.
3.  **Zero Trust Security:** Eliminating hardcoded passwords by fetching secrets at runtime from **OCI Vault** via Instance Principals.
4.  **Event-Driven Architecture (EDA):** Decoupling critical paths with **OCI Streaming (Kafka-compatible)** for asynchronous tasks like image processing or search indexing.
5.  **Native Performance:** Compiling to **GraalVM Native Image** and deploying on **ARM Ampere A1** instances for millisecond startup times and extreme efficiency.

---

## 🗺️ Project Roadmap

We are building this iteratively. Here is the current progress:

### ✅ Phase 1: The Reactive Foundation (Completed)
- **Goal:** Establish a secure, reactive connection to Oracle Autonomous Database (ATP) using mTLS.
- **Tech:** Quarkus REST, Agroal Pool, SmallRye Health.
- **Status:** **Ready!**

### ✅ Phase 2: The Converged Data Model (Completed)
- **Goal:** Create a hybrid data model mixing standard JPA Entities with native JSON storage.
- **Tech:** Hibernate ORM Panache + Oracle 26ai JSON Support + Flyway.
- **Feature:** Implemented Article CRUD where flexible content is stored as native JSON and queried using SQL/JSON path expressions (`json_exists`).

### 🔐 Phase 3: Zero Trust Security with Vault (Next)
- **Goal:** Remove all sensitive credentials (like DB passwords) from the source code and environment files.
- **Tech:** OCI Java SDK + OCI Vault + Instance Principals.
- **Feature:** The application will authenticate with OCI at startup, retrieve secrets from the Vault, and inject them into the connection pool dynamically.

### 🔮 Future Phases
- **Phase 4 (Media):** Implement **Object Storage** pattern for image uploads using Pre-Authenticated Requests (PARs).
- **Phase 5 (Events):** Publish "Article Created/Updated" events to **OCI Streaming** (Kafka-compatible).
- **Phase 6 (Production):** Deploy **Native Binary** to an OCI **Ampere A1 Compute** (ARM) instance.

---

## 🛠️ Getting Started

Follow these steps to run the current version of the project.

### Prerequisites
1.  **Java 21** (GraalVM recommended for native builds)
2.  **Maven 3.9+**
3.  **Oracle Cloud Account** (Always Free Tier)
4.  **Oracle Autonomous Database** (ATP) instance version **26ai**

### Setup Instructions

1.  **Download & Configure Wallet:**
    - Download your **DB Wallet** from OCI Console.
    - Extract its content to `src/main/resources/wallet/`.

2.  **Configure Environment:**
    - Copy the template: `cp .env.example .env`
    - Edit `.env` with your database credentials and service name.

    ```bash
    DB_USERNAME=ADMIN
    DB_PASSWORD=YourStrongPassword123!
    DB_SERVICE_NAME=freestackdb_high
    DB_WALLET_PATH=src/main/resources/wallet
    ```

3.  **Run in Development Mode:**
    ```bash
    ./mvnw quarkus:dev
    ```

---

## 🔍 Verifying the Implementation

### 1. Connectivity & Health
```bash
# Check Database Version (26ai)
curl http://localhost:8080/oracle/version

# Check Health Status (Readiness)
curl http://localhost:8080/q/health/ready
```

### 2. Converged Database Features (CRUD & JSON)
```bash
# Create a new Article with native JSON content
curl -X POST http://localhost:8080/articles \
  -H "Content-Type: application/json" \
  -d '{"title":"Cloud Native Java","author":"Matheus","content":{"tags":["java","oci"]}}'

# List all articles
curl http://localhost:8080/articles

# Search articles by tag inside the JSON document
curl "http://localhost:8080/articles/search?tag=java"
```

---

## 🔧 Technology Stack

| Component         | Technology                | OCI Service               |
| :---------------- | :------------------------ | :------------------------ |
| **Runtime**       | Quarkus (Java 21)         | Ampere A1 Compute (ARM)   |
| **Database**      | Oracle Database 26ai      | Autonomous Database (ATP) |
| **Persistence**   | Hibernate ORM Panache     | -                         |
| **Migrations**    | Flyway                    | -                         |
| **API**           | Quarkus REST (Jackson)    | -                         |
| **Validation**    | Hibernate Validator       | -                         |
| **Observability** | SmallRye Health           | OCI Monitoring            |
| **Build**         | Maven                     | -                         |

---

<p align="center">
  <i>Developed with ❤️ by Matheus Oliveira for the Cloud Native Community</i>
</p>
