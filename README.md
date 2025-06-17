# My Assessment Application

## Overview
This is an Android application developed as part of the Mobile App Development assessment. The application implements a login system and dashboard functionality using modern Android development practices.

## Features
- User Authentication
- Dashboard View
- API Integration
- Clean Architecture Implementation

## Screenshots of how it looks:
1. login:
<img width="206" alt="image" src="https://github.com/user-attachments/assets/602d7614-ce3c-4130-b011-7b005c8524dd" />
2. Dashboard, entites comming from api:
<img width="207" alt="image" src="https://github.com/user-attachments/assets/1ee51b43-4f34-4d57-9cfc-6bfc239dfcaf" />
3. Details for the entity:
<img width="209" alt="image" src="https://github.com/user-attachments/assets/47e401e4-0072-4ee7-987c-31cdd5419824" />


## Technical Stack
- Kotlin
- Android Jetpack Components
- Dagger Hilt for Dependency Injection
- Retrofit for API calls
- MVVM Architecture Pattern

## Project Structure
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/myassssmentapplication/
│   │   │   ├── data/
│   │   │   │   ├── api/
│   │   │   │   ├── model/
│   │   │   │   └── repository/
│   │   │   ├── di/
│   │   │   ├── ui/
│   │   │   └── utils/
│   │   └── res/
│   └── test/
```

## Setup Instructions
1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the application on an emulator or physical device

## Dependencies
- AndroidX Core KTX
- AndroidX AppCompat
- Material Design Components
- Retrofit
- Dagger Hilt
- Coroutines
- ViewModel and LiveData

## API Integration
The application integrates with a backend API for:
- User authentication
- Dashboard data retrieval

## Architecture
The application follows MVVM (Model-View-ViewModel) architecture pattern with:
- Repository pattern for data management
- Dependency Injection using Dagger Hilt
- Clean separation of concerns



