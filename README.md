# Saga Choreography with Kafka (Multi-Module Maven Project)

This repository contains a complete implementation of the **Saga Pattern using Choreography** with Spring Boot, Kafka, and Docker.

The system is built as a **multi-module Maven project** where each module represents a microservice. The services communicate asynchronously through Kafka topics to implement distributed transaction management using the Saga Pattern.

---

## 🚀 How to Create Multi-Module Maven Project in IntelliJ

If you want to create this project from scratch in IntelliJ IDEA, follow these steps:

1. **Create Parent Project**
   - Open IntelliJ → `File` → `New` → `Project`.
   - Select **Maven**.
   - Enter:
     - GroupId: `com.example`
     - ArtifactId: `saga-choreography-impl`
     - Packaging: `pom`
   - Finish setup. This will be the **parent project**.

2. **Configure Parent POM**
   - Open `pom.xml` of the parent.
   - Set packaging as `pom`.
   - Add `<modules>` section for all submodules (services).

   Example:
   ```xml
   <packaging>pom</packaging>
   <modules>
       <module>common-utils</module>
       <module>order-service</module>
       <module>inventory-service</module>
       <module>payment-service</module>
   </modules>

3. **Create Submodules**
   - Right-click on the parent project → New → Module.
   - Select **Maven**.
   - Provide ArtifactId as the service name (e.g., order-service).
   - Repeat for inventory-service, payment-service, and common-utils.

4. **Update Submodule POMs**
Each submodule should have its own pom.xml.
Ensure parent reference is set:

 Example:
   ```xml
    <parent>
      <groupId>com.example</groupId>
      <artifactId>saga-choreography-impl</artifactId>
      <version>1.0-SNAPSHOT</version>
    </parent>```
