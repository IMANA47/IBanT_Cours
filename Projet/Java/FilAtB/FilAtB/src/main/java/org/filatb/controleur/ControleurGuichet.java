package org.filatb.controleur;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.filatb.dao.ClientServiDAO;
import org.filatb.modele.Client;
import org.filatb.modele.ClientServiDTO;
import org.filatb.modele.GestionnaireFileAttente;
import org.filatb.utils.AudioPlayer;
import org.filatb.vue.VueGuichet;
import org.filatb.vue.VueHistorique;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControleurGuichet {
    private final GestionnaireFileAttente gestionnaire;
    private final VueGuichet vue;
    private Client clientEnCours = null;

    public ControleurGuichet(GestionnaireFileAttente gestionnaire, VueGuichet vue) {
        this.gestionnaire = gestionnaire;
        this.vue = vue;
        initialiserEvenements();
        mettreAJourAffichage();
    }

    private void initialiserEvenements() {
        vue.getBtnPrendreTicket().setOnAction(e -> prendreTicket());
        vue.getBtnAppelerSuivant().setOnAction(e -> appelerSuivant());

        // Le bouton annuler du tableau est géré dans la cellule, mais on peut aussi en avoir un global
        // On n'a plus de bouton "Annuler" global ; on utilise celui du tableau.
        // Si vous voulez un bouton global, ajoutez-le dans la vue.
    }

    private void prendreTicket() {
        String nom = vue.getTfNom().getText().trim();
        String motif = vue.getComboMotif().getValue();
        boolean prioritaire = vue.getRbVIP().isSelected();

        if (nom.isEmpty() || motif == null) {
            vue.afficherMessage("Veuillez saisir un nom et choisir un motif.");
            return;
        }

        try {
            Client client = gestionnaire.prendreTicket(nom, motif, prioritaire);
            vue.afficherMessage("Ticket #" + client.getNumeroTicket() + " attribué à " + nom);
            vue.getTfNom().clear();
            AudioPlayer.jouerSon("/sounds/ticket.mp3");
            mettreAJourAffichage();
        } catch (IllegalStateException ex) {
            vue.afficherMessage("Erreur : " + ex.getMessage());
        }
    }

    private void appelerSuivant() {
        if (gestionnaire.fileVide()) {
            vue.afficherMessage("La file est vide, aucun client à appeler.");
            return;
        }
        clientEnCours = gestionnaire.appelerClientSuivant();
        afficherClientEnCours(clientEnCours);
        AudioPlayer.jouerSon("/sounds/call.mp3");
        mettreAJourAffichage();
    }

    public void annulerClient(int numeroTicket) {
        boolean ok = gestionnaire.annulerClient(numeroTicket);
        if (ok) {
            vue.afficherMessage("Client #" + numeroTicket + " annulé.");
            mettreAJourAffichage();
        } else {
            vue.afficherMessage("Impossible d'annuler ce client.");
        }
    }

    private void afficherClientEnCours(Client client) {
        if (client != null) {
            vue.getLblClientEnCoursNumero().setText("B" + String.format("%03d", client.getNumeroTicket()));
            vue.getLblClientEnCoursNom().setText(client.getNom());
            vue.getLblClientEnCoursMotif().setText("Motif : " + client.getMotif());
            vue.getLblClientEnCoursHeure().setText("Arrivée : " + client.getHeureArrivee().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        } else {
            vue.getLblClientEnCoursNumero().setText("Aucun");
            vue.getLblClientEnCoursNom().setText("");
            vue.getLblClientEnCoursMotif().setText("");
            vue.getLblClientEnCoursHeure().setText("");
        }
    }

    public void mettreAJourAffichage() {
        // TableView
        List<Client> clients = gestionnaire.getClientsEnAttente();
        ObservableList<Client> observableList = FXCollections.observableArrayList(clients);
        vue.getTableViewFile().setItems(observableList);

        // Statistiques
        vue.getLblStatsAttente().setText(String.valueOf(gestionnaire.tailleFile()));
        vue.getLblStatsServis().setText(String.valueOf(gestionnaire.getTotalClientsServis()));
        double tempsMoyen = gestionnaire.getTempsAttenteMoyen();
        vue.getLblStatsTemps().setText(formatDuration(tempsMoyen));
        // Prochain ticket
        int prochain = gestionnaire.getProchainNumeroTicket();
        vue.getLblStatsProchain().setText("B" + String.format("%03d", prochain));

        // Dernier ticket émis
        Client dernier = gestionnaire.getDernierTicket();
        if (dernier != null) {
            vue.getLblNumeroDernierTicket().setText("B" + String.format("%03d", dernier.getNumeroTicket()));
            vue.getLblHeureDernierTicket().setText(dernier.getHeureArrivee().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        } else {
            vue.getLblNumeroDernierTicket().setText("Aucun");
            vue.getLblHeureDernierTicket().setText("--:--:--");
        }

        // Total en attente (tableau)
        vue.getLblTotalAttente().setText("Total en attente : " + gestionnaire.tailleFile() + " client(s)");
        vue.getLblNbAttenteTable().setText("(" + gestionnaire.tailleFile() + " clients)");

        // Désactiver bouton appeler si file vide
        vue.getBtnAppelerSuivant().setDisable(gestionnaire.fileVide());

        // Si le client en cours est null, le réafficher
        if (clientEnCours == null) {
            afficherClientEnCours(null);
        }
    }

    private String formatDuration(double seconds) {
        if (seconds <= 0) return "00:00:00";
        int h = (int) (seconds / 3600);
        int m = (int) ((seconds % 3600) / 60);
        int s = (int) (seconds % 60);
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public void afficherHistorique() {
        List<ClientServiDTO> historique = gestionnaire.getClientServiDAO().listerTousClientsServis();
        VueHistorique.afficher(historique);
    }
}