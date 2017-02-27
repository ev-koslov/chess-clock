package ev.koslov.chess_clock.timer;


import ev.koslov.chess_clock.timer.events.*;

import java.util.LinkedList;
import java.util.List;

public class TimerLogger {
    private LinkedList<GameEvent> gameEvents;

    public TimerLogger() {
        this.gameEvents = new LinkedList<>();
    }

    public List<GameEvent> getLoggedEvents() {
        return gameEvents;
    }

    public void startGame(ChessTimer timer) {
        gameEvents.addLast(new GameStart(timer));
    }

    public void pauseGame() {
        gameEvents.addLast(new GamePause());
    }

    public void continueGame() {
        gameEvents.addLast(new GameContinue());
    }

    public void turn(ChessTimer timer) {
        gameEvents.addLast(new GameTurn(timer));
    }

    public void changeGame(ChessTimer timer, long wNewTime, long bNewTime) {
        gameEvents.addLast(new GameParametersChange(timer, wNewTime, bNewTime));
    }

    public void endGame(ChessTimer timer) {
        gameEvents.addLast(new GameEnd(timer));
    }
//    public void resetGame();
}
