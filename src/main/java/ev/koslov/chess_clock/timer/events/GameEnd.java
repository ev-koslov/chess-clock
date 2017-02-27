package ev.koslov.chess_clock.timer.events;


import ev.koslov.chess_clock.timer.ChessPlayer;
import ev.koslov.chess_clock.timer.ChessTimer;
import ev.koslov.chess_clock.timer.Player;

public class GameEnd extends GameStart {
    public final int wTurns, bTurns;
    public final String winner;

    public GameEnd(ChessTimer timer) {
        super(timer);

        ChessPlayer wPlayer = timer.getPlayer(Player.WHITE);
        ChessPlayer bPlayer = timer.getPlayer(Player.BLACK);

        wTurns = wPlayer.getTurnsCount();
        bTurns = wPlayer.getTurnsCount();

        if (wPlayer.getTime() <= 0) {
            winner = bPlayer.getName();
        } else {
            if (bPlayer.getTime() <= 0) {

                winner = wPlayer.getName();

            } else {

                winner = "";

            }
        }

    }

    @Override
    public String toString() {
        return "Окончание игры";
    }
}
