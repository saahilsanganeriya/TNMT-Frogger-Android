# TMNT Frogger: technical notes

How the code works. Line numbers refer to `app/src/main/java/com/example/s0/MainActivity.java` (559 lines), which holds
all the game logic. `Player.java`, `Vehicle.java` and `GamePad.java` are empty classes.

## Build set-up

| Setting | Value | Where |
|---|---|---|
| Android Gradle Plugin | 7.4.1 | `build.gradle` |
| Gradle | 7.5 | `gradle/wrapper/gradle-wrapper.properties` (the wrapper JAR is not committed) |
| compileSdk / targetSdk | 32 | `app/build.gradle` |
| minSdk | 27 (Android 8.1) | `app/build.gradle` |
| Java source level | 1.8 | `app/build.gradle` |
| Libraries | appcompat 1.4.0, material 1.4.0, constraintlayout 2.1.2 | `app/build.gradle` |

`app/build.gradle` switches on view binding, but the code looks views up with `findViewById`.

## Tests

`app/src/androidTest/java/com/example/s0/ExampleInstrumentedTest.java` has 40 tests, grouped by sprint (2 to 5): goal
bounds, vehicle bounds, the water score checks and username checks. A comment above each test names the group member who
wrote it. Each test mocks `MainActivity` with Mockito and only stubs one of its methods
(`when(...).thenReturn(...)`); none calls the real method or asserts anything, so they do not check the game code.
They are instrumented tests: `gradle connectedDebugAndroidTest` runs them on a connected device or emulator.

## Screens

The app has one activity. Each screen is a layout that `MainActivity` loads with `setContentView`, and the layouts call
its public methods through `android:onClick`.

```
activity_main.xml       title                    Start   -> startConfig()
        |
configuration.xml       username, difficulty,    PLAY    -> startGame()  (checks all three are set)
        |               turtle
activity_game_screen.xml                                  -> gamePlay()   (starts the timers)
        |
        +-- all lives lost ----> game_over.xml   Restart -> startConfig()   Exit -> exitGame()
        +-- goal reached ------> game_win.xml    Restart -> startConfig()   Exit -> exitGame()
```

- Set-up (lines 100–193): ENTER rejects an empty or whitespace-only username; PLAY refuses to start until a username,
  a difficulty and a turtle are chosen, and says which is missing.
- Lives (lines 209–221): Easy 5, Medium 3, Hard 1.
- `exitGame` (lines 516–519) calls `finish()` and then `System.exit(0)`.

## Game loop

Two `java.util.Timer`s drive the game. Their tasks post work to the UI thread through a `Handler`.

- **Player** (lines 250–255, 334–366): every 10 ms, `changePos` moves the turtle 4 pixels in each direction whose arrow
  is held, kept within x = 2 to 970 and y = 180 to 1870. The arrow buttons set the direction flags in `onTouch`
  (lines 368–395) on press and clear them all on any other touch event, such as a release or a slide.
- **Vehicles and logs** (lines 294–331): `moveObject` gives every vehicle and log its own task on the second timer,
  every 6 ms. Each tick moves the view by its lane speed in pixels. A view that leaves the range x = −500 to 1350 jumps
  back to the far side. For vehicles, the same tick runs the collision check.

Lanes (constants at lines 32–49, set-up at lines 257–290; a negative speed moves left):

| Lane | y | Speed (pixels per 6 ms) | Objects |
|---|---:|---:|---|
| Road 1 | 1600 | −1 | 2 cars |
| Road 2 | 1480 | +1 | bus, construction truck, pickup truck |
| Road 3 | 1330 | −2 | trash truck, car |
| Road 4 | 1180 | +3 | pickup truck, government car |
| Road 5 | 1030 | −3 | car, 2 police cars |
| River 1 | 720 | −1 | 2 logs |
| River 2 | 590 | +1 | 2 logs |
| River 3 | 460 | −2 | 2 logs |
| River 4 | 330 | +3 | 2 logs |

Starting x positions come from eleven slots, 0 to 1200 pixels in steps of 120 (lines 50–60).

## Collisions, score and lives

- **Vehicles** (lines 457–486): the turtle is a box 40 pixels wide and 54 high around its position; a vehicle is
  70 × 40. An overlap calls `loseLife`.
- **Score** (lines 397–411): a step up scores only if it takes the turtle higher than it has been since its last life
  began; it scores 2 inside the river's rows, 1 elsewhere.
- **Losing a life** (lines 488–514): the score goes back to 0 and the turtle back to the start. At 0 lives both timers
  stop and the game-over screen shows the score.
- **Winning** (lines 441–454, 521–531): once the turtle is more than 100 pixels above the bottom edge of the goal strip,
  both timers stop and the win screen shows the score.

## Known gaps

- **The river is not a hazard.** `checkTouchingWater` (lines 428–439) is never called, and the log check and its call
  are commented out (lines 309–317, 533–558). Logs move, but the turtle can walk across the water.
- **Fixed pixel positions.** Lanes, limits and collision boxes are pixel values, not scaled to the screen size.
- **Timers outlive the screen.** Nothing stops the timers when the activity pauses; they stop only when the game ends.
- **Unused click handlers.** The arrow buttons in `activity_game_screen.xml` name `onLeftTouch`, `onUpTouch`,
  `onDownTouch` and `onRightTouch`, which do not exist. They never fire, because `onTouch` consumes every touch.
- **Unused strings.** `strings.xml` keeps 20 strings that nothing uses, among them a "MyStack App" set and frog
  colours. The app's launcher name is `S0`.
