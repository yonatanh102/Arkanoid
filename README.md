# Arkanoid - Advanced JavaFX 2D Game

An object-oriented, highly modular 2D Arkanoid clone built from scratch using Java and JavaFX. 
This project goes beyond a simple tutorial by implementing a custom game engine, data-driven level configuration, advanced physics, and a robust testing suite.

## ✨ Key Features

* **Custom Game Engine:** Built with a strict separation of concerns utilizing `Sprite` and `Collidable` interfaces.
* **Advanced Collision Detection:** Implements trajectory-based continuous collision detection (CCD) to prevent "tunneling" (balls passing through blocks), utilizing a custom geometry physics engine (`Line`, `Point`, `Rectangle`).
* **5-Zone Paddle Physics:** The paddle is dynamically divided into 5 regions, changing the ball's bounce angle based on the impact location.
* **Data-Driven Level Design:** Levels are entirely configurable via external JSON files. You can dynamically adjust block layouts, ball speeds, paddle properties, and background assets (images/colors) without touching the source code.
* **Power-Up System:** Includes a fully extensible power-up hierarchy (Multi-Ball, Expand/Shrink Paddle, etc.) triggered by an Observer pattern (`PowerUpSpawner`).
* **Polished HUD & UI:** A sleek, modern Information Panel displays live game stats (Lives, Score, Level Name) utilizing JavaFX Canvas rendering.
* **Unit Tested:** Includes a comprehensive JUnit 5 test suite covering core geometry, physics math, state management, and edge cases.

## 🛠️ Tech Stack

* **Language:** Java 17
* **GUI Framework:** JavaFX
* **Build Tool:** Maven
* **Testing:** JUnit 5
* **Data Parsing:** Gson (for JSON level parsing)

## 🚀 How to Run

### Prerequisites
Make sure you have **Java 17+** and **Maven** installed on your system.

### Running the Game
Navigate to the root directory of the project (where the `pom.xml` is located) and run the following command in your terminal:


mvn clean javafx:run

(Note: If you are using VS Code without a global Maven variable, use the absolute path to your mvn.cmd executable).

### Running the Tests
To execute the automated JUnit test suite for the physics and geometry engine:

mvn test

### 🎮 Controls
- Left Arrow: Move paddle left

- Right Arrow: Move paddle right

- Avoid dropping the ball to save your lives!

### 🏗️ Architecture Highlights
- Physics: The Ball class uses predictive movement, checking the Line of its future trajectory against the Environment before actually moving, preventing overlap bugs.

- State Management: Uses a Counter class passed by reference to decouple the game logic from the UI.

- Memory Management: Implemented object recycling (paddle size resets instead of recreation) to prevent memory leaks and "ghost" collisions with lingering power-ups.
