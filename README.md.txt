# Spotify Track Metadata Finder

This is a full-stack application that allows users to fetch music track metadata using an ISRC code. The data is retrieved from the Spotify API, stored in a PostgreSQL database, and displayed on a React-based user interface.



## ✨ Features

-   **Backend:** A robust REST API built with Java 21, Spring Boot 3, and secured with Spring Security.
-   **Frontend:** A responsive and interactive single-page application built with React, Vite, and Material-UI.
-   **Database:** PostgreSQL for persistent data storage.
-   **Containerization:** The entire backend environment (API + Database) is containerized with Docker and can be launched with a single command.
-   **API Documentation:** The backend includes an interactive Swagger UI for exploring and testing the API endpoints.
-   **State Management:** The frontend uses React's Context API for clean, centralized state management.

## 🚀 Tech Stack

**Backend:**
-   Java 21
-   Spring Boot 3.3.4
-   Spring Security (HTTP Basic Auth)
-   Spring Data JPA
-   PostgreSQL
-   Docker & Docker Compose
-   Maven

**Frontend:**
-   React 18
-   Vite
-   React Router
-   Axios
-   Material-UI (MUI)

## 📋 Prerequisites

Before you begin, you will need to have the following tools installed on your system:

-   [Docker & Docker Compose](https://www.docker.com/products/docker-desktop/)
-   [Node.js & npm](https://nodejs.org/) (v18 or higher)
-   Git

## ⚙️ Setup and Installation

### 1. Clone the Repository

```bash
git clone [https://github.com/tu-usuario/tu-repositorio.git](https://github.com/tu-usuario/tu-repositorio.git)
cd tu-repositorio

### 2. Configure the Backend

The backend requires credentials for the Spotify API to function.

-   Navigate to the backend directory:
    ```bash
    cd backend
    ```
-   Create a new file named `.env` in this directory.
-   Add your Spotify Client ID and Client Secret to the `.env` file like this:
    ```
    SPOTIFY_CLIENT_ID=your_spotify_client_id
    SPOTIFY_CLIENT_SECRET=your_spotify_client_secret
    ```
-   **Important:** Make sure the `backend/.gitignore` file contains a line with `.env` to prevent committing your secrets.

### 3. Run the Application

The entire application can be launched with just two commands in two separate terminals.

**Terminal 1: Start the Backend & Database**

-   From the `backend` directory, run:
    ```bash
    docker-compose up --build
    ```

**Terminal 2: Start the Frontend**

-   From the project's root directory, navigate to the `frontend` directory:
    ```bash
    cd frontend
    ```
-   Install the necessary packages:
    ```bash
    npm install
    ```
-   Start the development server:
    ```bash
    npm run dev
    ```

### 4. Accessing the Application

Once both parts are running, you can access them at the following URLs:

-   **Frontend User Interface:** `http://localhost:5173`
-   **Backend API Base URL:** `http://localhost:8080`
-   **Interactive API Documentation (Swagger):** `http://localhost:8080/swagger-ui.html`

### API Usage

The API is protected by HTTP Basic Authentication.
-   **Username:** `admin`
-   **Password:** `password`

You can test all endpoints interactively via the **[Swagger UI](http://localhost:8080/swagger-ui.html)** by using the "Authorize" button.