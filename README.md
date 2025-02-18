# GitBak - Modern GitHub Client for Android

GitBak is a modern Android application that provides a seamless GitHub experience. Built with Jetpack Compose and following modern Android development practices, it allows users to explore GitHub, manage repositories, and connect with other developers - with or without authentication.

<img src="https://github.com/user-attachments/assets/1ced963c-443b-485a-b4b5-f8d80e21fca8" width="250" alt="My Repositories"/>


## 🎯 Features

### 🔐 Authentication
- Secure GitHub OAuth integration
- Persistent session management
- Seamless login/logout flow

### 👥 User Management
- Advanced user search functionality
- Detailed user profile viewing
- Follow/Unfollow capabilities
- Followers and following lists with pagination
- User activity tracking

### 📚 Repository Features
- Comprehensive repository listing
- Detailed repository information
- Star/Unstar functionality
- Repository statistics (stars, forks, watchers)
- Repository topics and languages
- License information
- Activity timestamps

### 🔍 Search Capabilities
- Real-time user search
- Optimized search results

## 📸 Screenshots

<div align="center">
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/34a0a252-21ba-4a67-9d91-c61fe885ca6d" width="216" height="468" alt="Onboarding"/>
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/d1eed086-19eb-40e9-a4da-6fb85daa112b" width="216" height="468" alt="Search"/>
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/f6bc1d03-7419-4dc8-9086-141a72ade438" width="216" height="468" alt="Detail"/>
      </td>
    </tr>
    <tr>
      <td align="center"><strong>Onboarding Screen</strong></td>
      <td align="center"><strong>Search Screen</strong></td>
      <td align="center"><strong>Detail Screen</strong></td>
    </tr>
  </table>

  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/f7c8f0e8-1fa6-44dc-be47-d7f9f04a768f" width="216" height="468" alt="Repositories"/>
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/23de9535-67a4-4020-a941-cb0dbbdd2f60" width="216" height="468" alt="Repository Detail"/>
      </td>
       <td align="center">
        <img src="https://github.com/user-attachments/assets/7c4d0e87-4978-489e-950d-3141dc1828c5" width="216" height="468" alt="My Repositories"/>
      </td>
    </tr>
    <tr>
      <td align="center"><strong>Repositories</strong></td>
      <td align="center"><strong>Repository Detail</strong></td>
      <td align="center"><strong>My Repositories</strong></td>
    </tr>
  </table>
</div>

## 🎥 Demo

<div align="center">
 <table>
   <tr>
    <td align="center">
        <video src="https://github.com/user-attachments/assets/69b3109b-3cef-4a21-a7e1-e31cd3a4837b" width="250" height="500" controls/>
        <br>
        <strong>Sign In Demo</strong>
      </td>
      <td align="center">
        <video src="https://github.com/user-attachments/assets/d5418666-3314-43de-96b2-1c62aefd4e5c" width="216" height="468" controls/>
      </td>
      <td align="center">
        <video src="https://github.com/user-attachments/assets/607a4e67-62b6-4eab-8f2b-e41d72f3ce83" width="216" height="468" controls/>
      </td>
    </tr>
    <tr>
      <td align="center"><strong>Signin Demo</strong></td>
      <td align="center"><strong>Following Demo</strong></td>
      <td align="center"><strong>Star Repository Demo</strong></td>
    </tr>
  </table>         
</div>

## 🏗️ Architecture

GitBak is built with Clean Architecture principles and MVVM pattern, ensuring separation of concerns, testability, and maintainability.

### 📂 Project Structure

```
app/
├── data/
│   ├── common/      # Common utilities and extensions
│   ├── model/       # Data models and entities
│   ├── remote/      # API services and network handling
│   ├── repository/  # Repository implementations
│   └── session/     # Session management
├── di/              # Dependency injection modules
├── domain/
│   ├── repository/  # Repository interfaces
│   └── usecase/     # Business logic use cases
└── presentation/
    ├── components/  # Reusable UI components
    ├── navigation/  # Navigation handling
    ├── screens/     # UI screens
    ├── state/       # UI state management
    └── theme/       # App theming
```

### 🏗️ Architectural Components

#### Clean Architecture
The application follows Clean Architecture principles with three main layers:

- **Data Layer**: Handles data operations and external interactions
  - Repository implementations
  - API services
  - Data mapping

- **Domain Layer**: Contains business logic and rules
  - Use cases
  - Repository interfaces
  - Domain models

- **Presentation Layer**: Manages UI and user interactions
  - ViewModels
  - Compose UI components
  - State management

#### MVVM Pattern
- **Model**: Data and business logic encapsulation
- **View**: Declarative UI using Jetpack Compose
- **ViewModel**: UI state management and business logic handling

### 🛠️ Technical Stack

#### Modern Android Development
- **Kotlin** - Primary programming language
- **Coroutines & Flow** - Asynchronous programming
- **StateFlow** - Reactive state management

#### UI Framework
- **Jetpack Compose** - Modern declarative UI
- **Material3** - Material Design implementation
- **Navigation Compose** - In-app navigation

#### Dependency Injection
- **Hilt** - Compile-time DI framework

#### Networking
- **Retrofit** - Type-safe HTTP client
- **OkHttp** - HTTP client with interceptors

#### Authentication
- **OAuth2** - GitHub authentication

### 🔄 Data Flow

```
User Action → View → ViewModel → UseCase → Repository → API Service
     ↑                   ↑          ↑          ↑              ↓
     └───────────────────←──────────←──────────←──────────────┘
```

## 🚀 Setup

1. Clone the repository:
```bash
git clone https://github.com/farukkaraca/gitbak.git
```

2. Create GitHub OAuth application:
   - Go to [GitHub Developer Settings](https://github.com/settings/developers)
   - Click "New OAuth App"
   - Fill in the application details:
     - Application name: GitBak
     - Homepage URL: http://localhost:3000
     - Authorization callback URL: http://localhost:3000/callback

3. Add GitHub OAuth credentials to `local.properties`:
```properties
GITHUB_CLIENT_ID=your_client_id
GITHUB_CLIENT_SECRET=your_client_secret
```

4. Open in Android Studio and sync project

5. Run the application

## 📝 Requirements

- Android Studio Hedgehog | 2023.1.1 or later
- Minimum SDK 24
- Kotlin 1.9.0 or later
- JDK 18

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Developer

Faruk Karaca - [@farukkaraca](https://github.com/farukkaraca) 
