# TMNT Frogger Android Game 🐢🚗

![TMNT Logo](app/src/main/res/drawable/tmnt.jpg)

An exciting Android adaptation of the classic Frogger arcade game featuring beloved Teenage Mutant Ninja Turtles characters. Navigate through traffic-filled roads and treacherous rivers to reach safety while avoiding vehicles and obstacles.

## 🎮 Game Features

- **Character Selection**: Choose from 4 iconic TMNT characters:
  - ![Raphael](app/src/main/res/drawable/raphael55.png) Raphael
  - ![Leonardo](app/src/main/res/drawable/leonardo.png) Leonardo  
  - ![Donatello](app/src/main/res/drawable/donatello.png) Donatello
  - ![Michelangelo](app/src/main/res/drawable/michelangelo.png) Michelangelo

- **Difficulty Levels**: 
  - **Easy**: 5 lives
  - **Medium**: 3 lives  
  - **Hard**: 1 life

- **Dynamic Gameplay**: 
  - 5 lanes of moving vehicles with varying speeds
  - 4 river lanes with floating logs
  - Score system with bonus points for water crossing
  - Touch-based directional controls

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- Android SDK with minimum API level 27
- Target SDK 32

### Installation
1. Clone this repository
2. Open the project in Android Studio
3. Build and run on your Android device or emulator

## 🎯 How to Play

![Game Controls](app/src/main/res/drawable/upbutton.png) ![Game Controls](app/src/main/res/drawable/downbutton.png) ![Game Controls](app/src/main/res/drawable/leftbutton.png) ![Game Controls](app/src/main/res/drawable/rightbutton.png)

1. **Setup**: Enter your username, select difficulty, and choose your TMNT character
2. **Movement**: Use the directional buttons to navigate your character
3. **Objective**: Cross the road and river safely to reach the goal areas
4. **Scoring**: Earn points for forward progress, with bonus points in water areas
5. **Lives**: Avoid vehicles and water hazards - collisions cost you a life!

## 🛠️ Technical Highlights

- **Pure Android Java**: No external game engines, built with native Android SDK
- **Custom Animation System**: Smooth sprite movement with timer-based animations
- **Collision Detection**: Precise boundary checking for vehicles and obstacles
- **Dynamic Object Management**: Multiple moving vehicles and logs with different speeds
- **Responsive UI**: Constraint layout design optimized for various screen sizes

## 📱 Screenshots

### Main Menu
![Safe Area](app/src/main/res/drawable/safe_area.png)

### Gameplay Elements
- ![Road](app/src/main/res/drawable/road.png) **Road sections** with moving vehicles
- ![Water](app/src/main/res/drawable/water.png) **Water areas** with floating logs  
- ![Goal](app/src/main/res/drawable/goal.png) **Goal areas** to reach safely

### Vehicles & Obstacles
![Bus](app/src/main/res/drawable/bus.png) ![Police Car](app/src/main/res/drawable/policecar.png) ![Fire Truck](app/src/main/res/drawable/firetruck.png) ![Construction Truck](app/src/main/res/drawable/constructiontruck.png)

## 🏆 Game Mechanics

- **Lives System**: Start with 1-5 lives based on difficulty
- **Score Progression**: Points awarded for moving forward
- **Water Bonus**: Double points when crossing water areas
- **Collision Physics**: Realistic hit detection with vehicle boundaries
- **Win Condition**: Successfully reach the goal area at the top

## 🔧 Architecture

The game follows a single-activity architecture with multiple layout views:
- `MainActivity.java`: Core game logic and state management
- Configuration screen for game setup
- Game screen with real-time gameplay
- Win/lose screens with score display

---

*A nostalgic tribute to classic arcade gaming with modern Android development practices and TMNT theming.*