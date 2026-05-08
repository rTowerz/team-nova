# Flashcard AI App Guide

## Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Frontend Setup (HTML/CSS/JavaScript)](#frontend-setup)
4. [Backend Setup (Java)](#backend-setup)
5. [API Integration](#api-integration)
6. [Database Setup](#database-setup)
7. [Running the Application](#running-the-application)
8. [Sample Code](#sample-code)

---

## Project Overview

A flashcard application enhanced with AI capabilities that helps users learn and memorize information. The app includes:
- **Create & manage flashcard decks**
- **Study mode with spaced repetition**
- **AI-powered answer evaluation**
- **Progress tracking and analytics**
- **User authentication**

---

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                   Frontend (Web Browser)                 │
│            HTML/CSS/JavaScript (Single Page App)         │
│                                                           │
│  • User Interface & Forms                               │
│  • Client-side State Management                         │
│  • API Requests to Backend                              │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP/REST API
┌────────────────────▼────────────────────────────────────┐
│                   Backend (Java)                         │
│            Spring Boot / Java REST API                   │
│                                                           │
│  • User Management & Authentication                     │
│  • Flashcard CRUD Operations                            │
│  • AI Answer Evaluation (Integration with OpenAI/etc)   │
│  • Progress Tracking                                    │
└────────────────────┬────────────────────────────────────┘
                     │ JDBC / ORM
┌────────────────────▼────────────────────────────────────┐
│                   Database                               │
│              PostgreSQL / MySQL / SQLite                 │
│                                                           │
│  • Users Table                                          │
│  • Decks Table                                          │
│  • Flashcards Table                                     │
│  • Study Sessions & Progress                            │
└─────────────────────────────────────────────────────────┘
```

---

## Frontend Setup

### 1. HTML Structure (`index.html`)

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Flashcard AI App</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    
</body>