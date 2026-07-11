package org.filatb.vue;

import org.filatb.controleur.ControleurGuichet;
import org.filatb.modele.GestionnaireFileAttente;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        GestionnaireFileAttente gestionnaire = new GestionnaireFileAttente(10);
        VueGuichet vue = new VueGuichet();
        ControleurGuichet controleur = new ControleurGuichet(gestionnaire, vue);
        vue.setControleur(controleur);  // <-- important pour les boutons dans le tableau

        // Lier les boutons
        vue.getBtnHistorique().setOnAction(e -> controleur.afficherHistorique());
        vue.getBtnReinitialiser().setOnAction(e -> {
            gestionnaire.viderFile();
            controleur.mettreAJourAffichage();
        });
        vue.getBtnFinirTraitement().setOnAction(e -> {
            // Par exemple, effacer l'affichage du client en cours
            vue.getLblClientEnCoursNumero().setText("Aucun");
            vue.getLblClientEnCoursNom().setText("");
            vue.getLblClientEnCoursMotif().setText("");
            vue.getLblClientEnCoursHeure().setText("");
        });

        Scene scene = new Scene(vue.getRoot(), 960, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Gestion de file d'attente bancaire");
        primaryStage.setScene(scene);
        primaryStage.show();

        controleur.mettreAJourAffichage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}