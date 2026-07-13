package org.gstudent.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.gstudent.entities.Utilisateur;
import org.gstudent.exception.DaoException;
import org.gstudent.service.UtilisateurService;

public class LoginController {

    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    public void onLogin() {
        String login = tfLogin.getText().trim();
        String password = pfPassword.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            afficherAlerte("Veuillez saisir un login et un mot de passe.");
            return;
        }

        try {
            Utilisateur user = utilisateurService.authentifier(login, password);
            if (user != null) {
                ouvrirMainApp();
            } else {
                afficherAlerte("Identifiants incorrects.");
            }
        } catch (DaoException e) {
            afficherAlerte("Erreur de connexion : " + e.getMessage());
        }
    }

    private void ouvrirMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gstudent/views/main-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) tfLogin.getScene().getWindow();
            stage.setTitle("Gestion des étudiants - Plateforme");
            stage.setScene(scene);
            stage.setMaximized(true);
        } catch (Exception e) {
            afficherAlerte("Erreur lors du chargement de l'application : " + e.getMessage());
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}