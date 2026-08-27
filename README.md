# 💰 TipApp - Fair Tip Distribution for Waiters

Welcome to **TipApp**! This project was developed to simplify the distribution of tips in hospitality teams, based on the hours worked by each staff member.

This project represents my learning journey in modern Android development, applying the latest tools and architectures recommended by the community.

## 🚀 Features
- **Real-Time Calculation**: Enter the total amount and hours for each waiter to see the distribution instantly.
- **Local Persistence**: Data is not lost when closing the app thanks to a local database.
- **Modern Interface**: Fluid and adaptive design using Jetpack Compose.
- **Smart Management**: Easily add, edit, or delete waiters.

## 🛠️ Technologies and Architecture
For this project, I decided to step out of my comfort zone and apply a robust architecture:

- **Architecture**: Clean Architecture + MVVM (Model-View-ViewModel).
- **UI**: Jetpack Compose (Declarative interface).
- **Dependency Injection**: Hilt (for more decoupled and maintainable code).
- **Database**: Room (Local data persistence).
- **Language**: Kotlin with reactive flows (StateFlow).

## 💡 Why this architecture?
Although I am in the early stages of my career as a developer, I decided to implement **Clean Architecture** to learn how to separate business logic from the interface. This makes the code:
1. **Testable**: Calculation logic is independent of Android.
2. **Scalable**: Easy to add new features in the future.
3. **Organized**: Each layer (`data`, `domain`, `ui`) has a clear responsibility.

## 📸 Screenshots
*(You can upload app images here once you have them)*

---
Developed with ❤️ by **[YOUR NAME OR USERNAME]**
- [LinkedIn](https://www.linkedin.com/in/eduardo-pinto-producer/)
- [Portfolio](https://github.com/Eddiexspansk/)
