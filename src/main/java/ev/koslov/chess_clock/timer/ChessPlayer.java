package ev.koslov.chess_clock.timer;

import java.util.LinkedList;


public class ChessPlayer {
    private long time, lastTurnTime;
    private int turns;
    private LinkedList<Long> turnsTimeList;
    private String name;
    private boolean turn;

    public ChessPlayer(String name, long time) {
        this.name = name;
        this.time = time;
        this.turnsTimeList = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void changeTime(long delta) {
        if (delta < 0) {
            lastTurnTime += delta;
        }
        time += delta;
    }

    public long getTime() {
        return time;
    }

    public int getTurnsCount() {
        return turns;
    }

    public void startTurn() {
        if (!turn) {
            lastTurnTime = 0;
            turn = true;
        } else {
            throw new UnsupportedOperationException("Turn is already started");
        }
    }

    public void endTurn() {
        if (turn) {
            turnsTimeList.addLast(lastTurnTime);
            turn = false;
            turns++;
        } else {
            throw new UnsupportedOperationException("Turn is not started.");
        }
    }

    public long getLastTurnTime() {
        return turnsTimeList.size() > 0 ? turnsTimeList.getLast() : 0;
    }
}
