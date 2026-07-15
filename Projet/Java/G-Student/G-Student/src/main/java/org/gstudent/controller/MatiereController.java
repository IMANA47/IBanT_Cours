package org.gstudent.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gstudent.entities.Matiere;
import org.gstudent.exception.DaoException;
import org.gstudent.service.MatiereService;
import org.gstudent.util.ValidationUtils;

import java.sql.SQLException;

public class MatiereController extends BaseController {

    @FXML private TableView<Matiere> tableMatieres;
    @FXML private TableColumn<Matiere, Integer> colId;
    @FXML private TableColumn<Matiere, String> colNom;
    @FXML private TableColumn<Matiere, Double> colCoeff;

    @FXML private TextField tfNom, tfCoeff;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer, btnAnnuler;

    private MatiereService service = new MatiereService();
    private ObservableList<Matiere> data = FXCollections.observableArrayList();
    private Matiere matiereSelectionnee;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCoeff.setCellValueFactory(new PropertyValueFactory<>("coefficient"));

        chargerDonnees();

        tableMatieres.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            matiereSelectionnee = newVal;
            if (newVal != null) {
                tfNom.setText(newVal.getNom());
                tfCoeff.setText(String.valueOf(newVal.getCoefficient()));
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
            tableMatieres.setItems(data);
        } catch (DaoException e) {
            afficherErreur("Erreur", e.getMessage());
        }
    }

    @FXML
    public void onAjouter() {
        String nom = tfNom.getText().trim();
        String coeffStr = tfCoeff.getText().trim();
        
        if (!ValidationUtils.isNotEmpty(nom) || !ValidationUtils.isNotEmpty(coeffStr)) {
            afficherErreur("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }
        
        if (!ValidationUtils.isValidName(nom)) {
            afficherErreur("Nom invalide", "Le nom doit contenir entre 2 et 50 caractères alphabétiques.");
            return;
        }
        
        double coeff;
        try { 
            coeff = Double.parseDouble(coeffStr); 
            if (!ValidationUtils.isValidCoefficient(coeff)) {
                afficherErreur("Coefficient invalide", "Le coefficient doit être entre 0 et 20.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Coefficient invalide.");
            return;
        }
        
        try {
            Matiere m = new Matiere(0, nom, coeff);
            service.ajouter(m);
            chargerDonnees();
            viderChamps();
            afficherInformation("Succès", "Matière ajoutée avec succès.");
        } catch (DaoException ex) {
            afficherErreur("Erreur", ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onModifier() {
        if (matiereSelectionnee == null) return;
        String nom = tfNom.getText().trim();
        String coeffStr = tfCoeff.getText().trim();
        
        if (!ValidationUtils.isNotEmpty(nom) || !ValidationUtils.isNotEmpty(coeffStr)) {
            afficherErreur("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }
        
        if (!ValidationUtils.isValidName(nom)) {
            afficherErreur("Nom invalide", "Le nom doit contenir entre 2 et 50 caractères alphabétiques.");
            return;
        }
        
        double coeff;
        try { 
            coeff = Double.parseDouble(coeffStr); 
            if (!ValidationUtils.isValidCoefficient(coeff)) {
                afficherErreur("Coefficient invalide", "Le coefficient doit être entre 0 et 20.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Coefficient invalide.");
            return;
        }
        
        try {
            matiereSelectionnee.setNom(nom);
            matiereSelectionnee.setCoefficient(coeff);
            service.modifier(matiereSelectionnee);
            chargerDonnees();
            viderChamps();
            afficherInformation("Succès", "Matière modifiée avec succès.");
        } catch (DaoException ex) {
            afficherErreur("Erreur", ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onSupprimer() {
        if (matiereSelectionnee == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer la matière " + matiereSelectionnee.getNom() + " ?",
                ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                service.supprimer(matiereSelectionnee.getId());
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
        tableMatieres.getSelectionModel().clearSelection();
    }

    private void viderChamps() {
        tfNom.clear();
        tfCoeff.clear();
        matiereSelectionnee = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

}