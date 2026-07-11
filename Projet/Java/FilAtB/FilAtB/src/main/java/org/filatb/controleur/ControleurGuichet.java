package org.filatb.controleur;

import org.filatb.modele.Client;
import org.filatb.modele.ClientServiDTO;
import org.filatb.modele.GestionnaireFileAttente;
import org.filatb.utils.AudioPlayer;
import org.filatb.vue.VueGuichet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        // Bouton "Prendre ticket"
        vue.getBtnPrendreTicket().setOnAction(e -> prendreTicket());

        // Bouton "Appeler suivant"
        vue.getBtnAppelerSuivant().setOnAction(e -> appelerSuivant());

        // Bouton "Annuler" - associé à la sélection dans la ListView
        vue.getBtnAnnuler().setOnAction(e -> annulerClient());

        // Rafraîchissement périodique (optionnel) ou via observateurs
    }

    private void prendreTicket() {
        String nom = vue.getTfNom().getText().trim();
        String motif = vue.getComboMotif().getValue();
        if (nom.isEmpty() || motif == null) {
            vue.afficherMessage("Veuillez saisir un nom et choisir un motif.");
            return;
        }
        try {
            Client client = gestionnaire.prendreTicket(nom, motif, false); // priorité false par défaut
            vue.afficherMessage("Ticket #" + client.getNumeroTicket() + " attribué à " + nom);
            vue.getTfNom().clear();
            // Mettre à jour l'affichage
            mettreAJourAffichage();
        } catch (IllegalStateException ex) {
            vue.afficherMessage("Erreur : " + ex.getMessage());
        }
        AudioPlayer.jouerSon("/sounds/ticket.wav");

    }

    private void appelerSuivant() {
        if (gestionnaire.fileVide()) {
            vue.afficherMessage("La file est vide, aucun client à appeler.");
            return;
        }
        clientEnCours = gestionnaire.appelerClientSuivant();
        vue.afficherClientEnCours(clientEnCours);
        // Mettre à jour l'affichage
        mettreAJourAffichage();
        // Jouer le son
        AudioPlayer.jouerSon("/sounds/call.wav");
    }

    private void annulerClient() {
        Client selected = vue.getListViewFile().getSelectionModel().getSelectedItem();
        if (selected == null) {
            vue.afficherMessage("Veuillez sélectionner un client à annuler dans la liste.");
            return;
        }
        boolean ok = gestionnaire.annulerClient(selected.getNumeroTicket());
        if (ok) {
            vue.afficherMessage("Client #" + selected.getNumeroTicket() + " annulé.");
            mettreAJourAffichage();
        } else {
            vue.afficherMessage("Impossible d'annuler ce client.");
        }
    }

    public void mettreAJourAffichage() {
        // Mettre à jour la ListView
        List<Client> clients = gestionnaire.getClientsEnAttente();
        ObservableList<Client> observableList = FXCollections.observableArrayList(clients);
        vue.getListViewFile().setItems(observableList);

        // Mettre à jour les statistiques
        vue.getLblNbAttente().setText("En attente : " + gestionnaire.tailleFile());
        vue.getLblTotalServis().setText("Servis : " + gestionnaire.getTotalClientsServis());
        double tempsMoyen = gestionnaire.getTempsAttenteMoyen();
        vue.getLblTempsMoyen().setText(String.format("Temps moyen : %.1f s", tempsMoyen));

        // Activer/désactiver bouton appeler
        vue.getBtnAppelerSuivant().setDisable(gestionnaire.fileVide());
        // Si file pleine, message éventuel
        if (gestionnaire.filePleine()) {
            vue.afficherMessage("Attention : la file est pleine !");
        }
    }

    public void afficherHistorique() {
        List<ClientServiDTO> historique = gestionnaire.getClientServiDAO().listerTousClientsServis();
        VueHistorique.afficherHistorique(historique);
    }
}