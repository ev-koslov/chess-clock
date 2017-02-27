package ev.koslov.chess_clock;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class FXUtils {

    public static ViewControllerPair loadViewAndController(String pathToFxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewControllerPair.class.getResource(pathToFxmlFile));
        Parent view = (Parent)fxmlLoader.load();
        Object controller = fxmlLoader.getController();
        return new ViewControllerPair(view, controller);
    }

    public static class ViewControllerPair<T> {
        public final Scene scene;
        public final T controller;

        private ViewControllerPair(Parent root, T controller) {
            this.scene = new Scene(root);
            this.controller = controller;
        }
    }
}
