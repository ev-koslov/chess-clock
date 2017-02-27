package ev.koslov.chess_clock.timer;

public interface ChessTimer {

    void start();
    void swapTurn();
    void pause();
    void unpause();
    void stop();

    void updateTimers(long wUpdateValue, long bUpdatedValue);

    ChessPlayer getPlayer(Player player);
    TimerState getState();
    Player getTurn();
    TimerLogger getTimerLogger();
}
