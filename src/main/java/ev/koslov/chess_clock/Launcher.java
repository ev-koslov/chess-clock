package ev.koslov.chess_clock;

import ev.koslov.chess_clock.controllers.ClockController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by voron on 16.02.2017.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXUtils.ViewControllerPair<ClockController> pair = FXUtils.loadViewAndController("/fxml/clock.fxml");

        stage.setScene(pair.scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
