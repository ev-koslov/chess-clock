package ev.koslov.chess_clock.timer.events;

public abstract class GameEvent {

    public final long eventTime = System.currentTimeMillis();

    protected GameEvent() {
    }

    @Override
    public abstract String toString();
}
