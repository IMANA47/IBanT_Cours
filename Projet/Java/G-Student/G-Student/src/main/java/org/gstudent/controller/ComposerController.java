package org.gstudent.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.gstudent.entities.composer;
import org.gstudent.entities.Etudiant;
import org.gstudent.entities.Matiere;
import org.gstudent.exception.DaoException;
import org.gstudent.service.composerService;
import org.gstudent.service.EtudiantService;
import org.gstudent.service.MatiereService;

import java.util.List;

public class ComposerController {

    @FXML private TableView<composer> tablecomposers;
    @FXML private TableColumn<composer, Integer> colId;
    @FXML private TableColumn<composer, String> colEtudiant;
    @FXML private TableColumn<composer, String> colMatiere;
    @FXML private TableColumn<composer, Double> colNote;

    @FXML private ComboBox<Etudiant> cbEtudiant;
    @FXML private ComboBox<Matiere> cbMatiere;
    @FXML private TextField tfNote;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer, btnAnnuler;

    private composerService service = new composerService();
    private EtudiantService etudiantService = new EtudiantService();
    private MatiereService matiereService = new MatiereService();
    private ObservableList<composer> data = FXCollections.observableArrayList();
    private composer composerSelectionnee;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEtudiant.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEtudiant().getNom() + " " + cellData.getValue().getEtudiant().getPrenom()));
        colMatiere.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMatiere().getNom()));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        chargerComboBox();
        chargerDonnees();

        tablecomposers.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            composerSelectionnee = newVal;
            if (newVal != null) {
                cbEtudiant.setValue(newVal.getEtudiant());
                cbMatiere.setValue(newVal.getMatiere());
                tfNote.setText(String.valueOf(newVal.getNote()));
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

    private void chargerComboBox() {
        try {
            List<Etudiant> etudiants = etudiantService.listerTous();
            cbEtudiant.setItems(FXCollections.observableArrayList(etudiants));
            List<Matiere> matieres = matiereService.listerTous();
            cbMatiere.setItems(FXCollections.observableArrayList(matieres));
        } catch (DaoException e) {
            afficherErreur("Erreur", e.getMessage());
        }
    }

    private void chargerDonnees() {
        try {
            data.setAll(service.listerTous());
            tablecomposers.setItems(data);
        } catch (DaoException e) {
            afficherErreur("Erreur", e.getMessage());
        }
    }

    @FXML
    public void onAjouter() {
        Etudiant e = cbEtudiant.getValue();
        Matiere m = cbMatiere.getValue();
        String noteStr = tfNote.getText().trim();
        if (e == null || m == null || noteStr.isEmpty()) {
            afficherErreur("Champs vides", "Veuillez sélectionner un étudiant, une matière et saisir une note.");
            return;
        }
        double note;
        try { note = Double.parseDouble(noteStr); if (note<0||note>20) throw new NumberFormatException(); } catch (NumberFormatException ex) {
            afficherErreur("Erreur", "La note doit être un nombre entre 0 et 20.");
            return;
        }
        try {
            composer c = new composer(0, e, m, note);
            service.ajouter(c);
            chargerDonnees();
            viderChamps();
        } catch (DaoException ex) {
            afficherErreur("Erreur", ex.getMessage());
        }
    }

    @FXML
    public void onModifier() {
        if (composerSelectionnee == null) return;
        Etudiant e = cbEtudiant.getValue();
        Matiere m = cbMatiere.getValue();
        String noteStr = tfNote.getText().trim();
        if (e == null || m == null || noteStr.isEmpty()) {
            afficherErreur("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }
        double note;
        try { note = Double.parseDouble(noteStr); if (note<0||note>20) throw new NumberFormatException(); } catch (NumberFormatException ex) {
            afficherErreur("Erreur", "Note invalide.");
            return;
        }
        try {
            composerSelectionnee.setEtudiant(e);
            composerSelectionnee.setMatiere(m);
            composerSelectionnee.setNote(note);
            service.modifier(composerSelectionnee);
            chargerDonnees();
            viderChamps();
        } catch (DaoException ex) {
            afficherErreur("Erreur", ex.getMessage());
        }
    }

    @FXML
    public void onSupprimer() {
        if (composerSelectionnee == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer cette composer ?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                service.supprimer(composerSelectionnee.getId());
                chargerDonnees();
                viderChamps();
            } catch (DaoException ex) {
                afficherErreur("Erreur", ex.getMessage());
            }
        }
    }

    @FXML
    public void onAnnuler() {
        viderChamps();
        tablecomposers.getSelectionModel().clearSelection();
    }

    private void viderChamps() {
        cbEtudiant.getSelectionModel().clearSelection();
        cbMatiere.getSelectionModel().clearSelection();
        tfNote.clear();
        composerSelectionnee = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    private void afficherErreur(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}