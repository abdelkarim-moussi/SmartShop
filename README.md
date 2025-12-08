## 🛒 Smartshop – Backend Application
### 📌 Overview

Smartshop est une application backend de commerce électronique développée avec Spring Boot, conçue pour gérer les produits, clients, commandes et stocks.

L’application embarque une logique métier avancée incluant :

un système automatique de fidélité client,

une gestion stricte des stocks,

des règles métier solides autour du cycle de vie des commandes.

Le projet suit une architecture modulaire favorisant la maintenabilité, la testabilité et l’évolutivité.
La persistance est assurée via JPA/Hibernate, avec Liquibase pour la gestion des migrations de schéma.

🚀 Technologies
Category	Technology
Language	Java 17+
Framework	Spring Boot 3.x
Persistence	Spring Data JPA / Hibernate
Database	PostgreSQL
Migrations	Liquibase
Security	Custom HTTP Filter (Session-based)
Utilities	Lombok, MapStruct
🏗️ Architecture

Le projet suit une architecture en couches, garantissant une séparation claire des responsabilités.

com.smartshop
├── domain
│   ├── entities
│   ├── enums
│   └── repositories (interfaces)
│
├── application
│   ├── services (use cases / business logic)
│   ├── dto
│   └── mapper
│
├── infrastructure
│   ├── security
│   ├── config
│   └── repository (implémentations JPA)
│
└── presentation
└── controller (REST APIs)

Responsibilities per layer

Domain: Entités métier, enums, contrats de repository

Application: Logique métier pure, DTOs, mappers

Infrastructure: Sécurité, configuration, accès aux données

Presentation: Exposition des APIs REST

⚙️ Configuration & Run
✅ Prerequisites

Java 17+

Maven 3.x

PostgreSQL

🗄️ Database Configuration

Editer src/main/resources/application.properties :

spring.datasource.url=jdbc:postgresql://localhost:5432/smartshop_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=none
spring.liquibase.enabled=true

▶️ Run the Application
mvn spring-boot:run


Application disponible à :
📍 http://localhost:8080

🧠 Business Features
📦 Order Management

Order Creation

Vérification stricte du stock produit

Si stock insuffisant → statut REJECTED

Décrémentation du stock pour les commandes valides

Support des codes promotionnels (PROMO-XXXX)

format valide requis

maximum 10 utilisations

Order Cancellation

Possible uniquement pour les commandes avec statut PENDING

Ré-incrémentation du stock

Order Confirmation

restAmount doit être égal à ZERO

Déclenche automatiquement la mise à jour du niveau de fidélité client

⭐ Loyalty System

Le niveau de fidélité du client est automatiquement recalculé après chaque commande confirmée.

Level	Condition (Orders OR Total Amount)	Discount
BASIC	< 3 orders AND < €1000	None
SILVER	≥ 3 orders OR ≥ €1,000	5% if subtotal ≥ €500
GOLD	≥ 10 orders OR ≥ €5,000	10% if subtotal ≥ €800
PLATINUM	≥ 20 orders OR ≥ €15,000	15% if subtotal ≥ €1,200
📊 Client Statistics

Available client metrics:

Total number of orders

Total confirmed orders amount

First and last order dates

Full order history

🔒 Security & Access Control

Sécurité basée sur :

HTTP Authorization Filter

Session-based authentication

👥 Roles & Permissions
Role	Client APIs (/api/v1/clients/...)	Product APIs (/api/v1/products/all)
ADMIN	✅ Full access	✅ Full access
CLIENT	✅ Own data only	✅ Read-only
Unauthenticated	❌ Denied	❌ Denied
🌐 Public Endpoints

/api/v1/login

/api/v1/register

✅ Status Codes Convention
Operation	Status
Successful POST	201 Created
Successful DELETE	204 No Content
Resource not found	404 Not Found
Email already used	409 Conflict
Unauthorized access	401 Unauthorized