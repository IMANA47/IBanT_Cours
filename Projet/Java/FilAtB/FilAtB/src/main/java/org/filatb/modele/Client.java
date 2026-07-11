package org.filatb.modele;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private final int numeroTicket;
    private final String nom;
    private final String motif;
    private final LocalTime heureArrivee;
    private final boolean prioritaire; // pour bonus VIP

    public Client(int numeroTicket, String nom, String motif, boolean prioritaire) {
        this.numeroTicket = numeroTicket;
        this.nom = nom;
        this.motif = motif;
        this.heureArrivee = LocalTime.now();
        this.prioritaire = prioritaire;
    }

    // Getters
    public int getNumeroTicket() { return numeroTicket; }
    public String getNom() { return nom; }
    public String getMotif() { return motif; }
    public LocalTime getHeureArrivee() { return heureArrivee; }
    public boolean isPrioritaire() { return prioritaire; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("#%d %s (%s) à %s", numeroTicket, nom, motif, heureArrivee.format(formatter));
    }
    public String getHeureArriveeFormatee() {
        return heureArrivee.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}