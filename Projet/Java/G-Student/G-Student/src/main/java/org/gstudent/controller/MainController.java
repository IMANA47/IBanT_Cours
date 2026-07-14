package org.gstudent.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML private Tab tabEtudiants;
    @FXML private Tab tabMatieres;
    @FXML private Tab tabcomposers;
    @FXML private Tab tabStats;

    @FXML
    public void initialize() {
        // Charger les vues dans chaque onglet
        try {
            FXMLLoader loaderEtu = new FXMLLoader(getClass().getResource("/org/gstudent/views/etudiant-view.fxml"));
            tabEtudiants.setContent(loaderEtu.load());

            FXMLLoader loaderMat = new FXMLLoader(getClass().getResource("/org/gstudent/views/matiere-view.fxml"));
            tabMatieres.setContent(loaderMat.load());

            FXMLLoader loaderComp = new FXMLLoader(getClass().getResource("/org/gstudent/views/Composer-view.fxml"));
            tabcomposers.setContent(loaderComp.load());

            FXMLLoader loaderStats = new FXMLLoader(getClass().getResource("/org/gstudent/views/stats-view.fxml"));
            tabStats.setContent(loaderStats.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}