package org.gstudent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gstudent.config.ConnexionBD;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Démarrer le serveur web H2 pour accéder à la console
        ConnexionBD.startWebServer();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gstudent/views/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("G-Student - Connexion");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ConnexionBD.closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}