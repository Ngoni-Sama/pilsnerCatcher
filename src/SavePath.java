package application;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SavePath extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        Button button = new Button("Choose");
        Label chosen = new Label();
        button.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                String fileAsString = file.toString();

                chosen.setText("Chosen: " + fileAsString);
            } else {
                chosen.setText(null);
            }
        });

        VBox layout = new VBox(10, button, chosen);
        layout.setMinWidth(400);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}