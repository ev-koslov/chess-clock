package ev.koslov.chess_clock.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by voron on 18.02.2017.
 */
public class SimpleChessTimer implements ChessTimer {
    private ScheduledExecutorService executorService;

    private ChessPlayer whitePlayer, blackPlayer;

    private TimerLogger timerLogger;

    private TimerWorker whitePlayerWorker, blackPlayerWorker;
    private TimerWorker runningPlayerTimer, nextTurnPlayerTimer;
    private Future runningWorkerFuture;

    private TimerState state;
    private Player turn;

    public SimpleChessTimer(ChessPlayer whitePlayer, ChessPlayer blackPlayer) {
        if (whitePlayer == null || blackPlayer == null){
            throw new NullPointerException("Player instances cannot be NULL.");
        }

        this.executorService = Executors.newSingleThreadScheduledExecutor();

        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        this.whitePlayerWorker = new TimerWorker(whitePlayer);
        this.blackPlayerWorker = new TimerWorker(blackPlayer);

        //setting next timer to run
        this.nextTurnPlayerTimer = whitePlayerWorker;

        this.timerLogger = new TimerLogger();

        this.state = TimerState.READY;
    }

    @Override
    public void start() {

        verifyState(TimerState.READY);

        state = TimerState.RUNNING;
        turn = Player.WHITE;

        whitePlayer.startTurn();

        runningWorkerFuture = executorService.scheduleAtFixedRate(whitePlayerWorker, 0, 50, TimeUnit.MILLISECONDS);

        runningPlayerTimer = whitePlayerWorker;
        nextTurnPlayerTimer = blackPlayerWorker;

        timerLogger.startGame(this);

    }

    @Override
    public void swapTurn() {

        verifyState(TimerState.RUNNING);

        runningWorkerFuture.cancel(false);
        runningWorkerFuture = executorService.scheduleAtFixedRate(nextTurnPlayerTimer, 0, 50, TimeUnit.MILLISECONDS);

        if (turn.equals(Player.WHITE)) {
            whitePlayer.endTurn();
            blackPlayer.startTurn();
            turn = Player.BLACK;
        } else {
            whitePlayer.startTurn();
            blackPlayer.endTurn();
            turn = Player.WHITE;
        }

        TimerWorker temp = runningPlayerTimer;
        runningPlayerTimer = nextTurnPlayerTimer;
        nextTurnPlayerTimer = temp;

        timerLogger.turn(this);
    }

    @Override
    public void pause() {
        verifyState(TimerState.RUNNING);

        runningWorkerFuture.cancel(false);

        state = TimerState.PAUSED;

        timerLogger.pauseGame();
    }

    @Override
    public void unpause() {
        verifyState(TimerState.PAUSED);

        runningWorkerFuture = executorService.scheduleAtFixedRate(runningPlayerTimer, 0, 50, TimeUnit.MILLISECONDS);

        state = TimerState.RUNNING;

        timerLogger.continueGame();
    }

    @Override
    public void stop() {
        if (runningWorkerFuture != null){
            runningWorkerFuture.cancel(false);
        }

        executorService.shutdownNow();

        state = TimerState.STOPPED;

        timerLogger.endGame(this);
    }

    @Override
    public void updateTimers(long wUpdateValue, long bUpdatedValue) {
        whitePlayer.setTime(wUpdateValue);
        blackPlayer.setTime(bUpdatedValue);

        timerLogger.changeGame(this, wUpdateValue, bUpdatedValue);
    }

    @Override
    public ChessPlayer getPlayer(Player player) {
        return player.equals(Player.WHITE) ? whitePlayer : blackPlayer;
    }

    @Override
    public TimerState getState() {
        return state;
    }

    @Override
    public Player getTurn() {
        return turn;
    }

    @Override
    public TimerLogger getTimerLogger() {
        return timerLogger;
    }





    private void verifyState(TimerState expectedState) {
        if (!state.equals(expectedState)) {
            throw new UnsupportedOperationException(String.format("Wrong timer state: %s. Expected: %s.", state, expectedState));
        }
    }

    private class TimerWorker implements Runnable {

        private ChessPlayer playerTimer;

        private TimerWorker(ChessPlayer playerTimer) {
            this.playerTimer = playerTimer;
        }

        @Override
        public void run() {
            try {
                playerTimer.changeTime(-50);

                if (playerTimer.getTime() <= 0) {
                    stop();
                    state = TimerState.EXPIRED;
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
