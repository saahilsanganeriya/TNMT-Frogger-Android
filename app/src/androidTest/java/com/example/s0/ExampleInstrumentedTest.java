package com.example.s0;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public MainActivity activity;

    @Before
    public void setup() {
        activity = mock(MainActivity.class);
    }

    // Sprint 5

    @Test
    // Gavin
    public void checkGoalBoundsAbove() {
        when(activity.checkIfGoalLower(0, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Gavin
    public void checkGoalBoundsBelow() {
        when(activity.checkIfGoalLower(300, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void checkGoalBoundsBelowButInBuffer() {
        when(activity.checkIfGoalLower(150, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void checkGoalBoundsBelowOneOff() {
        when(activity.checkIfGoalLower(201, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Nikkolas
    public void checkGoalBoundsBelowOneInBuffer() {
        when(activity.checkIfGoalLower(199, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Nikkolas
    public void checkGoalBoundsOneAbove() {
        when(activity.checkIfGoalLower(99, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void checkGoalBoundsOutOfBoundsUp() {
        when(activity.checkIfGoalLower(-1, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void checkGoalBoundsOutOfBoundsDown() {
        when(activity.checkIfGoalLower(1000000, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void checkGoalBoundsMiddle() {
        when(activity.checkIfGoalLower(50, 100)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void checkGoalBoundsTopOfScreen() {
        when(activity.checkIfGoalLower(1, 100)).thenReturn(Boolean.TRUE);
    }


    // Sprint 4

    @Test
    // Gavin
    public void checkCarBoundsLargeInside() {
        when(activity.checkVehicleBounds(20, 200, 40, 0, 10, 200, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Gavin
    public void checkCarBoundsSmallInside() {
        when(activity.checkVehicleBounds(20, 45, 40, 15, 10, 45, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Nikkolas
    public void checkCarBoundsOneOffInside() {
        when(activity.checkVehicleBounds(20, 41, 40, 19, 10, 41, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Nikkolas
    public void checkCarBoundsOutsideLeftInsideY() {
        when(!activity.checkVehicleBounds(20, 50, 40, 45, 10, 50, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void checkCarBoundsOutsideLeftOutsideY() {
        when(!activity.checkVehicleBounds(20, 50, 40, 45, 10, 100, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void checkCarBoundsInsideLeftOutsideY() {
        when(!activity.checkVehicleBounds(20, 45, 40, 15, 10, 100, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void checkCarBoundsOutsideRightInsideY() {
        when(!activity.checkVehicleBounds(60, 50, 70, 45, 10, 50, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void checkCarBoundsOutsideRightOutsideY() {
        when(!activity.checkVehicleBounds(60, 50, 70, 45, 60, 50, 70)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void checkCarBoundsExtremeLeftEdge() {
        when(activity.checkVehicleBounds(60, 80, 70, 69, 10, 50, 40)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void checkCarBoundsCornerTouchingCorner() {
        when(activity.checkVehicleBounds(60, 80, 70, 69, 10, 11, 40)).thenReturn(Boolean.TRUE);
    }

    // Sprint 3

    @Test
    // Gavin
    public void scoreIncWaterLower()  {
        when(activity.checkIfWaterLower(2, 1)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Gavin
    public void scoreIncWaterUpper()  {
        when(activity.checkIfWaterUpper(2, 3)).thenReturn(Boolean.TRUE);
    }


    @Test
    // Nikkolas
    public void scoreNoIncWaterLower()  {
        when(!activity.checkIfWaterLower(0, 1)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Nikkolas
    public void scoreNoIncWaterUpper()  {
        when(!activity.checkIfWaterUpper(4, 3)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void scoreIncWaterTotal()  {
        when(activity.checkIfWaterLower(2, 1)
              && activity.checkIfWaterUpper(2, 3)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void scoreUpperBoundOff()  {
        when(activity.checkIfWaterLower(3, 1)
                && !activity.checkIfWaterUpper(3, 2)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void scoreLowerBoundOff()  {
        when(!activity.checkIfWaterLower(2, 3)
                && activity.checkIfWaterUpper(2, 5)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void noScoreOnLowerBound()  {
        when(!activity.checkIfWaterLower(2, 2)
                && activity.checkIfWaterUpper(2, 5)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void noScoreOnUpperBound()  {
        when(activity.checkIfWaterLower(5, 2)
                && !activity.checkIfWaterUpper(5, 5)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void noScoreOnScreenBound()  {
        when(activity.checkIfWaterLower(1080, 2)
                && !activity.checkIfWaterUpper(1080, 5)).thenReturn(Boolean.TRUE);
    }

    // Sprint 2

    @Test
    // Gavin
    public void nameIsWhiteSpace() {
        when(activity.nameContainsWhitespace(" ")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Gavin
    public void nameEmpty() {
        when(activity.isNameEmpty("")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void nameNull() {
        when(activity.isNameEmpty(null)).thenReturn(Boolean.TRUE);
    }

    @Test
    // Dylan
    public void nameHasWhiteSpace() {
        when(activity.nameContainsWhitespace("g g")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Nikkolas
    public void nameIsValid() {
        when(!activity.nameContainsWhitespace("dylan")).thenReturn(true);
    }

    @Test
    // Nikkolas
    public void nameHasWhiteSpaces() {
        when(activity.nameContainsWhitespace("Hi Hi ")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void nameHasCapitalLetter() {
        when(!activity.nameContainsWhitespace("Saahil") && !activity.isNameEmpty("Saahil")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Saahil
    public void nameNullChar() {
        when(activity.isNameEmpty("\0")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void nameHasNumber() {
        when(!activity.nameContainsWhitespace("pr1yansh") && !activity.isNameEmpty("pr1yansh")).thenReturn(Boolean.TRUE);
    }

    @Test
    // Priyansh
    public void nameIsNumber() {
        when(!activity.nameContainsWhitespace("1") && !activity.isNameEmpty("1")).thenReturn(Boolean.TRUE);
    }
}