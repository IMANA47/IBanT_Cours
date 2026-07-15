package org.gstudent.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gstudent.entities.Etudiant;
import org.gstudent.exception.DaoException;
import org.gstudent.service.EtudiantService;
import org.gstudent.util.ValidationUtils;

import java.sql.SQLException;

public class EtudiantController extends BaseController {

    @FXML private TableView<Etudiant> tableEtudiants;
    @FXML private TableColumn<Etudiant, Integer> colId;
    @FXML private TableColumn<Etudiant, String> colNom;
    @FXML private TableColumn<Etudiant, String> colPrenom;
    @FXML private TableColumn<Etudiant, String> colEmail;

    @FXML private TextField tfNom, tfPrenom, tfEmail;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer, btnAnnuler;

    private EtudiantService service = new EtudiantService();
    private ObservableList<Etudiant> data = FXCollections.observableArrayList();
    private Etudiant etudiantSelectionne;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        chargerDonnees();

        tableEtudiants.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            etudiantSelectionne = newVal;
            if (newVal != null) {
                tfNom.setText(newVal.getNom());
                tfPrenom.setText(newVal.getPrenom());
                tfEmail.setText(newVal.getEmail());
                btnModifier.setDisable(false);
                btnSupprimer.setDisable(false);
            } else {
                btnModifier.setDisable(true);
                btnSupprimer.setDisable(true);
            }
        });

        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        btnAnnuler.setDisable(true);
    }

    private void chargerDonnees() {
        try {
            data.setAll(service.listerTous());
            tableEtudiants.setItems(data);
        } catch (DaoException e) {
            afficherErreur("Erreur chargement", e.getMessage());
        }
    }

    @FXML
    public void onAjouter() {
        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        String email = tfEmail.getText().trim();
        
        if (!ValidationUtils.isNotEmpty(nom) || !ValidationUtils.isNotEmpty(prenom) || !ValidationUtils.isNotEmpty(email)) {
            afficherErreur("Champs vides", "Tous les champs sont obligatoires.");
            return;
        }
        
        if (!ValidationUtils.isValidName(nom)) {
            afficherErreur("Nom invalide", "Le nom doit contenir entre 2 et 50 caractères alphabétiques.");
            return;
        }
        
        if (!ValidationUtils.isValidName(prenom)) {
            afficherErreur("Prénom invalide", "Le prénom doit contenir entre 2 et 50 caractères alphabétiques.");
            return;
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            afficherErreur("Email invalide", "Veuillez saisir une adresse email valide.");
            return;
        }
        
        try {
            Etudiant e = new Etudiant(0, nom, prenom, email);
            service.ajouter(e);
            chargerDonnees();
            viderChamps();
            afficherInformation("Succès", "Étudiant ajouté avec succès.");
        } catch (DaoException ex) {
            afficherErreur("Erreur", ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onModifier() {
        if (etudiantSelectionne == null) return;
        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        String email = tfEmail.getText().trim();
        
        if (!ValidationUtils.isNotEmpty(nom) || !ValidationUtils.isNotEmpty(prenom) || !ValidationUtils.isNotEmpty(email)) {
            afficherErreur("Champs vides", "Tous les champs sont obligatoires.");
            return;
        }
        
        if (!ValidationUtils.isValidName(nom)) {
            afficherErreur("Nom invalide", "Le nom doit contenir entre 2 et 50 caractères alphabétiques.");
            return;
        }
        
        if (!ValidationUtils.isValidName(prenom)) {
            afficherErreur("Prénom invalide", "Le prénom doit contenir entre 2 et 50 caractères alphabétiques.");
            return;
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            afficherErreur("Email invalide", "Veuillez saisir une adresse email valide.");
            return;
        }
        
        try {
            etudiantSelectionne.setNom(nom);
            etudiantSelectionne.setPrenom(prenom);
            etudiantSelectionne.setEmail(email);
            service.modifier(etudiantSelectionne);
            chargerDonnees();
            viderChamps();
            afficherInformation("Succès", "Étudiant modifié avec succès.");
        } catch (DaoException ex) {
            afficherErreur("Erreur", ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onSupprimer() {
        if (etudiantSelectionne == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer " + etudiantSelectionne.getPrenom() + " " + etudiantSelectionne.getNom() + " ?",
                ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                service.supprimer(etudiantSelectionne.getId());
                chargerDonnees();
                viderChamps();
            } catch (DaoException ex) {
                afficherErreur("Erreur", ex.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void onAnnuler() {
        viderChamps();
        tableEtudiants.getSelectionModel().clearSelection();
    }

    private void viderChamps() {
        tfNom.clear();
        tfPrenom.clear();
        tfEmail.clear();
        etudiantSelectionne = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

}