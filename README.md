# 🏥MEDICAL CLINIC API👨‍⚕️

## WHY?

This project has as main goal, help myself **🧑🏽‍💻[Cabaleiro Nicolas](https://www.linkedin.com/in/nicolas-cabaleiro-ab8b64234/)**, to apply my knowledge about: API building with Spring-Boot and Spring-Security mostly.
Now, why I chose a medical clinic as project?, I thought it would be a good project to have several business rules and roles system for like this apply an authentication and authorization system.

## GENERAL FUNCTIONS AND FEATURES

The medical clinic API can register and log in a user with username and password, the user registered can be a patient
or a professional, anyone user can register yourself as a patient, but not as a professional, a professional after 
register yourself, must wait an approval from an administrator user, when the professional is accepted 
, your role will change to Professional Role and be able to use the professional user features,
such as register an appointment list, make a report of an appointment, show a list of your appointments, etc...
    of the same way, a patient user can see the enable appointments to book some of them,
both users can cancel an appointment with a time of anticipation defined previously.
This is the main function of the API, besides of many more that will be detailed later.


## 💻TECHNOLOGIES USED

For this API development I used **☕Java 17** as programming language with **[🍃Spring Framework 3](https://spring.io/)**, and **MYSQL** as DataBase

**[🕵️Spring-Security 6](https://spring.io/projects/spring-security)** for all part of security of the API as well as the **authentication**, **authorization**, **roles** and **permissions**,
**[JWT](https://jwt.io/)** such as Authentication Token and **Postman** for test the API responses, in next I let de **[Postman Collection](MedicalClinic/MedicalClinic.postman_collection.json)**, so you can test the API.


## ⚒️WHAT NEEDS IT TO WORK?

Doesn't need more that a previous **MYSQL configuration**, you need set the roles, and granted authorities to these roles 
and at least one admin user.
all this you can do with the next file, importing it in your MYSQL DataBase: **[SQL IMPORT](MedicalClinic/medicalclinic.sql)**


and also the next **application.properties file configuration**.

#### 📊 DATABASE CONFIGURATION
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/<YOUR-DATABASE-NAME>?characterEncoding=utf8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.datasource.username=<YOUR-USERNAME>
spring.datasource.password=<YOUR-PASSWORD>
spring.jpa.hibernate.ddl-auto=update
```

#### 🔑 SECURITY CONFIGURATION
```properties
spring.security.secret_key=<YOUR-SECRET-KEY>
spring.security.jwt.expiration_in_minutes=<MINUTES-FOR-A-TOKEN-TO-EXPIRE>
```
#### 💼 BUSINESS CONFIGURATION
```properties
cancel.appointments.permit=<NUMBER-OF-CANCELED-APPOINTMENTS_ALLOWED>
hours.to.cancel.appointment=<PRIOR-HOURS-TO-CANCEL-AN-APPOINTMENT>
```


## 📁STRUCTURE OF FOLDERS AND FILES

### Most important folders:


* **[📂Controller:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/controller)** In this folder you will found the files that have the endpoints of the API, which can be consumed 
for an external client to use the API
---
* **[📂Service:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/service)** In the service folder are the services that perform all the business logic of the application and link the data
repositories with the controllers
---
* **[📂Repository:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/repository)** This folder contain the repositories layer that have contact with our DB for each entity

#### 🔝these three folders make up the Layer Architecture of the API

next:

* **[📂Entity:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/entity)** In this folder you will found all the entities class that represent a table in DB, also is the mapping 
of the relations @OneToMany, @ManyToOne, etc..., using JPA with the ORM Framework Hibernate.
---
* **[📂Security:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/security)** This folder contain all the security configuration pf the API, such as filters and the
configuring authorization with JWT and security Beans🫘
---
* **[📂Exception:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/exception)** Here are the custom exceptions and an exception handler that is in charge of handling the
custom exceptions, as well as the generic exceptions for example of Java or SQL 
---
* **[📂Dto:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/dto)** The data transfer objects help us to handle the data better and in this way it could transfer data to client of a more custom and secure way, without sending or receiving the entity classes directly, is indicated how is the format of requests, responses
and show elements
---
* **[📂Util:](MedicalClinic/src/main/java/com/medicalClinicProyect/MedicalClinic/util)** This folder contains the utility classes that support to API without being in a specific folder


