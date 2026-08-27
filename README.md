# 💰 TipApp - Fair Tip Distribution for Waiters

TipApp was inspired by my experience working as a waiter. I noticed that tip distribution—an essential part of daily work in hospitality—was often still managed manually using pen and paper. This process could be slow, repetitive, and prone to mistakes, which made me wonder: why not build a simpler and more reliable solution?

TipApp helps hospitality teams distribute tips fairly based on the number of hours worked by each team member. It makes the process faster, clearer, and more accurate while reducing the need for manual calculations.

This project also represents my learning journey in modern Android development. Through TipApp, I am exploring current Android tools, best practices, and architectural patterns recommended by the developer community.
## 🚀 Features
- **Real-Time Calculation**: Enter the total amount and hours for each waiter to see the distribution instantly.
- **Local Persistence**: Data is not lost when closing the app thanks to a local database.
- **Modern Interface**: Fluid and adaptive design using Jetpack Compose.
- **Dark Mode Support**: Full support for system dark and light themes.
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
Developed by Eduardo Pinto

- [LinkedIn](https://www.linkedin.com/in/eduardo-pinto-producer/)
- [Portfolio](https://github.com/Eddiexspansk/)
