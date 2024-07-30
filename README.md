# Elasticsearch/Spring Boot Project

## Overview

This project demonstrates a Spring Boot application integrated with PostgreSQL and Elasticsearch, enabling search functionality using Elasticsearch. The project is containerized using Docker Compose for easy setup and deployment.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Assumptions and Clarifications](#assumptions-and-clarifications)
- [Usage](#usage)
- [Author](#author)

## Technologies Used

- **Java 17**
- **Spring Boot 3.0**
- **PostgreSQL 14**
- **Elasticsearch 7.10**
- **Docker & Docker Compose**
- **Hibernate**
- **Maven**
- **RESTful API**

## Setup Instructions

1. **Clone the repository:**
   ```sh
   git clone https://github.com/nastenka-ooops/Digital-Chief-Test.git
   cd Digital-Chief-Test
   ```

2. **Configure environment variables:**
   Create a `.env` file in the root directory with the following content:
   ```env
   DB_NAME=your-database-name
   DB_PASSWORD=your-database-password
   DB_USERNAME=your-database-username
   ```
   
3. **Build and run the Docker containers:**
   ```sh
   docker-compose --env-file .\.env up
   ```

4. **Verify that the containers are running:**
   - PostgreSQL should be available at `localhost:5432`
   - Elasticsearch should be available at `localhost:9200`
   - Spring Boot application should be running at `localhost:8080`
  
5. **Run the database initialization script:**
   Ensure you have Docker installed and running. Execute the following command to run the script that initializes the PostgreSQL database:
   ```sh
   .\scripts\database.sql
   ```

## API Endpoints

### Load Data into Elasticsearch
- **Endpoint:** `POST /api/v1/data/load`
- **Description:** Loads data from PostgreSQL to Elasticsearch.
- **Response:** HTTP 200 OK on success.

### Search Products
- **Endpoint:** `GET /api/v1/search`
- **Description:** Searches for products based on a query with an optional filter applied. The filter is controlled via a configuration flag.
- **Query Parameter:**
  - `query` (string): The search query.
- **Configuration Flag:**
  - search.filter.enabled (boolean): Whether to apply the filter based on SKU attributes.
  - search.filter.color (string) : Specified the color 
  - search.filter.availability (boolean): Specified the availability
- **Response:** JSON array of matching products.

### Example:
```sh
curl -X GET "http://localhost:8080/api/v1/search?query=product"
```

## Assumptions and Clarifications

- The project uses `spring-boot-starter-data-jpa` for ORM and `Elasticsearch Rest Client` for interacting with Elasticsearch.
- Variative fields for Product and SKU include types such as number, date, and text.
- Data is preloaded into PostgreSQL using `data.sql`.
- Elasticsearch index combines both Product and SKU information.
- **Filtered Search:** When enabled via configuration (filter.enabled), the search will only return products with SKUs that meet the filter criteria. The filter checks for attributes like color and availability.

## Usage

1. **Run the application:**
   Ensure Docker containers are up and running, then access the application at `http://localhost:8080`.

2. **Load data into Elasticsearch:**
   Send a POST request to `/api/v1/data/load`.

3. **Search for products:**
   Use the `/api/v1/search` endpoint with the desired query parameter.

4. **Use filtered search (if enabled):**
   Update your configuration file to enable the filter:
```env
search.filter.enabled=true
search.filter.color=BLUE
search.filter.availability=false
```

## Author
- Backend developer - [Brutskaya Anastasia](https://github.com/nastenka-ooops)
