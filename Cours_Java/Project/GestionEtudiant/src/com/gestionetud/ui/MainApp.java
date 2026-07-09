package com.gestionetud.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

/**
 * Point d'entrée de l'application JavaFX de gestion des étudiants.
 * <p>
 * Charge le fichier {@code EtudiantView.fxml} du package
 * {@code com.gestionetud.ui} et affiche la fenêtre principale.
 * </p>
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("EtudiantView.fxml");
        if (fxmlUrl == null) {
            throw new IllegalStateException("Fichier FXML introuvable : EtudiantView.fxml");
        }

        Parent root = FXMLLoader.load(fxmlUrl);

        Scene scene = new Scene(root, 900, 650);

        primaryStage.setTitle("🎓 GestionEtudiant");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(780);
        primaryStage.setMinHeight(560);
        primaryStage.show();
    }

    /** Lancement de l'application JavaFX. */
    public static void main(String[] args) {
        launch(args);
    }
}
