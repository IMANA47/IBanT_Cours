package org.filatb.modele;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientServiDTO {
    private int id;
    private int numeroTicket;
    private String nom;
    private String motif;
    private boolean priorite;
    private LocalDateTime heureArrivee;
    private LocalDateTime heurePriseEnCharge;
    private String guichet;

    // Constructeurs, getters et setters
    public ClientServiDTO() {}

    public ClientServiDTO(int numeroTicket, String nom, String motif, boolean priorite,
                          LocalDateTime heureArrivee, LocalDateTime heurePriseEnCharge, String guichet) {
        this.numeroTicket = numeroTicket;
        this.nom = nom;
        this.motif = motif;
        this.priorite = priorite;
        this.heureArrivee = heureArrivee;
        this.heurePriseEnCharge = heurePriseEnCharge;
        this.guichet = guichet;
    }

    // Getters et setters pour tous les champs
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumeroTicket() { return numeroTicket; }
    public void setNumeroTicket(int numeroTicket) { this.numeroTicket = numeroTicket; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public boolean isPriorite() { return priorite; }
    public void setPriorite(boolean priorite) { this.priorite = priorite; }

    public LocalDateTime getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(LocalDateTime heureArrivee) { this.heureArrivee = heureArrivee; }

    public LocalDateTime getHeurePriseEnCharge() { return heurePriseEnCharge; }
    public void setHeurePriseEnCharge(LocalDateTime heurePriseEnCharge) { this.heurePriseEnCharge = heurePriseEnCharge; }

    public String getGuichet() { return guichet; }
    public void setGuichet(String guichet) { this.guichet = guichet; }

    // Pour un affichage formaté dans la table, on peut ajouter des getters renvoyant des String
    public String getHeureArriveeFormatted() {
        return heureArrivee.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getHeurePriseFormatted() {
        return heurePriseEnCharge.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("#%d %s (%s)", numeroTicket, nom, motif);
    }
}