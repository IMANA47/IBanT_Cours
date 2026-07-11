package org.filatb.vue;
import org.filatb.controleur.ControleurGuichet;
import org.filatb.modele.GestionnaireFileAttente;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialisation du modèle (capacité 10 par exemple)
        GestionnaireFileAttente gestionnaire = new GestionnaireFileAttente(10);
        VueGuichet vue = new VueGuichet();
        ControleurGuichet controleur = new ControleurGuichet(gestionnaire, vue);
        vue.getBtnHistorique().setOnAction(e -> controleur.afficherHistorique())

        Scene scene = new Scene(vue.getRoot(), 950, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Gestion de file d'attente bancaire");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Mise à jour initiale
        controleur.mettreAJourAffichage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}