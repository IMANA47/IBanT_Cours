package com.gestionetud.entities;

public class Composer {
    private int id;
    private double note;
    private Etudiant etudiant;
    private Matiere matiere;

    // constructeur par defaut


    public Composer() {
    }

    // constructeur par paramètre


    public Composer(int id, double note, Etudiant etudiant, Matiere matiere) {
        this.id = id;
        this.note = note;
        this.etudiant = etudiant;
        this.matiere = matiere;
    }
    // getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}
