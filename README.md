# **FlightApp Microservices**

Hi, I’m **Srihari**, and I built this microservices-based FlightApp system. The project is designed with separate services so each part of the application stays clean, simple, and easy to maintain.

---

## **Services Included**

* **Config Server** – Centralized configuration.
* **Eureka Server** – Service discovery.
* **API Gateway** – Routes all external requests.
* **Booking-Service** – Handles booking creation, cancellation, and retrieval.
* **Flight-Service** – Manages flight data and search operations.
* **Email-Service** – Sends email notifications for successful bookings.
* **RabbitMQ** – Used for asynchronous communication between Booking-Service and Email-Service.

---

## **How It Works**

### **Booking-Service**

I implemented this service to:

* Create and manage bookings
* Fetch flight details from **Flight-Service** using REST
* Use a **circuit breaker** for stability
* Publish booking events to **RabbitMQ**

### **Flight-Service**

This service maintains all flight data and responds to search and availability requests.

### **Email-Service**

I made this service consume messages from RabbitMQ and send confirmation emails automatically.

---

## **Communication Flow**

* All services pull configuration from **Config Server**
* They register with **Eureka**
* **API Gateway** handles request routing
* Services communicate using **REST** and **RabbitMQ**

---
<img width="1458" height="511" alt="Flightapp_microservices" src="https://github.com/user-attachments/assets/43acbcd0-c91b-496d-82d4-782b6fd84058" />

