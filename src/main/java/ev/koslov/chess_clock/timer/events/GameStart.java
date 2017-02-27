package ev.koslov.chess_clock.timer.events;

import ev.koslov.chess_clock.timer.ChessPlayer;
import ev.koslov.chess_clock.timer.ChessTimer;
import ev.koslov.chess_clock.timer.Player;

public class GameStart extends GameEvent {
    public final long wEstTime, bEstTime;
    public final String wName, bName;

    public GameStart(ChessTimer timer) {
        ChessPlayer wPlayer = timer.getPlayer(Player.WHITE);
        ChessPlayer bPlayer = timer.getPlayer(Player.BLACK);

        wEstTime = wPlayer.getTime();
        wName = wPlayer.getName();

        bEstTime = bPlayer.getTime();
        bName = bPlayer.getName();

    }

    @Override
    public String toString() {
        return "Начало игры";
    }
}
