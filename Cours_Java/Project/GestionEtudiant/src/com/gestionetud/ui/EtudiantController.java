package com.gestionetud.ui;

import com.gestionetud.entities.Etudiant;
import com.gestionetud.exception.DatabaseException;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.service.EtudiantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contrôleur JavaFX pour la vue de gestion des étudiants.
 * <p>
 * Utilise exactement :
 * <ul>
 *   <li>{@link EtudiantService#getAllEtudiants()} pour charger la liste</li>
 *   <li>{@link EtudiantService#registerEtudiant(Etudiant)} pour créer</li>
 *   <li>{@link EtudiantService#updateEtudiant(Etudiant)} pour modifier</li>
 *   <li>{@link EtudiantService#removeEtudiant(int)} pour supprimer</li>
 * </ul>
 * Les exceptions {@link ValidationException} et {@link DatabaseException}
 * sont propagées et affichées dans des {@link Alert}.
 * </p>
 */
public class EtudiantController implements Initializable {

    // ================================================================
    // Injection FXML — noms calqués sur les attributs de l'entité
    // ================================================================

    /** Champ en lecture seule pour l'idEtudiant généré par la BD */
    @FXML private TextField idEtudiantField;

    /** Champ lié à l'attribut {@code nom} de l'entité {@link Etudiant} */
    @FXML private TextField nomField;

    /** Champ lié à l'attribut {@code prenom} de l'entité {@link Etudiant} */
    @FXML private TextField prenomField;

    /** Champ lié à l'attribut {@code age} de l'entité {@link Etudiant} */
    @FXML private TextField ageField;

    // Tableau
    @FXML private TableView<Etudiant>       etudiantTable;
    @FXML private TableColumn<Etudiant, Integer> colIdEtudiant;
    @FXML private TableColumn<Etudiant, String>  colNom;
    @FXML private TableColumn<Etudiant, String>  colPrenom;
    @FXML private TableColumn<Etudiant, Integer> colAge;

    // Boutons
    @FXML private Button btnNouveau;
    @FXML private Button btnEnregistrer;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnRafraichir;

    // Indicateurs
    @FXML private Label              statusLabel;
    @FXML private Label              countLabel;
    @FXML private Label              footerLabel;
    @FXML private ProgressIndicator  progressIndicator;

    // ================================================================
    // État interne
    // ================================================================

    private final EtudiantService etudiantService = new EtudiantService();
    private final ObservableList<Etudiant> etudiantData = FXCollections.observableArrayList();

    /** Étudiant actuellement sélectionné dans la TableView (null si aucun) */
    private Etudiant etudiantSelectionne = null;

    // ================================================================
    // Initialisation
    // ================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurerColonnes();
        etudiantTable.setItems(etudiantData);
        chargerEtudiants();
    }

    /**
     * Configure les colonnes de la TableView en utilisant
     * les noms exacts des getters de l'entité {@link Etudiant}.
     */
    private void configurerColonnes() {
        colIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        // Centrer la colonne ID et Âge
        colIdEtudiant.setStyle("-fx-alignment: CENTER;");
        colAge.setStyle("-fx-alignment: CENTER;");
    }

    // ================================================================
    // Chargement des données — Task javafx.concurrent
    // ================================================================

    /**
     * Charge tous les étudiants depuis {@link EtudiantService#getAllEtudiants()}
     * dans un thread dédié pour ne pas bloquer le thread JavaFX.
     */
    private void chargerEtudiants() {
        setChargementEnCours(true);
        setStatut("Chargement des étudiants...");

        Task<List<Etudiant>> tache = new Task<>() {
            @Override
            protected List<Etudiant> call() {
                // Appel exact de votre méthode Service
                return etudiantService.getAllEtudiants();
            }
        };

        tache.setOnSucceeded(event -> {
            etudiantData.setAll(tache.getValue());
            setChargementEnCours(false);
            setStatut("Liste chargée avec succès.");
            countLabel.setText(etudiantData.size() + " étudiant(s)");
        });

        tache.setOnFailed(event -> {
            setChargementEnCours(false);
            Throwable cause = tache.getException();
            if (cause instanceof DatabaseException) {
                afficherAlerteErreur("Erreur base de données", cause.getMessage());
            } else {
                afficherAlerteErreur("Erreur inattendue", cause != null ? cause.getMessage() : "Inconnue");
            }
            setStatut("Échec du chargement.");
        });

        Thread thread = new Thread(tache);
        thread.setDaemon(true);
        thread.start();
    }

    // ================================================================
    // Actions des boutons (handlers FXML)
    // ================================================================

    /** Vide le formulaire et désélectionne la table. */
    @FXML
    private void handleNouveau() {
        viderFormulaire();
        etudiantTable.getSelectionModel().clearSelection();
        etudiantSelectionne = null;
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        btnEnregistrer.setDisable(false);
        nomField.requestFocus();
        setStatut("Nouveau formulaire prêt.");
    }

    /**
     * Crée un nouvel étudiant via {@link EtudiantService#registerEtudiant(Etudiant)}.
     * Attrape {@link ValidationException} et {@link DatabaseException}.
     */
    @FXML
    private void handleEnregistrer() {
        try {
            Etudiant nouvelEtudiant = lireFormulaire();
            setChargementEnCours(true);
            setStatut("Enregistrement en cours...");

            Task<Void> tache = new Task<>() {
                @Override
                protected Void call() {
                    // Appel exact de votre méthode Service
                    etudiantService.registerEtudiant(nouvelEtudiant);
                    return null;
                }
            };

            tache.setOnSucceeded(e -> {
                setChargementEnCours(false);
                setStatut("✔ Étudiant enregistré avec succès.");
                chargerEtudiants();
                viderFormulaire();
            });

            tache.setOnFailed(e -> {
                setChargementEnCours(false);
                Throwable cause = tache.getException();
                if (cause instanceof ValidationException) {
                    afficherAlerteAvertissement("Validation échouée", cause.getMessage());
                } else if (cause instanceof DatabaseException) {
                    afficherAlerteErreur("Erreur base de données", cause.getMessage());
                } else {
                    afficherAlerteErreur("Erreur", cause != null ? cause.getMessage() : "Inconnue");
                }
                setStatut("Échec de l'enregistrement.");
            });

            new Thread(tache) {{ setDaemon(true); }}.start();

        } catch (ValidationException e) {
            afficherAlerteAvertissement("Saisie invalide", e.getMessage());
        }
    }

    /**
     * Met à jour l'étudiant sélectionné via {@link EtudiantService#updateEtudiant(Etudiant)}.
     * Attrape {@link ValidationException} et {@link DatabaseException}.
     */
    @FXML
    private void handleModifier() {
        if (etudiantSelectionne == null) {
            afficherAlerteAvertissement("Aucune sélection", "Veuillez d'abord sélectionner un étudiant dans la liste.");
            return;
        }
        try {
            Etudiant etudiantModifie = lireFormulaire();
            // On conserve l'idEtudiant original de l'entité sélectionnée
            etudiantModifie.setIdEtudiant(etudiantSelectionne.getIdEtudiant());
            setChargementEnCours(true);
            setStatut("Modification en cours...");

            Task<Void> tache = new Task<>() {
                @Override
                protected Void call() {
                    // Appel exact de votre méthode Service
                    etudiantService.updateEtudiant(etudiantModifie);
                    return null;
                }
            };

            tache.setOnSucceeded(e -> {
                setChargementEnCours(false);
                setStatut("✔ Étudiant modifié avec succès.");
                chargerEtudiants();
                handleNouveau();
            });

            tache.setOnFailed(e -> {
                setChargementEnCours(false);
                Throwable cause = tache.getException();
                if (cause instanceof ValidationException) {
                    afficherAlerteAvertissement("Validation échouée", cause.getMessage());
                } else if (cause instanceof DatabaseException) {
                    afficherAlerteErreur("Erreur base de données", cause.getMessage());
                } else {
                    afficherAlerteErreur("Erreur", cause != null ? cause.getMessage() : "Inconnue");
                }
                setStatut("Échec de la modification.");
            });

            new Thread(tache) {{ setDaemon(true); }}.start();

        } catch (ValidationException e) {
            afficherAlerteAvertissement("Saisie invalide", e.getMessage());
        }
    }

    /**
     * Supprime l'étudiant sélectionné via {@link EtudiantService#removeEtudiant(int)}.
     * Demande confirmation avant de procéder.
     */
    @FXML
    private void handleSupprimer() {
        if (etudiantSelectionne == null) {
            afficherAlerteAvertissement("Aucune sélection", "Veuillez sélectionner un étudiant à supprimer.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer l'étudiant ?");
        confirmation.setContentText(
                "Voulez-vous vraiment supprimer « " +
                etudiantSelectionne.getNom() + " " +
                etudiantSelectionne.getPrenom() + " » ?\n" +
                "Cette action est irréversible."
        );
        confirmation.getDialogPane().setStyle("-fx-background-color: #242840; -fx-text-fill: white;");

        confirmation.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK) {
                int idASupprimer = etudiantSelectionne.getIdEtudiant();
                setChargementEnCours(true);
                setStatut("Suppression en cours...");

                Task<Void> tache = new Task<>() {
                    @Override
                    protected Void call() {
                        // Appel exact de votre méthode Service
                        etudiantService.removeEtudiant(idASupprimer);
                        return null;
                    }
                };

                tache.setOnSucceeded(e -> {
                    setChargementEnCours(false);
                    setStatut("✔ Étudiant supprimé.");
                    chargerEtudiants();
                    handleNouveau();
                });

                tache.setOnFailed(e -> {
                    setChargementEnCours(false);
                    Throwable cause = tache.getException();
                    if (cause instanceof DatabaseException) {
                        afficherAlerteErreur("Erreur base de données", cause.getMessage());
                    } else {
                        afficherAlerteErreur("Erreur", cause != null ? cause.getMessage() : "Inconnue");
                    }
                    setStatut("Échec de la suppression.");
                });

                new Thread(tache) {{ setDaemon(true); }}.start();
            }
        });
    }

    /** Recharge la liste depuis {@link EtudiantService#getAllEtudiants()}. */
    @FXML
    private void handleRafraichir() {
        handleNouveau();
        chargerEtudiants();
    }

    /**
     * Remplit le formulaire avec les données de la ligne cliquée
     * et active les boutons Modifier et Supprimer.
     */
    @FXML
    private void handleTableSelection() {
        Etudiant selectionne = etudiantTable.getSelectionModel().getSelectedItem();
        if (selectionne != null) {
            etudiantSelectionne = selectionne;
            // Remplir les champs avec les valeurs exactes de l'entité
            idEtudiantField.setText(String.valueOf(selectionne.getIdEtudiant()));
            nomField.setText(selectionne.getNom());
            prenomField.setText(selectionne.getPrenom());
            ageField.setText(String.valueOf(selectionne.getAge()));
            // Activer les boutons contextuels
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
            btnEnregistrer.setDisable(true);
            setStatut("Étudiant sélectionné : " + selectionne.getNom() + " " + selectionne.getPrenom());
        }
    }

    // ================================================================
    // Méthodes utilitaires privées
    // ================================================================

    /**
     * Lit le contenu des champs du formulaire et construit un objet {@link Etudiant}.
     *
     * @return l'étudiant construit depuis le formulaire
     * @throws ValidationException si le champ âge n'est pas un entier valide
     */
    private Etudiant lireFormulaire() throws ValidationException {
        String nom    = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String ageStr = ageField.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || ageStr.isEmpty()) {
            throw new ValidationException("Les champs Nom, Prénom et Âge sont obligatoires.");
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new ValidationException("L'âge doit être un nombre entier valide (ex: 20).");
        }

        // idEtudiant = 0 à la création (la BD génère l'ID réel)
        return new Etudiant(0, nom, prenom, age);
    }

    /** Vide tous les champs du formulaire. */
    private void viderFormulaire() {
        idEtudiantField.clear();
        nomField.clear();
        prenomField.clear();
        ageField.clear();
    }

    /** Active ou désactive l'indicateur de chargement et le pied de page. */
    private void setChargementEnCours(boolean enCours) {
        progressIndicator.setVisible(enCours);
        btnEnregistrer.setDisable(enCours);
        btnRafraichir.setDisable(enCours);
    }

    /** Met à jour le label de statut dans le pied de page. */
    private void setStatut(String message) {
        statusLabel.setText(message);
        footerLabel.setText(message);
    }

    /** Affiche une alerte d'erreur (rouge). */
    private void afficherAlerteErreur(String titre, String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle(titre);
        alerte.setHeaderText(titre);
        alerte.setContentText(message);
        alerte.showAndWait();
    }

    /** Affiche une alerte d'avertissement (orange). */
    private void afficherAlerteAvertissement(String titre, String message) {
        Alert alerte = new Alert(Alert.AlertType.WARNING);
        alerte.setTitle(titre);
        alerte.setHeaderText(titre);
        alerte.setContentText(message);
        alerte.showAndWait();
    }
}
