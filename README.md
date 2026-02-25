# Kafka Spring Boot Demo

## Overview
This is a demo project to learn how to put messages on Kafka using Spring Boot with a producer microservice, and retrieve them with another Spring Boot consumer microservice and use them (the usage is printing since is a demo project).

## Project Structure
- **Root Directory**: Contains the `docker-compose.yml` file to run Kafka locally.
- **`producer`**: A Spring Boot microservice responsible for publishing messages to the Kafka broker.
- **`consumer`**: A Spring Boot microservice responsible for listening to the Kafka broker and consuming (printing) the messages.

## How to Run

### 1. Start the Kafka Message Broker
From the root of the project, run the following command to instantiate the Kafka docker container:
```bash
docker compose up -d
```

### 2. Run the Consumer Microservice
Navigate to the `consumer` folder and start the Spring Boot application by running the maven wrapper command:
```bash
cd consumer
./mvnw spring-boot:run
```

### 3. Run the Producer Microservice
In a **new terminal window**, navigate to the `producer` folder and start the producer application:
```bash
cd producer
./mvnw spring-boot:run
```

## How It Works

### Producer Execution
When the producer runs, thanks to the `DemoRunner` class, it puts 3 messages in the message broker, and then it stops. The logic generates a 4-digit number randomly each time for the order ID (e.g., **ORDER-4812**).

If you run the producer command again:
```bash
./mvnw spring-boot:run
```
It will re-execute the `DemoRunner`, putting another 3 messages into the broker. You will instantly see these new messages retrieved and printed in the consumer's terminal window.

## Unit Tests
Unit tests are provided for both the **consumer** and the **producer** microservices. You can execute them by running the `./mvnw test` command in their respective `consumer` and `producer` directories.
