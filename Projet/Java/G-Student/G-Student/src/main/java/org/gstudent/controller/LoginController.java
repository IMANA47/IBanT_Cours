package org.gstudent.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.gstudent.entities.Utilisateur;
import org.gstudent.exception.DaoException;
import org.gstudent.service.UtilisateurService;

public class LoginController extends BaseController {

    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    public void onLogin() {
        String login = tfLogin.getText().trim();
        String password = pfPassword.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            afficherErreur("Erreur", "Veuillez saisir un login et un mot de passe.");
            return;
        }

        try {
            Utilisateur user = utilisateurService.authentifier(login, password);
            if (user != null) {
                ouvrirMainApp();
            } else {
                afficherErreur("Erreur", "Identifiants incorrects.");
            }
        } catch (DaoException e) {
            afficherErreur("Erreur", "Erreur de connexion : " + e.getMessage());
        }
    }

    private void ouvrirMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gstudent/views/main-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) tfLogin.getScene().getWindow();
            stage.setTitle("G-Student - Plateforme");
            stage.setScene(scene);
            stage.setMaximized(true);
        } catch (Exception e) {
            afficherErreur("Erreur", "Erreur lors du chargement de l'application : " + e.getMessage());
        }
    }

}