package ev.koslov.chess_clock.controllers;

import ev.koslov.chess_clock.FXUtils;
import ev.koslov.chess_clock.timer.ChessTimer;
import ev.koslov.chess_clock.timer.Player;
import ev.koslov.chess_clock.timer.TimerState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class ClockController extends AbstractController {

    @FXML
    private Button whiteSwapTurn, blackSwapTurn, history, reset, pause, settings;

    @FXML
    private Label whiteName, whiteTime, whiteTurns, blackName, blackTime, blackTurns;

    private ChessTimer chessTimer;

    private Stage modalWindowStage;
    private FXUtils.ViewControllerPair<SettingsController> settingsInterface;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //инициализируем modalWindowStage. Делаем его подчиненным окну часов.
        modalWindowStage = new Stage();
        modalWindowStage.initModality(Modality.WINDOW_MODAL);
        modalWindowStage.setResizable(false);

        //зазружаем интерфейс настроек и истории и инициализируем связи между обьектами

        try {
            settingsInterface = FXUtils.loadViewAndController("/fxml/settings.fxml");
            //            historyInterface = FXUtils.loadViewAndController("/fxml/history.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }


//        guiUpdater = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                updatePlayersInfo();
//
//                if (chessTimer.getState().equals(TimerState.EXPIRED)) {
//                    redrawInterface();
//                }
//            }
//        }));

//        guiUpdater.setCycleCount(Timeline.INDEFINITE);
    }


    //инициализация таймера
    public void init(ChessTimer timer) {
        this.chessTimer = timer;

        blackSwapTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startTimer();
            }
        });

        blackSwapTurn.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                resetTimer();
            }
        });

        redrawInterface();
    }

    private void startTimer() {

    }

    @FXML
    public void swapTurn() {

    }

    @FXML
    protected void pauseTimer() {

    }

    public void unpause() {

    }

    @FXML
    protected void resetTimer() {

    }

    @FXML
    protected void openSettings() throws IOException {

    }

    @FXML
    protected void openHistory() throws IOException {

    }

    @Override
    protected void redrawInterface() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (chessTimer != null) {
                    switch (chessTimer.getState()) {
                        case READY: {
                            updateButtonLabels("Ожидание...", "Начать", true, false);
                            break;
                        }
                        case RUNNING: {
                            String label1 = "Передать ход";
                            String label2 = "Ожидание...";
                            if (chessTimer.getTurn() == Player.WHITE) {
                                updateButtonLabels(label1, label2, false, true);
                            } else {
                                updateButtonLabels(label2, label1, true, false);
                            }
                            break;
                        }
                        case PAUSED: {
                            String label1 = "Ожидание...";
                            String label2 = "Продолжить";

                            if (chessTimer.getTurn() == Player.WHITE) {
                                updateButtonLabels(label1, label2, true, false);
                            } else {
                                updateButtonLabels(label2, label1, false, true);
                            }
                            break;
                        }
                        case EXPIRED:
                        case STOPPED:{
                            updateButtonLabels("", "", true, true);
                            break;
                        }
                    }


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

                    whiteTurns.setText(String.valueOf(chessTimer.getPlayer(Player.WHITE).getTurnsCount()));
                    blackTurns.setText(String.valueOf(chessTimer.getPlayer(Player.BLACK).getTurnsCount()));


                } else {

                    whiteName.setText("");
                    blackName.setText("");

                    whiteTime.setText("");
                    whiteTurns.setText("");

                    blackTime.setText("");
                    blackTurns.setText("");

                }
            }
        });

    }
//
//    private void updatePlayersInfo() {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
////                if (chessTimer != null) {
////                    long wTime = chessTimer.getPlayer(Player.WHITE).getTime();
////                    long bTime = chessTimer.getPlayer(Player.BLACK).getTime();
////
////                    whiteTime.setText(String.format(
////                            "%02d:%02d:%02d",
////                            TimeUnit.HOURS.convert(wTime, TimeUnit.MILLISECONDS) % 24,
////                            TimeUnit.MINUTES.convert(wTime, TimeUnit.MILLISECONDS) % 60,
////                            TimeUnit.SECONDS.convert(wTime, TimeUnit.MILLISECONDS) % 60
////                    ));
////
////                    blackTime.setText(String.format(
////                            "%02d:%02d:%02d",
////                            TimeUnit.HOURS.convert(bTime, TimeUnit.MILLISECONDS) % 24,
////                            TimeUnit.MINUTES.convert(bTime, TimeUnit.MILLISECONDS) % 60,
////                            TimeUnit.SECONDS.convert(bTime, TimeUnit.MILLISECONDS) % 60
////                    ));
////
////                    whiteTurns.setText(String.valueOf(chessTimer.getPlayer(Player.WHITE).getTurnsCount()));
////                    blackTurns.setText(String.valueOf(chessTimer.getPlayer(Player.BLACK).getTurnsCount()));
////                } else {
////                    whiteName.setText("");
////                    blackName.setText("");
////
////                    whiteTime.setText("");
////                    whiteTurns.setText("");
////
////                    blackTime.setText("");
////                    blackTurns.setText("");
////                }
//            }
//        });
//
//    }

    private void updateButtonLabels(final String whiteSwapTurnLabel, final String blackSwapTurnLabel, final boolean disableWButton, final boolean disableBButton) {
        whiteSwapTurn.setText(whiteSwapTurnLabel);
        blackSwapTurn.setText(blackSwapTurnLabel);

        whiteSwapTurn.setDisable(disableWButton);
        blackSwapTurn.setDisable(disableBButton);
    }
}
