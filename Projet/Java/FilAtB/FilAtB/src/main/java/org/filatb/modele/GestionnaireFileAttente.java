package org.filatb.modele;

import org.filatb.dao.ClientServiDAO;
import org.filatb.dao.ClientServiDAOImpl;
import org.filatb.modele.exceptions.FileVideException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireFileAttente {
    private final IFile<Client> file;
    private int prochainNumeroTicket;
    private int totalClientsServis;
    private final ClientServiDAO clientServiDAO;

    public GestionnaireFileAttente(int capaciteFile) {
        this.file = new FileCirculaire<>(capaciteFile);
        this.clientServiDAO = new ClientServiDAOImpl();
        // Charger les statistiques depuis la base
        chargerStatistiques();
    }

    private void chargerStatistiques() {
        // On récupère le dernier numéro de ticket utilisé et le total servis
        int dernierNumero = clientServiDAO.obtenirDernierNumeroTicket();
        this.prochainNumeroTicket = dernierNumero + 1;
        this.totalClientsServis = clientServiDAO.obtenirTotalClientsServis();
    }

    public Client prendreTicket(String nom, String motif, boolean prioritaire) {
        if (file.estPleine()) {
            throw new IllegalStateException("La file d'attente est pleine !");
        }
        Client client = new Client(prochainNumeroTicket, nom, motif, prioritaire);
        file.enfiler(client);
        prochainNumeroTicket++;
        return client;
    }

    public Client appelerClientSuivant() {
        Client client = file.defiler();
        // Enregistrer dans la base
        LocalDateTime heurePrise = LocalDateTime.now();
        clientServiDAO.enregistrerClientServi(client, heurePrise, "Guichet 1");
        totalClientsServis++;
        return client;
    }

    public Client voirTete() {
        return file.tete();
    }

    public boolean fileVide() {
        return file.estVide();
    }

    public boolean filePleine() {
        return file.estPleine();
    }

    public int tailleFile() {
        return file.taille();
    }

    public List<Client> getClientsEnAttente() {
        // Utilise la méthode toList() de FileCirculaire pour récupérer l'ordre FIFO
        if (file instanceof FileCirculaire) {
            return ((FileCirculaire<Client>) file).toList();
        }
        // Si vous avez d'autres implémentations, adaptez ici
        return new ArrayList<>();
    }

    public int getTotalClientsServis() {
        return totalClientsServis;
    }

    public double getTempsAttenteMoyen() {
        // Calcul basé sur les clients servis dans la base (moyenne des différences)
        return clientServiDAO.calculerTempsAttenteMoyen();
    }

    // Annulation d'un client à une position donnée (par numéro de ticket)
    public boolean annulerClient(int numeroTicket) {
        // On reconstruit la file en excluant le client
        if (file.estVide()) return false;
        List<Client> temp = new ArrayList<>();
        boolean trouve = false;
        // On parcourt la file et on reconstruit
        while (!file.estVide()) {
            Client c = file.defiler();
            if (c.getNumeroTicket() == numeroTicket && !trouve) {
                trouve = true;
                // Ne pas ajouter ce client
            } else {
                temp.add(c);
            }
        }
        // Ré-enfiler les clients conservés
        for (Client c : temp) {
            file.enfiler(c);
        }
        return trouve;
    }

    // Pour mise à jour des stats au redémarrage, on pourrait aussi synchroniser
}
