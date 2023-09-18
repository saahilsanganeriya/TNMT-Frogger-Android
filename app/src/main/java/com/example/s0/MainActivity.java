package com.example.s0;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main behaviors of the Frogger game.
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final boolean VEHICLE = true;
    private static final boolean LOG = false;
    private static final int RAPHAEL = 1;
    private static final int DONATELLO = 2;
    private static final int LEONARDO = 3;
    private static final int MICHELANGELO = 4;
    private static final int EASY = 1;
    private static final int MEDIUM = 2;
    private static final int HARD = 3;
    private static final int RIVER_Y_LANE1 = 720;
    private static final int RIVER_Y_LANE2 = 590;
    private static final int RIVER_Y_LANE3 = 460;
    private static final int RIVER_Y_LANE4 = 330;
    private static final int RIVER_SPEED_LANE1 = -1;
    private static final int RIVER_SPEED_LANE2 = 1;
    private static final int RIVER_SPEED_LANE3 = -2;
    private static final int RIVER_SPEED_LANE4 = 3;
    private static final int Y_LANE1 = 1600;
    private static final int Y_LANE2 = 1480;
    private static final int Y_LANE3 = 1330;
    private static final int Y_LANE4 = 1180;
    private static final int Y_LANE5 = 1030;
    private static final int SPEED_LANE1 = -1;
    private static final int SPEED_LANE2 = 1;
    private static final int SPEED_LANE3 = -2;
    private static final int SPEED_LANE4 = 3;
    private static final int SPEED_LANE5 = -3;
    private static final int X_SLOT0 = 0;
    private static final int X_SLOT1 = 120;
    private static final int X_SLOT2 = 240;
    private static final int X_SLOT3 = 360;
    private static final int X_SLOT4 = 480;
    private static final int X_SLOT5 = 600;
    private static final int X_SLOT6 = 720;
    private static final int X_SLOT7 = 840;
    private static final int X_SLOT8 = 960;
    private static final int X_SLOT9 = 1080;
    private static final int X_SLOT10 = 1200;


    //Animation Attributes
    private float spriteX;
    private float spriteY;
    private boolean actionUp;
    private boolean actionDown;
    private boolean actionLeft;
    private boolean actionRight;
    private ImageView sprite;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Timer vehicleTimer = new Timer();
    private final Handler vehicleHandle = new Handler();
    private float vehicleTopY;

    private EditText usernameInput;
    private TextView usernameDisplay;
    private TextView difficultyDisplay;
    private TextView playDisplay;
    private TextView spriteDisplay;
    private TextView scoreText;
    private String playerName;
    private float minGoalPos;
    private int selectedDifficulty = 0;
    private int selectedCharacter = 0;
    private float maxYAxis = 1870;
    private int score = 0;
    private int lives = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startConfig(View view) {
        setContentView(R.layout.configuration);
        usernameInput = (EditText) findViewById(R.id.usernameText);
        usernameDisplay = (TextView) findViewById(R.id.usernameFeedback);
        difficultyDisplay = (TextView) findViewById(R.id.difficultyText);
        spriteDisplay = (TextView) findViewById(R.id.spriteText);
        playDisplay = (TextView) findViewById(R.id.playFeedback);
    }


    public Boolean isNameEmpty(String name) {
        if (playerName.equals("")) {
            return true;
        } // if
        return false;
    }

    public Boolean nameContainsWhitespace(String name) {
        if (playerName.trim().isEmpty()) {
            return true;
        } // if
        return false;
    }
    public void onUsernameEnter(View view) {
        playerName = usernameInput.getText().toString();
        if (isNameEmpty(playerName)) {
            updateUsernameFeedback("The username cannot be empty.");
        } else if (nameContainsWhitespace(playerName)) {
            updateUsernameFeedback("The username cannot be whitespace only.");
        } else {
            updateUsernameFeedback("Username Accepted");
        }
    }

    private void updateUsernameFeedback(String message) {
        usernameDisplay.setText(message);
    }

    public void onEasy(View view) {
        updateDifficultyText("Easy it is!");
        selectedDifficulty = EASY;
    }

    public void onMedium(View view) {
        updateDifficultyText("Medium for the veteran!");
        selectedDifficulty = MEDIUM;
    }

    public void onHard(View view) {
        updateDifficultyText("Hard, a challenge!");
        selectedDifficulty = HARD;
    }

    private void updateDifficultyText(String message) {
        difficultyDisplay.setText(message);
    }

    public void onRaphael(View view) {
        updateSpriteText("You have chosen Raphael!");
        selectedCharacter = RAPHAEL;
    }

    public void onMichelangelo(View view) {
        updateSpriteText("You have chosen Michelangelo!");
        selectedCharacter = MICHELANGELO;
    }

    public void onDonatello(View view) {
        updateSpriteText("You have chosen Donatello!");
        selectedCharacter = DONATELLO;
    }

    public void onLeonardo(View view) {
        updateSpriteText("You have chosen Leonardo!");
        selectedCharacter = LEONARDO;
    }

    private void updateSpriteText(String message) {
        spriteDisplay.setText(message);
    }

    public void startGame(View view) {
        if (playerName == null) {
            updatePlayFeedback("Please input a valid username.");
        } else if ((playerName.equals("")) || (playerName.trim().isEmpty())) {
            updatePlayFeedback("Please input a valid username.");
        } else if (selectedDifficulty == 0) {
            updatePlayFeedback("Please select a difficulty.");
        } else if (selectedCharacter == 0) {
            updatePlayFeedback("Please select a character.");
        } else {
            gamePlay(view);
        }
    }

    private void updatePlayFeedback(String message) {
        playDisplay.setText(message);
    }

    @SuppressLint("CutPasteId")
    public void gamePlay(View view) {
        setContentView(R.layout.activity_game_screen);
        TextView startingLivesView = findViewById(R.id.starting_lives_view);
        ImageView playerCharacterView = findViewById(R.id.player_character_view);
        TextView usernameGameplayView = findViewById(R.id.usernameGameplay);

        spriteY = 1600;
        usernameGameplayView.setText(playerName);

        switch (selectedDifficulty) {
        case EASY:
            lives = 5;
            break;
        case MEDIUM:
            lives = 3;
            break;
        case HARD:
            lives = 1;
            break;
        default:
            lives = 0;
        }
        startingLivesView.setText(lives + "");

        switch (selectedCharacter) {
        case RAPHAEL:
            playerCharacterView.setImageResource(R.drawable.raphael55);
            break;
        case DONATELLO:
            playerCharacterView.setImageResource(R.drawable.donatello);
            break;
        case LEONARDO:
            playerCharacterView.setImageResource(R.drawable.leonardo);
            break;
        case MICHELANGELO:
            playerCharacterView.setImageResource(R.drawable.michelangelo);
            break;
        default:
            playerCharacterView.setImageResource(R.drawable.firetruck);
        }




        findViewById(R.id.upButton).setOnTouchListener(this);
        findViewById(R.id.downButton).setOnTouchListener(this);
        findViewById(R.id.leftButton).setOnTouchListener(this);
        findViewById(R.id.rightButton).setOnTouchListener(this);


        timer.schedule(new TimerTask() { // Timer controlling sensitivity of sprite movement
            @Override
            public void run() {
                handler.post(() -> changePos(view));
            }
        }, 0, 10);

        // lane 1 set up
        moveObject(view, R.id.lane1cara, SPEED_LANE1, X_SLOT4, Y_LANE1, VEHICLE);
        moveObject(view, R.id.lane1card, SPEED_LANE1, X_SLOT9, Y_LANE1, VEHICLE);

        // lane 2 set up
        moveObject(view, R.id.lane2bus, SPEED_LANE2, X_SLOT2, Y_LANE2, VEHICLE);
        moveObject(view, R.id.lane2constructiontruck, SPEED_LANE2, X_SLOT9, Y_LANE2, VEHICLE);
        moveObject(view, R.id.lane2pickuptruckb, SPEED_LANE2, X_SLOT7, Y_LANE2, VEHICLE);

        // lane 3 set up
        moveObject(view, R.id.lane3trashtruck, SPEED_LANE3, X_SLOT2, Y_LANE3, VEHICLE);
        moveObject(view, R.id.lane3carf, SPEED_LANE3, X_SLOT10, Y_LANE3, VEHICLE);

        // lane 4 set up
        moveObject(view, R.id.lane4pickuptrucka, SPEED_LANE4, X_SLOT6, Y_LANE4, VEHICLE);
        moveObject(view, R.id.lane4govtcar, SPEED_LANE4, X_SLOT8, Y_LANE4, VEHICLE);

        // lane 5 set up
        moveObject(view, R.id.lane5card, SPEED_LANE5, X_SLOT3, Y_LANE5, VEHICLE);
        moveObject(view, R.id.lane5police1, SPEED_LANE5, X_SLOT8, Y_LANE5, VEHICLE);
        moveObject(view, R.id.lane5police2, SPEED_LANE5, X_SLOT10, Y_LANE5, VEHICLE);
        
        // river lane 1 set up
        moveObject(view, R.id.riverlane1logA, RIVER_SPEED_LANE1, X_SLOT3, RIVER_Y_LANE1, LOG);
        moveObject(view, R.id.riverlane1logB, RIVER_SPEED_LANE1, X_SLOT8, RIVER_Y_LANE1, LOG);
        // river lane 2 set up
        moveObject(view, R.id.riverlane2logA, RIVER_SPEED_LANE2, X_SLOT1, RIVER_Y_LANE2, LOG);
        moveObject(view, R.id.riverlane2logB, RIVER_SPEED_LANE2, X_SLOT9, RIVER_Y_LANE2, LOG);
        // river lane 3 set up
        moveObject(view, R.id.riverlane3logA, RIVER_SPEED_LANE3, X_SLOT5, RIVER_Y_LANE3, LOG);
        moveObject(view, R.id.riverlane3logB, RIVER_SPEED_LANE3, X_SLOT10, RIVER_Y_LANE3, LOG);
        // river lane 4 set up
        moveObject(view, R.id.riverlane4logA, RIVER_SPEED_LANE4, X_SLOT2, RIVER_Y_LANE4, LOG);
        moveObject(view, R.id.riverlane4logB, RIVER_SPEED_LANE4, X_SLOT9, RIVER_Y_LANE4, LOG);
    }

    //Method handling vehicle movement
    public void moveObject(View view, int id, int speed, int xPos, int yPos, boolean vehicleorlog) {
        ImageView vehicle = (ImageView) findViewById(id);
        vehicle.setX(xPos);
        vehicle.setY(yPos);
        vehicleTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                vehicleHandle.post(new Runnable() { //handle object does not terminate timer task
                    public void run() {
                        float curTruckX = vehicle.getX();
                        if (curTruckX < 1351 && curTruckX > -501) {
                            vehicle.setX(curTruckX + speed);
                            if (vehicleorlog == VEHICLE) {
                                checkEachVehicle(curTruckX + speed, yPos, id, view);
                            }
                            //if (!checkTouchingLog(curTruckX + speed, yPos, id, view)) {
                            //checkTouchingWater(view);
                            // } else {

                            //if ((spriteX + speed > -20) && (spriteX + speed < 990)){
                            //    loseLife(view);
                            //}

                            // }

                        } else {
                            if (speed > 0) {
                                vehicle.setX(-500);
                            } else {
                                vehicle.setX(1350);
                            }
                        } // if
                    }
                });

            } // run
        }, 0, 6);
    } // moveTruck


    public void changePos(View view) {
        if (lives == 0 || checkIfGoalLower(spriteY, minGoalPos)) {
            return;
        } // if
        spriteX = findViewById(R.id.player_character_view).getX();
        spriteY = findViewById(R.id.player_character_view).getY();

        //MoveUp
        if (actionUp && spriteY >= 180) {
            spriteY -= 4;
            incScore(view, spriteY);
        }

        //MoveDown
        if (actionDown && spriteY <= 1870) {
            spriteY += 4;
        }

        //MoveLeft
        if (actionLeft && spriteX >= 2) {
            spriteX -= 4;
        }

        //MoveRight
        if (actionRight && spriteX <= 970) {
            spriteX += 4;
        }

        findViewById(R.id.player_character_view).setX(spriteX);
        findViewById(R.id.player_character_view).setY(spriteY);

        checkTouchingGoal(spriteY, view);
    }

    @Override //Method handling sprite movement with button listeners
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
            case R.id.upButton:
                actionUp = true;
                break;
            case R.id.downButton:
                actionDown = true;
                break;
            case R.id.leftButton:
                actionLeft = true;
                break;
            case R.id.rightButton:
                actionRight = true;
                break;

            default:
                throw new IllegalStateException("Whoops!");
            }
        } else {
            actionUp = false;
            actionDown = false;
            actionLeft = false;
            actionRight = false;
        }
        return true;
    }

    public void incScore(View view, float spriteY) { // Increments score based on sprite y pos
        if (spriteY < maxYAxis) {
            ImageView water = (ImageView) findViewById(R.id.water);
            int maxWater = water.getTop();
            int minWater = water.getBottom();
            if (checkIfWaterLower(spriteY, minWater) && checkIfWaterUpper(spriteY, maxWater)) {
                score = score + 2;
            } else {
                score++;
            } // if
            maxYAxis = spriteY;
            scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setText("Score: " + score);
        } // if
    } // incScore

    public Boolean checkIfWaterLower(float spriteY, float minWater) {
        if (spriteY < minWater) {
            return true;
        } // if
        return false;
    } // checkIfWater

    public Boolean checkIfWaterUpper(float spriteY, float maxWater) {
        if (spriteY > maxWater) {
            return true;
        } // if
        return false;

    } // checkIfWater

    public void checkTouchingWater(View view) {
        if (lives == 0) {
            return;
        }
        spriteY = findViewById(R.id.player_character_view).getY();
        if (((spriteY > RIVER_Y_LANE1 - 10) && (spriteY < RIVER_Y_LANE1 + 10))) {
            //         || ((spriteY > RIVER_Y_LANE2 - 10) && (spriteY < RIVER_Y_LANE2 + 10))
            //         || ((spriteY > RIVER_Y_LANE3 - 10) && (spriteY < RIVER_Y_LANE3 + 10))
            //         || ((spriteY > RIVER_Y_LANE4 - 10) && (spriteY < RIVER_Y_LANE4 + 10))) {
            loseLife(view);
        } // if
    } // checkTouchingWater

    public void checkTouchingGoal(float spriteY, View view) {
        ImageView goal = (ImageView) findViewById(R.id.goal);
        minGoalPos = goal.getBottom();
        if (checkIfGoalLower(spriteY, minGoalPos)) {
            winGame(view);
        } // if
    } // checkTouchingWater

    public Boolean checkIfGoalLower(float spriteY, float minGoal) {
        if ((minGoal - spriteY - 100) > 0) {
            return true;
        } // if
        return false;
    } // checkIfWater


    public void checkEachVehicle(float carX, float carY, int vehicleID, View view) {
        if (lives == 0 || checkIfGoalLower(spriteY, minGoalPos)) {
            return;
        } // if
        spriteX = findViewById(R.id.player_character_view).getX();
        spriteY = findViewById(R.id.player_character_view).getY();

        float spriteTopY = spriteY + 27;
        float spriteBottomY = spriteY - 27;
        float spriteRightX = spriteX + 20;
        float spriteLeftX = spriteX - 20;

        vehicleTopY = carY + 20;
        float vehicleBottomY = carY - 20;
        float vehicleRightX = carX + 35;
        float vehicleLeftX = carX - 35;

        if (checkVehicleBounds(spriteLeftX, vehicleRightX, spriteRightX, vehicleLeftX, spriteTopY,
                vehicleBottomY, spriteBottomY)) {
            loseLife(view);
        } // if
    } // checkTouchingVehicle

    public Boolean checkVehicleBounds(float slx, float vrx, float srx, float vlx, float sty,
                                      float vby, float sby) {
        if ((slx < vrx && srx > vlx) && (sty > vby && sby < vehicleTopY)) {
            return true;
        } // if
        return false;
    } // checkVehicleBounds

    public void loseLife(View view) {
        lives--;
        if (lives <= 0) {
            timer.cancel();
            vehicleTimer.cancel();
            setContentView(R.layout.game_over);
            TextView gameOver = (TextView) findViewById(R.id.scoreFinalText);
            gameOver.setText("Final Score: " + score);
            timer = new Timer();
            vehicleTimer = new Timer();
            maxYAxis = 1870;
            score = 0;
        } else {
            score = 0;
            scoreText.setText("Score: " + score);
            maxYAxis = 1870;
            ImageView startArea = (ImageView) findViewById(R.id.start_area);
            int maxStart = startArea.getTop() - 500;
            int minStart = startArea.getBottom();
            int startPos = (maxStart + minStart) / 2;
            ImageView playerCharacterView = findViewById(R.id.player_character_view);
            playerCharacterView.setY(startPos);
            playerCharacterView.setX(450);
            TextView startingLivesView = findViewById(R.id.starting_lives_view);
            startingLivesView.setText(lives + "");
        }
    } // loseLife

    public void exitGame(View view) {
        finish();
        System.exit(0);
    }

    public void winGame(View view) {
        timer.cancel();
        vehicleTimer.cancel();
        timer = new Timer();
        vehicleTimer = new Timer();
        setContentView(R.layout.game_win);
        TextView gameWin = (TextView) findViewById(R.id.scoreFinalTextWin);
        gameWin.setText("Final Score: " + score);
        maxYAxis = 1870;
        score = 0;
    } // winGame

    /*
    public boolean checkTouchingLog(float logX, float logY, int logID, View view) {
        if (lives == 0 || checkIfGoalLower(spriteY, minGoalPos)) {
            return false;
        } // if
        spriteX = findViewById(R.id.player_character_view).getX();
        spriteY = findViewById(R.id.player_character_view).getY();

        float spriteTopY = spriteY + 27;
        float spriteBottomY = spriteY - 27;
        float spriteRightX = spriteX + 20;
        float spriteLeftX = spriteX - 20;

        float logTopY = logY + 30;
        float logBottomY = logY - 30;
        float logRightX = logX + 65;
        float logLeftX = logX - 65;

        if ((spriteLeftX < logRightX && spriteRightX > logLeftX)
        && (spriteTopY > logBottomY && spriteBottomY < logTopY)) {
            return true;
        } // if
        return false;
    } // checkTouchingVehicle

     */

} // class