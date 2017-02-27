package ev.koslov.chess_clock.timer.events;

import ev.koslov.chess_clock.timer.ChessPlayer;
import ev.koslov.chess_clock.timer.ChessTimer;
import ev.koslov.chess_clock.timer.Player;

public class GameTurn extends GameEvent {

    public final String playerName;
    public final long estimateTime, lastTurnTime;
    public final int turnsCount;

    public GameTurn(ChessTimer timer) {
        ChessPlayer player = (timer.getTurn() == Player.WHITE) ? (timer.getPlayer(Player.BLACK)) : (timer.getPlayer(Player.WHITE));

        playerName = player.getName();
        estimateTime = player.getTime();
        lastTurnTime = player.getLastTurnTime();
        turnsCount = player.getTurnsCount();

    }

    @Override
    public String toString() {
        return "Ход " + playerName;
    }
}
