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

    // --- Getters et Setters ---
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

    // --- Méthodes formatées pour l'affichage ---
    public String getHeureArriveeFormatee() {
        if (heureArrivee == null) return "";
        return heureArrivee.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getHeurePriseFormatee() {
        if (heurePriseEnCharge == null) return "";
        return heurePriseEnCharge.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}