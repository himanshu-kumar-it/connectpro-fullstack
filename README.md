# ConnectPro - Professional Networking Platform

An enterprise-level Full Stack Professional Networking Platform inspired by LinkedIn, built using Spring Boot, Spring Security, JWT Authentication, React.js, and MySQL.

---

## Features

### Authentication
- User Registration
- Secure Login
- JWT Authentication
- Role-Based Authorization

### User Profile
- View Profile
- Update Profile
- Professional Headline
- Location Support

### Posts
- Create Posts
- View Feed
- Like Posts
- Comment on Posts
- Pagination & Sorting

### Network
- Search Users
- Send Connection Requests
- Accept Connection Requests
- Reject Connection Requests

### Backend
- RESTful APIs
- Layered Architecture
- Spring Data JPA
- Hibernate ORM
- Bean Validation
- Global Exception Handling

---

# Tech Stack

## Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate (JPA)
- JWT Authentication
- Maven

## Frontend

- React.js
- React Router
- Axios
- CSS3

## Database

- MySQL

## Tools

- Git
- GitHub
- Postman
- IntelliJ IDEA
- VS Code

---

# Project Structure

```text
ConnectPro
│
├── src
│
├── frontend
│   ├── src
│   ├── public
│   ├── package.json
│   └── vite.config.js
│
├── screenshots
│   ├── login.png
│   ├── home.png
│   ├── profile.png
│   └── network.png
│
├── pom.xml
├── mvnw
└── README.md
```

---

# Application Screenshots

## Login Page

![Login](screenshots/login.png)

---

## Home Page

![Home](screenshots/home.png)

---

## Profile Page

![Profile](screenshots/profile.png)

---

## Network Page

![Network](screenshots/network.png)

---

# REST APIs

## Authentication

```
POST /api/users/register
POST /api/auth/login
```

---

## Profile

```
GET /api/users/profile/{id}
PUT /api/users/profile/{id}
```

---

## Posts

```
GET /api/posts
POST /api/posts/user/{userId}
POST /api/posts/{postId}/like/user/{userId}
POST /api/posts/{postId}/comments/user/{userId}
```

---

## Connections

```
POST /api/connections/send
PUT /api/connections/{id}/accept
PUT /api/connections/{id}/reject
```

---

# Installation

## Clone Repository

```bash
git clone https://github.com/himanshu-kumar-it/connectpro-fullstack.git
```

---

## Backend

```bash
cd ConnectPro
./mvnw spring-boot:run
```

---

## Frontend

```bash
cd frontend
npm install
npm run dev
```

---

# Database

- MySQL
- Spring Data JPA
- Hibernate ORM

Create a database:

```sql
CREATE DATABASE connectpro_db;
```

Update your MySQL username and password inside:

```
application.properties
```

---

# Architecture

```
Controller
      │
Service
      │
Repository
      │
MySQL Database
```

---

# Future Improvements

- Profile Image Upload
- Notifications
- Real-time Chat
- Resume Upload
- Email Verification
- Forgot Password
- Docker Deployment
- Cloud Deployment (AWS)

---

# Repository

https://github.com/himanshu-kumar-it/connectpro-fullstack

---

# Author

**Himanshu Kumar**

Java Full Stack Developer

GitHub:
https://github.com/himanshu-kumar-it