package ev.koslov.chess_clock.controllers;

import ev.koslov.chess_clock.timer.ChessPlayer;
import ev.koslov.chess_clock.timer.ChessTimer;
import ev.koslov.chess_clock.timer.SimpleChessTimer;
import ev.koslov.chess_clock.timer.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class SettingsController extends AbstractController {
    private ClockController clockController;

    @FXML
    private TextField whiteTime, whiteName, blackTime, blackName;

    private ChessTimer chessTimer;
    private boolean editTimer;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init(ClockController clockController) {
        this.clockController = clockController;
    }

    public void editExistingTimer(ChessTimer chessTimer) {
        this.editTimer = true;
        this.chessTimer = chessTimer;

        redrawInterface();
    }

    public void createNewTimer() {
        this.editTimer = false;
        this.chessTimer = null;
    }

    @FXML
    private void confirm() {

        long whiteTimeLong = parseTime(whiteTime.getText());
        long blackTimeLong = parseTime(blackTime.getText());

        if (editTimer) {
            chessTimer.getPlayer(Player.WHITE).setTime(whiteTimeLong);
            chessTimer.getPlayer(Player.BLACK).setTime(blackTimeLong);
        } else {
            chessTimer = new SimpleChessTimer(
                    new ChessPlayer(whiteName.getText(), whiteTimeLong),
                    new ChessPlayer(blackName.getText(), blackTimeLong)
            );

            clockController.init(chessTimer);
        }

        ((Stage) whiteName.getScene().getWindow()).close();
    }

    @FXML
    private void reset() {
        createNewTimer();
    }

    @FXML
    private void cancel() {
        ((Stage) whiteName.getScene().getWindow()).close();
    }


    private long parseTime(String value) {
        String[] data = value.split(":");
        long time = 0;
        time += Long.parseLong(data[0]) * 3600;
        time += Long.parseLong(data[1]) * 60;
        time += Long.parseLong(data[2]);
        time *= 1000;
        return time;
    }

    @Override
    protected void redrawInterface() {
        if (editTimer) {

            long wTime = chessTimer.getPlayer(Player.WHITE).getTime();
            long bTime = chessTimer.getPlayer(Player.BLACK).getTime();

            whiteTime.setText(String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.HOURS.convert(wTime, TimeUnit.MILLISECONDS) % 24,
                    TimeUnit.MINUTES.convert(wTime, TimeUnit.MILLISECONDS) % 60,
                    TimeUnit.SECONDS.convert(wTime, TimeUnit.MILLISECONDS) % 60
            ));

            blackTime.setText(String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.HOURS.convert(bTime, TimeUnit.MILLISECONDS) % 24,
                    TimeUnit.MINUTES.convert(bTime, TimeUnit.MILLISECONDS) % 60,
                    TimeUnit.SECONDS.convert(bTime, TimeUnit.MILLISECONDS) % 60
            ));

            whiteName.setText(chessTimer.getPlayer(Player.WHITE).getName());
            blackName.setText(chessTimer.getPlayer(Player.BLACK).getName());

            whiteName.setDisable(true);
            blackName.setDisable(true);

        } else {
            //если создается новая игра, просто разблокируем поля ввода имен. В них будут введенные ранее значения
            whiteName.setDisable(false);
            blackName.setDisable(false);
        }
    }
}
