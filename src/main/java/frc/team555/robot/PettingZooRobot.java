package frc.team555.robot;

import java.util.List;
import java.util.ArrayList;

public class PettingZooRobot {
    public enum Move {
        FORWARD, BACK, LEFT, RIGHT, STOP
    }

    public List<Move> moves;

    public PettingZooRobot() {
        this.moves = new ArrayList<Move>();
    }

    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Robot moves forward 1 unit.
     */
    public void forward() {
        forward(1);
    }

    /**
     * Robot moves forward many units.
     * @param n # of units to move.
     */
    public void forward(int n) {
        for (int i = 0; i < n; i++)
            moves.add(Move.FORWARD);
    }

    /**
     * Robot moves backward 1 unit.
     */
    public void back() {
        back(1);
    }

    /**
     * Robot moves backward many units.
     * @param n # of units to move.
     */
    public void back(int n) {
        for (int i = 0; i < n; i++)
            moves.add(Move.BACK);
    }

    /**
     * Robot turns left 90 degrees.
     */
    public void left() {
        left(1);
    }

    /**
     * Robot turns left 90 degrees many times.
     * @param n # of times to turn.
     */
    public void left(int n) {
        for (int i = 0; i < n; i++)
            moves.add(Move.LEFT);
    }

    /**
     * Robot turns right 90 degrees.
     */
    public void right() {
        right(1);
    }

    /**
     * Robot turns right 90 degrees many times.
     * @param n # of times to turn.
     */
    public void right(int n) {
        for (int i = 0; i < n; i++)
            moves.add(Move.RIGHT);
    }

    /**
     * Robot stops in its tracks for 1 second.
     */
    public void stop() {
        stop(1);
    }

    /**
     * Robot stops in its tracks for many seconds.
     * @param n # of seconds to wait.
     */
    public void stop(int n) {
        for (int i = 0; i < n; i++)
            moves.add(Move.STOP);
    }
}
