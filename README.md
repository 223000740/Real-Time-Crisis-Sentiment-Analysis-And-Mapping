# Real-Time Crisis Sentiment Analysis 🚨🗺️

A full-stack web application that allows users to submit real-time distress reports with their geolocation. A machine learning model analyzes the text to determine urgency, and critical reports are immediately visualized on a live map for rapid response by authorities.

---

## 🧠 Project Overview

This system provides a platform for civilians to report emergency situations like floods, fires, or accidents. The backend, built with Java Spring Boot, orchestrates the workflow by processing submissions, communicating with a Python-based machine learning service for analysis, and storing the results in a MongoDB database. Urgent reports are highlighted on a Leaflet.js map, giving first responders a clear, real-time operational picture.

---

## ⚙️ Architecture & Workflow

The application uses a microservices-style architecture to separate concerns and leverage the best technologies for each task:

1. **Frontend (HTML, CSS, JS)**  
   - A user submits a message and their location is captured via the browser's Geolocation API.  
   - A POST request is sent to the backend.

2. **Backend (Java Spring Boot)**  
   - Receives the request and acts as an API gateway.  
   - Forwards the message text to the ML service for analysis.

3. **ML Service (Python Flask API)**  
   - A pre-trained Transformer model (using a zero-shot classification pipeline) classifies the text as "urgent" or "not urgent."  
   - Returns the prediction and a confidence score.

4. **Database (MongoDB)**  
   - If the report is classified as urgent with high confidence, the complete record (text, location, prediction, timestamp) is saved.

5. **Visualization (Leaflet.js)**  
   - The map dashboard periodically fetches all urgent reports and displays them as highlighted, animated markers.

---

## 🛠️ Tech Stack

| Category   | Technology / Library             |
|------------|----------------------------------|
| Frontend   | HTML, CSS, JavaScript, Leaflet.js |
| Backend    | Java, Spring Boot, Maven         |
| ML Service | Python, Flask, Hugging Face Transformers |
| Database   | MongoDB                          |

---

## ✨ Key Features

- **Real-Time Reporting**: Users can instantly submit crisis reports from any web browser.  
- **Geolocation Tagging**: Automatically captures and tags each report with the user's precise GPS coordinates.  
- **AI-Powered Urgency Detection**: Leverages a modern NLP model to intelligently classify the severity of a report, filtering out non-critical noise.  
- **Live Map Dashboard**: Visualizes incoming urgent reports on an interactive map, using animated markers to draw immediate attention to critical hotspots.  
- **Decoupled Architecture**: A robust microservices architecture makes the system modular, scalable, and easy to maintain.

---

## 🚀 Getting Started

Follow these steps to run the entire application stack locally.

### Prerequisites

- Java JDK 17+  
- Apache Maven  
- Python 3.8+  
- MongoDB installed and running

### Installation & Setup

#### Clone the repository

```bash
git clone https://github.com/YashVS15112004/Real-Time-Crisis-Sentiment-Analysis.git
cd Real-Time-Crisis-Analysis
