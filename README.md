# 🚀 Quarkus OCI FreeStack: The Ultimate Cloud-Native Showcase

> **Building a State-of-the-Art Microservice on Oracle Cloud Always Free Tier**

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
- **Tech:** Quarkus JDBC Oracle, Agroal Pool, SmallRye Health.
- **Status:** **Ready!** The application connects, validates certificates via Wallet, and exposes health checks.

### 🔜 Phase 2: The Converged Data Model (Next)
- **Goal:** Create a hybrid data model mixing standard JPA Entities with native JSON storage.
- **Tech:** Hibernate ORM Panache + Oracle JSON support.
- **Feature:** Store flexible article content as JSON docs while querying them using standard SQL.

### 🔮 Future Phases
- **Phase 3 (Security):** Integrate **OCI Vault** to remove `application.properties` secrets.
- **Phase 4 (Media):** Implement **Object Storage** pattern for image uploads.
- **Phase 5 (Events):** Publish "Article Created/Updated" events to **OCI Streaming**.
- **Phase 6 (Production):** Deploy **Native Binary** to an OCI **Ampere A1 Compute** instance.

---

## 🛠️ Getting Started (Phase 1)

Follow these steps to run the current version of the project.

### Prerequisites
1.  **Java 21** & **Maven 3.8+**
2.  **Oracle Cloud Account** (Always Free)
3.  **Oracle Autonomous Database** instance created (Workload: **Transaction Processing**)

### Setup Instructions

1.  **Clone resources:**
    - Download your **DB Wallet** from OCI Console.
    - Extract it to `src/main/resources/wallet/`.

2.  **Configure Credentials:**
    - Copy the template: `cp .env.example .env`
    - Edit `.env` with your DB password and service name (from `tnsnames.ora`).

    ```bash
    DB_USERNAME=ADMIN
    DB_PASSWORD=YourStrongPassword123!
    DB_SERVICE_NAME=freestackdb_high
    DB_WALLET_PATH=src/main/resources/wallet
    ```

3.  **Run in Dev Mode:**
    ```bash
    ./mvnw quarkus:dev
    ```

4.  **Verify Connection:**
    ```bash
    # Check Database Version
    curl http://localhost:8080/oracle/version
    
    # Check Health Status
    curl http://localhost:8080/q/health/ready
    ```

---

## 🔧 Technology Stack

| Component | Technology | OCI Service |
| :--- | :--- | :--- |
| **Runtime** | Quarkus (Java 21) | Ampere A1 Compute (ARM) |
| **Database** | Hibernate ORM Panache | Autonomous Database (ATP) |
| **API** | RESTEasy Reactive | - |
| **Observability** | SmallRye Health | OCI Monitoring |
| **Build** | Maven | - |

---

<p align="center">
  <i>Developed with ❤️ for the Cloud Native Community</i>
</p>
