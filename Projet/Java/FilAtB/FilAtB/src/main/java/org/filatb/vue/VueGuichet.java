package org.filatb.vue;

import org.filatb.modele.Client;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VueGuichet {
    private BorderPane root;
    private TextField tfNom;
    private ComboBox<String> comboMotif;
    private Button btnPrendreTicket;
    private Button btnAppelerSuivant;
    private Button btnAnnuler;
    private Button btnHistorique;   // <-- AJOUTÉ
    private ListView<Client> listViewFile;
    private Label lblClientEnCours;
    private Label lblNbAttente, lblTotalServis, lblTempsMoyen;
    private Label lblMessage;

    public VueGuichet() {
        construireInterface();
    }

    private void construireInterface() {
        root = new BorderPane();
        root.setPadding(new Insets(10));

        // Top : formulaire de prise de ticket
        VBox top = new VBox(10);
        top.setPadding(new Insets(10));
        HBox form = new HBox(10);
        tfNom = new TextField();
        tfNom.setPromptText("Nom du client");
        comboMotif = new ComboBox<>();
        comboMotif.getItems().addAll("Dépôt", "Retrait", "Ouverture de compte", "Virement", "Autre");
        comboMotif.setPromptText("Motif");
        btnPrendreTicket = new Button("Prendre un ticket");
        form.getChildren().addAll(tfNom, comboMotif, btnPrendreTicket);
        top.getChildren().add(form);

        // Centre : ListView de la file + bouton annuler
        VBox center = new VBox(10);
        center.setPadding(new Insets(10));
        Label lblFile = new Label("File d'attente (FIFO)");
        listViewFile = new ListView<>();
        listViewFile.setPrefHeight(300);
        btnAnnuler = new Button("Annuler le client sélectionné");
        center.getChildren().addAll(lblFile, listViewFile, btnAnnuler);

        // Right : panneau de contrôle et stats
        VBox right = new VBox(10);
        right.setPadding(new Insets(10));
        right.setPrefWidth(200);
        btnAppelerSuivant = new Button("Appeler client suivant");
        btnAppelerSuivant.setDisable(true);
        Label lblTraitement = new Label("En cours de traitement :");
        lblClientEnCours = new Label("Aucun");
        lblClientEnCours.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");

        // --- AJOUT DU BOUTON HISTORIQUE ---
        btnHistorique = new Button("Voir historique");
        // ---------------------------------

        right.getChildren().addAll(btnAppelerSuivant, lblTraitement, lblClientEnCours,
                new Separator(), new Label("Statistiques :"),
                (lblNbAttente = new Label("En attente : 0")),
                (lblTotalServis = new Label("Servis : 0")),
                (lblTempsMoyen = new Label("Temps moyen : 0 s")),
                btnHistorique);   // <-- AJOUTÉ DANS LA LISTE

        // Bottom : zone de message
        lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: blue;");
        root.setTop(top);
        root.setCenter(center);
        root.setRight(right);
        root.setBottom(lblMessage);
        BorderPane.setMargin(lblMessage, new Insets(10));
    }

    // Getters (tous les getters déjà présents, assurez-vous que getBtnHistorique existe)
    public BorderPane getRoot() { return root; }
    public TextField getTfNom() { return tfNom; }
    public ComboBox<String> getComboMotif() { return comboMotif; }
    public Button getBtnPrendreTicket() { return btnPrendreTicket; }
    public Button getBtnAppelerSuivant() { return btnAppelerSuivant; }
    public Button getBtnAnnuler() { return btnAnnuler; }
    public Button getBtnHistorique() { return btnHistorique; }   // <-- GETTER
    public ListView<Client> getListViewFile() { return listViewFile; }
    public Label getLblClientEnCours() { return lblClientEnCours; }
    public Label getLblNbAttente() { return lblNbAttente; }
    public Label getLblTotalServis() { return lblTotalServis; }
    public Label getLblTempsMoyen() { return lblTempsMoyen; }

    public void afficherMessage(String msg) {
        lblMessage.setText(msg);
    }

    public void afficherClientEnCours(Client client) {
        if (client != null) {
            lblClientEnCours.setText(client.toString());
        } else {
            lblClientEnCours.setText("Aucun");
        }
    }
}