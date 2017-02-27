package ev.koslov.chess_clock.timer.events;

import ev.koslov.chess_clock.timer.ChessTimer;

public class GameParametersChange extends GameStart {
    public final long wChangeTimeTime, bChangeTimeTime;

    public GameParametersChange(ChessTimer timer, long wChangeTimeTime, long bChangeTimeTime) {
        super(timer);
        this.wChangeTimeTime = wChangeTimeTime;
        this.bChangeTimeTime = bChangeTimeTime;
    }

    @Override
    public String toString() {
        return "Изменение параметров";
    }
}
