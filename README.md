# CoinSphere - Cryptocurrency Market Mobile application

**CoinSphere** is a modern Android mobile app built using **Kotlin** and **Jetpack Compose**. It allows users to view **real-time cryptocurrency prices**, explore detailed coin information, and manage a list of their **favorite coins**. Designed with a clean UI and powered by reactive architecture, CoinSphere delivers a fast and smooth crypto tracking experience.

---
## Screenshots

<table>
  <tr>
    <th>SplashScreen</th>
    <th>Loading Screen</th>
    <th>Market Screen</th>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/877faeac-9441-472e-948c-3711a50f224f" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/7a69e70a-a773-43ab-90ec-d2b7a72c86ca" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/3f217815-f3db-477d-a1c4-4e091125f715"  width="250"/>
    </td>
  </tr>
</table>
<table>
  <tr>
    <th>Search Screen</th>
    <th>Settings Screen</th>
    <th>Error Screen</th>
  </tr>
  <tr>
    <td align="center">
      <img  src="https://github.com/user-attachments/assets/b9a14881-dc19-4f9e-8bc5-ce3dd54476b3" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/6449c99a-f1d5-4030-82dd-cb793540cc80" width="250"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/d1515e8c-a0f8-4d8f-9c9f-e07310b43dab" width="250"/>
    </td>
  </tr>
</table>




---
# Model-View-ViewModel

MVVM is a software architectural pattern that separates the presentation logic (View) from the business logic (Model) and introduces a middle layer (ViewModel) to mediate communication between them. This design pattern promotes better code organization, testability, and maintainability of Android applications.
<img width="960" height="571" alt="image" src="https://github.com/user-attachments/assets/fb417718-ec6f-4aa2-9810-dfa88e7035f7" />


## Core Features

- **Live Market Data** — Displays real-time prices, ranks, and change percentages of popular cryptocurrencies
- **Search for coins** — Search for coins
- **Coin Details View** — See in-depth info about each coin, including price, change, symbol, and image
- **Favorites** — Users can mark coins they want to track closely
- **Pull-to-Refresh** — Easily refresh market data with a swipe
- **Error/Empty State Handling** — UI reacts gracefully to no internet or failed API calls
- **Dark/Light Mode Support** — Follows system theme preferences

---

## Tech Stack

| Layer             | Tool / Library        |
|------------------|------------------------|
| **Language**     | Kotlin                 |
| **UI Toolkit**   | Jetpack Compose        |
| **Architecture** | MVVM                   |
| **DI**           | Hilt                   |
| **Networking**   | Retrofit + OkHttp      |
| **Async/Reactive**| Coroutines + Flow     |
| **Image Loading**| Coil                   |
| **Navigation**   | Jetpack Compose Navigation |
| **Local Storage**| Room (for favorites)   |
| **State Handling**| StateFlow, ViewModel  |
| **API Provider** | CoinGecko (public API) |

---

## Project Structure

```text
com.coinsphere
│
├── data
│   ├── remote        # Retrofit API services and DTOs
│   ├── local         # Room database, DAOs, entities
│   └── repository    # Repository interface and implementation
│
├── di                # Hilt modules for dependency injection
│
├── ui
│   ├── screens
│   │   ├── market    # Main screen showing list of coins
│   │   ├── details   # Screen for individual coin info
│   │   └── favorites # User's favorite coins
│   └── components    # Reusable Composables (e.g., CoinItem, TopBar)
│
├── navigation        # Navigation graph and screen routes
│
├── utils             # Constants, formatters, and helper functions
│
└── MainActivity.kt   # App entry point and NavHost
```


