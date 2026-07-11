package org.filatb.modele;

import java.time.LocalDateTime;

public class ClientServiDTO {
    private int id;
    private int numeroTicket;
    private String nom;
    private String motif;
    private boolean priorite;
    private LocalDateTime heureArrivee;
    private LocalDateTime heurePriseEnCharge;
    private String guichet;

    // constructeur, getters, setters (ou un constructeur avec tous les champs)
    // Pour l'affichage, override toString()
}