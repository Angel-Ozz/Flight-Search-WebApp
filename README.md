#  Flight Search WebApp

A **full-stack flight search application** that allows users to find flights based on different parameters, built with:
- **Frontend:** React (TypeScript) + Vite + Tailwind CSS (served via Nginx), testing done with testing library and vitest
- **Backend:** Spring Boot (Gradle) with Amadeus Rest API, using java 21, testing done with Mockito
- **Rest API:** [Amadeus Doc](https://developers.amadeus.com/self-service/apis-docs/guides/developer-guides/quick-start/#video-tutorial)
- **Containerization:** Docker & Docker Compose

---

## Features
- Search flights by departure & arrival locations
- Filter by departure date, return date, currency, and non-stop flights
- Fetch real-time flight data from the Amadeus API
- Responsive UI optimized for desktop and mobile

---

## Project Structure
Flight-Search-WebApp/
│── api/                     # Backend (Spring Boot with gradle ) and java 21
│   ├── src/                 # Java source code
│   ├── build.gradle         # Gradle build file
│   ├── Dockerfile           # Backend Docker configuration
│   ├── .env                 # Backend environment variables
│
│── frontend/                # Frontend (React + TypeScript)
│   ├── src/                 # React components & logic
│   ├── vite.config.js       # Vite configuration
│   ├── Dockerfile           # Frontend Docker configuration
│
│── docker-compose.yml       # Docker Compose configuration
│── README.md                # Project documentation


---

## Setup Instructions

### **Prerequisites**
Ensure you have the following installed:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- (Optional) [Node.js 18+](https://nodejs.org/) & [Gradle 8+](https://gradle.org/)

---

### ** Running with Docker Compose (Recommended)**
This will build and start both the frontend and backend services.

```command to build the docker compose and get it up running
docker-compose up --build -d
```
```command to bring it down
docker-compose down
(you can also use ctrl + c)
```
 - The backend will be running in port 8080
 - The frontend will be running in por 3000

### ** Running locally **
This will build and start both the frontend and backend services.

``` use this to start the backend at port 8080
cd api
./gradlew bootRun
```

```use this to start the frontend at port 5173 (port used by vite)
cd frontend
npm install
npm run dev
```




