package com.gestionetud.ui;

import com.gestionetud.entities.Etudiant;
import com.gestionetud.exception.DatabaseException;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.service.EtudiantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Contrôleur JavaFX pour la gestion de l'interface des étudiants.
 */
public class EtudiantController {

    @FXML
    private TableView<Etudiant> etudiantTableView;

    @FXML
    private TableColumn<Etudiant, Integer> idColumn;

    @FXML
    private TableColumn<Etudiant, String> nomColumn;

    @FXML
    private TableColumn<Etudiant, String> prenomColumn;

    @FXML
    private TableColumn<Etudiant, Integer> ageColumn;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField ageField;

    @FXML
    private Button registerButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    private final EtudiantService etudiantService = new EtudiantService();
    private final ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
    private Etudiant selectedEtudiant = null;

    @FXML
    public void initialize() {
        // Liaison des colonnes de la TableView avec les propriétés de l'entité Etudiant
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        // Listener pour détecter la sélection d'une ligne
        etudiantTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedEtudiant = newValue;
                nomField.setText(selectedEtudiant.getNom());
                prenomField.setText(selectedEtudiant.getPrenom());
                ageField.setText(String.valueOf(selectedEtudiant.getAge()));
            }
        });

        // Chargement initial des données
        loadEtudiants();
    }

    /**
     * Charge la liste des étudiants en arrière-plan à l'aide de javafx.concurrent.Task.
     */
    private void loadEtudiants() {
        Task<List<Etudiant>> task = new Task<>() {
            @Override
            protected List<Etudiant> call() throws Exception {
                return etudiantService.getAllEtudiants();
            }
        };

        task.setOnSucceeded(event -> {
            etudiantList.setAll(task.getValue());
            etudiantTableView.setItems(etudiantList);
        });

        task.setOnFailed(event -> {
            showErrorAlert("Erreur de chargement", "Impossible de récupérer les étudiants depuis la base de données.", task.getException());
        });

        new Thread(task).start();
    }

    /**
     * Action pour enregistrer un nouvel étudiant.
     */
    @FXML
    private void handleRegister() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        int age;

        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Erreur de saisie", "L'âge doit être un nombre entier valide.", e);
            return;
        }

        Etudiant newEtudiant = new Etudiant(0, nom, prenom, age);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                etudiantService.registerEtudiant(newEtudiant);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            loadEtudiants();
            clearFields();
            showInfoAlert("Succès", "Étudiant enregistré avec succès.");
        });

        task.setOnFailed(event -> {
            showErrorAlert("Erreur d'enregistrement", "L'enregistrement de l'étudiant a échoué.", task.getException());
        });

        new Thread(task).start();
    }

    /**
     * Action pour mettre à jour l'étudiant sélectionné.
     */
    @FXML
    private void handleUpdate() {
        if (selectedEtudiant == null) {
            showInfoAlert("Sélection requise", "Veuillez sélectionner un étudiant dans le tableau.");
            return;
        }

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        int age;

        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Erreur de saisie", "L'âge doit être un nombre entier valide.", e);
            return;
        }

        selectedEtudiant.setNom(nom);
        selectedEtudiant.setPrenom(prenom);
        selectedEtudiant.setAge(age);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                etudiantService.updateEtudiant(selectedEtudiant);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            loadEtudiants();
            clearFields();
            showInfoAlert("Succès", "Étudiant mis à jour avec succès.");
        });

        task.setOnFailed(event -> {
            showErrorAlert("Erreur de mise à jour", "La mise à jour de l'étudiant a échoué.", task.getException());
        });

        new Thread(task).start();
    }

    /**
     * Action pour supprimer l'étudiant sélectionné.
     */
    @FXML
    private void handleRemove() {
        if (selectedEtudiant == null) {
            showInfoAlert("Sélection requise", "Veuillez sélectionner un étudiant dans le tableau.");
            return;
        }

        int id = selectedEtudiant.getIdEtudiant();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                etudiantService.removeEtudiant(id);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            loadEtudiants();
            clearFields();
            showInfoAlert("Succès", "Étudiant supprimé avec succès.");
        });

        task.setOnFailed(event -> {
            showErrorAlert("Erreur de suppression", "La suppression de l'étudiant a échoué.", task.getException());
        });

        new Thread(task).start();
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        ageField.clear();
        selectedEtudiant = null;
        etudiantTableView.getSelectionModel().clearSelection();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String context, Throwable exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(context);

        // Détection des exceptions personnalisées de notre couche métier
        if (exception instanceof ValidationException) {
            alert.setContentText("Règle métier violée : " + exception.getMessage());
        } else if (exception instanceof DatabaseException) {
            alert.setContentText("Erreur base de données : " + exception.getMessage() + "\n(Détail : " + exception.getCause().getMessage() + ")");
        } else {
            alert.setContentText("Détail de l'erreur : " + exception.getMessage());
        }

        alert.showAndWait();
    }
}
