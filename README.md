# TMNT Frogger for Android

A Frogger-style game for Android, written in Java with the plain Android SDK (no game engine). You pick one of the four
Teenage Mutant Ninja Turtles and cross five lanes of traffic and a river to reach the goal at the top of the screen. It
was a group project for CS 2340 (Objects and Design) at Georgia Tech in spring 2023.

Demo: [video on YouTube](https://www.youtube.com/shorts/DH13POnPiRk). The repository also holds a demo recording,
`demo video.mp4`.

## Who built what

This was coursework by a group of five, built over five sprints. The group's Sprint 1 design document is in the
repository (`Design Delivearbles S1.docx`: the noun list, conceptual classes, a use-case diagram and a domain model).
Saahil Sanganeriya uploaded the finished app to GitHub in one commit on 17 September 2023 and maintains this repository.

The tests are the only part with recorded authors: a comment above each of the 40 tests in
`app/src/androidTest/java/com/example/s0/ExampleInstrumentedTest.java` names who wrote it, and Saahil wrote 8 of them,
two in each of sprints 2 to 5. The repository does not record who wrote which part of the game code.

## How to play

1. Tap **Start**. On the set-up screen, type a username and tap **ENTER** (it cannot be empty or only spaces), pick a
   difficulty and a turtle, then tap **PLAY**.
2. Hold the arrow buttons to move.
3. A car or truck that touches you costs a life, sends you back to the start and resets your score to 0.
4. You score 1 point for each step further up the screen than you have been in this life, and 2 points per step in the
   river.
5. Reach the goal strip at the top to win. Win or lose, the end screen shows your final score, with **Restart** (back to
   set-up) and **Exit**.

| Difficulty | Lives |
|---|---:|
| Easy | 5 |
| Medium | 3 |
| Hard | 1 |

Five road lanes carry 12 vehicles, the lanes moving in alternating directions at three speeds. The river has four lanes
of moving logs, but it is not a hazard yet: the water and log checks are commented out, so you can walk straight across.
These values are in `app/src/main/java/com/example/s0/MainActivity.java`: lives at lines 209–221, lanes and speeds at
lines 32–49 and 257–290, scoring at lines 397–411, the commented-out river checks at lines 309–317 and 533–558.

## Build and run

The build files pin Android Gradle Plugin 7.4.1 and Gradle 7.5, compile against Android SDK platform 32 (API 32), and
run on Android 8.1 (API 27) or newer. Gradle needs JDK 11 or newer.

**Android Studio.** Open the folder in Android Studio Electric Eel (2022.1.1) or newer, let Gradle sync, and run the
`app` configuration on a device or emulator.

**Command line.** The repository has `gradle/wrapper/gradle-wrapper.properties` but not `gradle-wrapper.jar`, so
`./gradlew` does not run. Use an installed Gradle 7.5:

```sh
export ANDROID_HOME=/path/to/android-sdk   # with platforms;android-32 and build-tools;30.0.3 installed
gradle assembleDebug                       # writes app/build/outputs/apk/debug/app-debug.apk
adb install app/build/outputs/apk/debug/app-debug.apk
```

`gradle assembleDebug` was tested on Linux with JDK 17, Gradle 7.5, SDK platform 32 and build-tools 30.0.3 (October
2026). The app installs under the launcher name **S0** (its `app_name` string).

## How it works

- **One activity.** `MainActivity.java` (559 lines) holds all the game logic. It swaps five layouts with
  `setContentView`: title (`activity_main.xml`), set-up (`configuration.xml`), game (`activity_game_screen.xml`), game
  over (`game_over.xml`) and win (`game_win.xml`). `Player.java`, `Vehicle.java` and `GamePad.java` are empty classes.
- **Two timers.** A `java.util.Timer` task every 10 ms moves the turtle 4 pixels while an arrow is held. Every vehicle
  and log has its own task on a second timer, every 6 ms, that moves it by its lane speed and wraps it to the other side
  of the screen.
- **Collisions.** Each vehicle tick checks whether a box around the turtle overlaps a box around that vehicle.
- **Fixed positions.** Lanes and limits are pixel values in the code (road lanes at y = 1030 to 1600, river lanes at
  y = 330 to 720, the turtle kept within x = 2 to 970 and y = 180 to 1870), not scaled to the screen.

[TECHNICAL_OVERVIEW.md](TECHNICAL_OVERVIEW.md) goes through the code in more detail.

## Third-party artwork

Teenage Mutant Ninja Turtles, its character names and the turtle images in `app/src/main/res/drawable/` (`tmnt.jpg`,
`raphael.png`, `raphael55.png`, `leonardo.png`, `donatello.png`, `michelangelo.png`) are trademarks and artwork of their
owners. This is an unofficial student project, not affiliated with or endorsed by them. The repository does not record
where the other images (vehicles, road, river, buttons) came from.

## Licence

There is no licence file, so default copyright applies: the code is a student group's coursework (see
[Who built what](#who-built-what)), and the TMNT artwork belongs to its owners.
