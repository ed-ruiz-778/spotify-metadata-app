# Spotify Track Metadata Finder

A full-stack application designed to fetch, store, and display music track metadata using an ISRC code. The project leverages a Spring Boot backend API and a responsive React frontend.

## ✨ Features

-   **RESTful Backend API:** Built with Java 21 & Spring Boot, providing endpoints to create and retrieve track data.
-   **Spotify Integration:** Fetches track metadata and cover art in real-time from the official Spotify API.
-   **Containerized Environment:** The backend and its PostgreSQL database are fully containerized with Docker for easy setup and consistent execution.
-   **Secure Endpoints:** The API is protected using Spring Security with HTTP Basic Authentication.
-   **Interactive API Documentation:** Includes a Swagger UI for easy exploration and testing of all backend endpoints.
-   **Reactive Frontend:** A clean and modern user interface built with React, Vite, and Material-UI.
-   **Centralized State Management:** Uses React's Context API for clean, centralized state management.

## 🚀 Tech Stack

| Backend                               | Frontend                  |
| ------------------------------------- | ------------------------- |
| Java 21                               | React 18 & Vite           |
| Spring Boot 3.3.4                     | React Router              |
| Spring Data JPA & Spring Security     | Axios                     |
| PostgreSQL                            | Material-UI (MUI)         |
| Docker & Docker Compose               |                           |
| Maven                                 |                           |

## ⚙️ Getting Started

Follow these steps to set up and run the project locally.

### Prerequisites

Ensure you have the following software installed:
-   [Docker & Docker Compose](https://www.docker.com/products/docker-desktop/)
-   [Node.js & npm](https://nodejs.org/) (v18 or higher)
-   Git

### Installation and Execution

1.  **Clone the Repository**
    
    ```bash
    git clone [https://github.com/tu-usuario/tu-repositorio.git](https://github.com/tu-usuario/tu-repositorio.git)
    cd tu-repositorio
    ```

2.  **Configure and Run the Backend**

    -   Navigate to the `backend` directory:
        ```bash
        cd backend
        ```
    -   Create a `.env` file and add your Spotify API credentials:
        ```
        SPOTIFY_CLIENT_ID=your_spotify_client_id
        SPOTIFY_CLIENT_SECRET=your_spotify_client_secret
        ```
    -   Launch the backend and database (this will take a minute the first time):
        ```bash
        docker-compose up --build
        ```

3.  **Configure and Run the Frontend**

    -   Open a **new terminal window**.
    -   Navigate to the `frontend` directory:
        ```bash
        cd frontend
        ```
    -   Install dependencies:
        ```bash
        npm install
        ```
    -   Start the development server:
        ```bash
        npm run dev
        ```

## 🌐 Accessing the Application

Once both services are running, the application will be available at:

-   **Frontend UI:** `http://localhost:5173`
-   **Backend API:** `http://localhost:8080`
-   **Swagger API Docs:** `http://localhost:8080/swagger-ui.html`

### API Credentials

The API is protected by HTTP Basic Authentication.
-   **Username:** `admin`
-   **Password:** `password`
