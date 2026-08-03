package org.filatb.vue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.filatb.controleur.ControleurGuichet;
import org.filatb.modele.Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VueGuichet {
    private BorderPane root;
    private ControleurGuichet controleur;

    // Composants
    private TableView<Client> tableViewFile;
    private Label lblNumeroDernierTicket, lblHeureDernierTicket;
    private Label lblClientEnCoursNumero, lblClientEnCoursNom, lblClientEnCoursMotif, lblClientEnCoursHeure;
    private Label lblStatsAttente, lblStatsServis, lblStatsTemps, lblStatsProchain;
    private Label lblMessage;
    private Label lblTotalAttente;
    private Label lblSousTitreTable;

    private Button btnPrendreTicket, btnAppelerSuivant, btnFinirTraitement;
    private Button btnHistorique, btnReinitialiser;
    private ComboBox<String> comboMotif;
    private TextField tfNom;
    private RadioButton rbVIP, rbStandard;
    private ToggleGroup tgPriorite;

    public VueGuichet() {
        construireInterface();
    }

    private void construireInterface() {
        root = new BorderPane();
        root.setPadding(Insets.EMPTY);

        HBox header = creerHeader();
        root.setTop(header);

        BorderPane mainContent = new BorderPane();
        mainContent.setLeft(creerColonneGauche());
        mainContent.setCenter(creerCentre());
        mainContent.setRight(creerColonneDroite());
        mainContent.setPadding(new Insets(15));
        root.setCenter(mainContent);

        HBox footer = creerFooter();
        root.setBottom(footer);

        lancerHorloge();
    }

    private HBox creerHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);
        header.setPadding(new Insets(8, 25, 8, 25));

        Label logo = new Label("🏦");
        logo.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        VBox titleBox = new VBox(0);
        Label title = new Label("FilAtB");
        title.getStyleClass().add("header-title");
        Label subtitle = new Label("Gestion de la file d'attente");
        subtitle.getStyleClass().add("header-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label clockLabel = new Label();
        clockLabel.getStyleClass().add("header-clock");

        Label userLabel = new Label("👤 Guichetier 1");
        userLabel.getStyleClass().add("header-user");

        header.getChildren().addAll(logo, titleBox, spacer, clockLabel, userLabel);
        return header;
    }

    private VBox creerColonneGauche() {
        VBox left = new VBox(15);
        left.setPrefWidth(260);

        // Carte Prendre ticket
        VBox carteTicket = new VBox(8);
        carteTicket.getStyleClass().add("card");

        Label titreTicket = new Label("🎫 PRENDRE UN TICKET");
        titreTicket.getStyleClass().add("card-title");

        Label lblNom = new Label("Nom du client *");
        tfNom = new TextField();
        tfNom.setPromptText("Entrez le nom du client");
        tfNom.setPrefHeight(32);

        Label lblMotif = new Label("Motif de visite *");
        comboMotif = new ComboBox<>();
        comboMotif.getItems().addAll("Dépôt", "Retrait", "Ouverture de compte", "Virement", "Autre");
        comboMotif.setPromptText("Sélectionnez un motif");
        comboMotif.setPrefWidth(Double.MAX_VALUE);
        comboMotif.setPrefHeight(32);

        Label lblPriorite = new Label("Type de client");
        tgPriorite = new ToggleGroup();
        rbVIP = new RadioButton("★ Client VIP / Prioritaire");
        rbStandard = new RadioButton("Client Standard");
        rbStandard.setSelected(true);
        rbVIP.setToggleGroup(tgPriorite);
        rbStandard.setToggleGroup(tgPriorite);

        btnPrendreTicket = new Button("PRENDRE UN TICKET");
        btnPrendreTicket.getStyleClass().add("button-primary");
        btnPrendreTicket.setMaxWidth(Double.MAX_VALUE);
        btnPrendreTicket.setPrefHeight(38);

        carteTicket.getChildren().addAll(titreTicket, lblNom, tfNom, lblMotif, comboMotif, lblPriorite, rbVIP, rbStandard, btnPrendreTicket);

        // Carte Dernier ticket
        VBox carteDernier = new VBox(4);
        carteDernier.getStyleClass().add("card");
        Label titreDernier = new Label("📌 DERNIER TICKET ÉMIS");
        titreDernier.getStyleClass().add("card-title");

        lblNumeroDernierTicket = new Label("Aucun");
        lblNumeroDernierTicket.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #0F3D91;");

        HBox heureBox = new HBox(5);
        Label heureLabel = new Label("Heure d'arrivée");
        heureLabel.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
        lblHeureDernierTicket = new Label("--:--:--");
        lblHeureDernierTicket.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E293B;");
        heureBox.getChildren().addAll(heureLabel, lblHeureDernierTicket);

        carteDernier.getChildren().addAll(titreDernier, lblNumeroDernierTicket, heureBox);

        left.getChildren().addAll(carteTicket, carteDernier);
        return left;
    }

    @SuppressWarnings("unchecked")
    private VBox creerCentre() {
        VBox center = new VBox(15);
        center.setPadding(new Insets(0, 15, 0, 15));

        // Carte Client en cours
        VBox carteClient = new VBox(10);
        carteClient.getStyleClass().add("card");
        carteClient.setStyle("-fx-background-color: #0F3D91; -fx-background-radius: 14px;");
        carteClient.setPadding(new Insets(18));

        Label titreClient = new Label("👤 CLIENT EN COURS");
        titreClient.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        HBox infoClient = new HBox(20);
        infoClient.setAlignment(Pos.CENTER_LEFT);

        lblClientEnCoursNumero = new Label("Aucun");
        lblClientEnCoursNumero.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");

        VBox details = new VBox(2);
        lblClientEnCoursNom = new Label("");
        lblClientEnCoursMotif = new Label("");
        lblClientEnCoursHeure = new Label("");
        lblClientEnCoursNom.setStyle("-fx-text-fill: #E0E7FF; -fx-font-size: 14px;");
        lblClientEnCoursMotif.setStyle("-fx-text-fill: #E0E7FF; -fx-font-size: 13px;");
        lblClientEnCoursHeure.setStyle("-fx-text-fill: #E0E7FF; -fx-font-size: 13px;");
        details.getChildren().addAll(lblClientEnCoursNom, lblClientEnCoursMotif, lblClientEnCoursHeure);

        infoClient.getChildren().addAll(lblClientEnCoursNumero, details);

        HBox boutonsBox = new HBox(15);
        boutonsBox.setAlignment(Pos.CENTER);

        btnAppelerSuivant = new Button("📞 APPELER CLIENT SUIVANT");
        btnAppelerSuivant.getStyleClass().add("button-success");
        btnAppelerSuivant.setPrefHeight(44);
        btnAppelerSuivant.setPrefWidth(200);

        btnFinirTraitement = new Button("✅ FINIR TRAITEMENT");
        btnFinirTraitement.getStyleClass().add("button-danger");
        btnFinirTraitement.setPrefHeight(44);
        btnFinirTraitement.setPrefWidth(200);
        btnFinirTraitement.setDisable(true);

        boutonsBox.getChildren().addAll(btnAppelerSuivant, btnFinirTraitement);

        carteClient.getChildren().addAll(titreClient, infoClient, boutonsBox);

        // ---------- TABLEAU ----------
        VBox tableBox = new VBox(6);
        Label titreTable = new Label("FILE D'ATTENTE");
        titreTable.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label sousTitreTable = new Label();
        sousTitreTable.setId("lblNbAttenteTable");
        lblSousTitreTable = sousTitreTable;

        tableViewFile = new TableView<>();
        tableViewFile.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableViewFile.setPrefHeight(220);

        TableColumn<Client, Integer> colIndex = new TableColumn<>("#");
        colIndex.setCellValueFactory(new PropertyValueFactory<>("numeroTicket"));
        colIndex.setPrefWidth(35);

        TableColumn<Client, Integer> colTicket = new TableColumn<>("Ticket");
        colTicket.setCellValueFactory(new PropertyValueFactory<>("numeroTicket"));
        colTicket.setPrefWidth(70);

        TableColumn<Client, String> colNom = new TableColumn<>("Nom du client");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(130);

        TableColumn<Client, String> colMotif = new TableColumn<>("Motif");
        colMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        colMotif.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String motif, boolean empty) {
                super.updateItem(motif, empty);
                if (empty || motif == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label badge = new Label(motif);
                    badge.getStyleClass().add(getBadgeClass(motif));
                    setGraphic(badge);
                    setText(null);
                }
            }
        });
        colMotif.setPrefWidth(100);

        TableColumn<Client, String> colHeure = new TableColumn<>("Heure d'arrivée");
        colHeure.setCellValueFactory(new PropertyValueFactory<>("heureArriveeFormatee"));
        colHeure.setPrefWidth(100);

        TableColumn<Client, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btnSuppr = new Button("X");
            {
                btnSuppr.getStyleClass().add("button-danger");
                btnSuppr.setPrefWidth(40);
                btnSuppr.setPadding(new Insets(2, 6, 2, 6));
                btnSuppr.setOnAction(e -> {
                    Client client = getTableView().getItems().get(getIndex());
                    if (controleur != null) {
                        controleur.annulerClient(client.getNumeroTicket());
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(btnSuppr);
            }
        });
        colAction.setPrefWidth(60);

        tableViewFile.getColumns().addAll(colIndex, colTicket, colNom, colMotif, colHeure, colAction);

        Label totalAttente = new Label();
        totalAttente.setId("lblTotalAttente");
        totalAttente.setAlignment(Pos.CENTER);
        totalAttente.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E293B; -fx-font-size: 13px;");
        lblTotalAttente = totalAttente;

        tableBox.getChildren().addAll(titreTable, sousTitreTable, tableViewFile, totalAttente);

        center.getChildren().addAll(carteClient, tableBox);
        return center;
    }

    private String getBadgeClass(String motif) {
        switch (motif) {
            case "Dépôt": return "badge-depot";
            case "Retrait": return "badge-retrait";
            case "Ouverture de compte": return "badge-ouverture";
            case "Virement": return "badge-virement";
            default: return "badge-autre";
        }
    }

    private VBox creerColonneDroite() {
        VBox right = new VBox(15);
        right.setPrefWidth(220);

        VBox stats = new VBox(6);
        stats.getStyleClass().add("card");

        Label titreStats = new Label("📊 STATISTIQUES");
        titreStats.getStyleClass().add("card-title");

        GridPane gridStats = new GridPane();
        gridStats.setHgap(10);
        gridStats.setVgap(8);

        Label lbl1 = new Label("Clients en attente");
        lbl1.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
        lblStatsAttente = new Label("0");
        lblStatsAttente.getStyleClass().add("card-value");
        lblStatsAttente.setStyle("-fx-font-size: 24px;");
        gridStats.add(lbl1, 0, 0);
        gridStats.add(lblStatsAttente, 0, 1);

        Label lbl2 = new Label("Clients servis");
        lbl2.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
        lblStatsServis = new Label("0");
        lblStatsServis.getStyleClass().add("card-value");
        lblStatsServis.setStyle("-fx-font-size: 24px;");
        gridStats.add(lbl2, 1, 0);
        gridStats.add(lblStatsServis, 1, 1);

        Label lbl3 = new Label("Temps d'attente moyen");
        lbl3.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
        lblStatsTemps = new Label("00:00:00");
        lblStatsTemps.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0F3D91;");
        gridStats.add(lbl3, 0, 2);
        gridStats.add(lblStatsTemps, 0, 3);

        Label lbl4 = new Label("Prochain ticket");
        lbl4.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
        lblStatsProchain = new Label("Aucun");
        lblStatsProchain.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0F3D91;");
        gridStats.add(lbl4, 1, 2);
        gridStats.add(lblStatsProchain, 1, 3);

        stats.getChildren().addAll(titreStats, gridStats);

        VBox actions = new VBox(8);
        actions.getStyleClass().add("card");

        Label titreActions = new Label("⚡ ACTIONS RAPIDES");
        titreActions.getStyleClass().add("card-title");

        btnHistorique = new Button("📋 Historique");
        btnHistorique.getStyleClass().add("button-outline");
        btnHistorique.setMaxWidth(Double.MAX_VALUE);
        btnHistorique.setPrefHeight(35);

        btnReinitialiser = new Button("🔄 Réinitialiser la file");
        btnReinitialiser.getStyleClass().add("button-outline");
        btnReinitialiser.setMaxWidth(Double.MAX_VALUE);
        btnReinitialiser.setPrefHeight(35);

        actions.getChildren().addAll(titreActions, btnHistorique, btnReinitialiser);

        right.getChildren().addAll(stats, actions);
        return right;
    }

    private HBox creerFooter() {
        HBox footer = new HBox();
        footer.getStyleClass().add("footer");
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setSpacing(15);
        footer.setPadding(new Insets(6, 25, 6, 25));

        HBox statusBox = new HBox(8);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        Label dot = new Label();
        dot.getStyleClass().add("status-dot");
        Label statusText = new Label("Système opérationnel");
        statusText.getStyleClass().add("footer-text");
        statusBox.getChildren().addAll(dot, statusText);

        if (lblMessage == null) {
            lblMessage = new Label();
            lblMessage.setStyle("-fx-text-fill: #FFD700; -fx-font-weight: bold; -fx-font-size: 12px;");
            lblMessage.setVisible(false);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label msg = new Label("Merci de votre patience et bonne journée !");
        msg.getStyleClass().add("footer-text");
        msg.setStyle("-fx-font-weight: bold;");

        Label lblVersion = new Label("v1.0.0");
        lblVersion.getStyleClass().add("footer-version");

        footer.getChildren().addAll(statusBox, lblMessage, spacer, msg, lblVersion);
        return footer;
    }

    private void lancerHorloge() {
        Label clockLabel = (Label) ((HBox) root.getTop()).getChildren().get(3);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    clockLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss   dd/MM/yyyy")));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void afficherMessage(String msg) {
        if (lblMessage != null) {
            lblMessage.setText(msg);
            lblMessage.setVisible(true);
            Timeline timer = new Timeline(new KeyFrame(Duration.seconds(4), e -> lblMessage.setVisible(false)));
            timer.setCycleCount(1);
            timer.play();
        }
    }

    // Getters
    public BorderPane getRoot() { return root; }
    public void setControleur(ControleurGuichet controleur) { this.controleur = controleur; }

    public Button getBtnPrendreTicket() { return btnPrendreTicket; }
    public Button getBtnAppelerSuivant() { return btnAppelerSuivant; }
    public Button getBtnFinirTraitement() { return btnFinirTraitement; }
    public Button getBtnHistorique() { return btnHistorique; }
    public Button getBtnReinitialiser() { return btnReinitialiser; }
    public TableView<Client> getTableViewFile() { return tableViewFile; }
    public TextField getTfNom() { return tfNom; }
    public ComboBox<String> getComboMotif() { return comboMotif; }
    public RadioButton getRbVIP() { return rbVIP; }
    public RadioButton getRbStandard() { return rbStandard; }
    public Label getLblNumeroDernierTicket() { return lblNumeroDernierTicket; }
    public Label getLblHeureDernierTicket() { return lblHeureDernierTicket; }
    public Label getLblClientEnCoursNumero() { return lblClientEnCoursNumero; }
    public Label getLblClientEnCoursNom() { return lblClientEnCoursNom; }
    public Label getLblClientEnCoursMotif() { return lblClientEnCoursMotif; }
    public Label getLblClientEnCoursHeure() { return lblClientEnCoursHeure; }
    public Label getLblStatsAttente() { return lblStatsAttente; }
    public Label getLblStatsServis() { return lblStatsServis; }
    public Label getLblStatsTemps() { return lblStatsTemps; }
    public Label getLblStatsProchain() { return lblStatsProchain; }
    public Label getLblTotalAttente() { return lblTotalAttente; }
    public Label getLblNbAttenteTable() { return lblSousTitreTable; }
}