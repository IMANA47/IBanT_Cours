package org.gstudent.entities;

import java.util.Objects;

public class Composer {
    private int id;
    private Etudiant etudiant;
    private Matiere matiere;
    private double note; // sur 20

    public Composer() {}

    public Composer(int id, Etudiant etudiant, Matiere matiere, double note) {
        this.id = id;
        this.etudiant = etudiant;
        this.matiere = matiere;
        this.note = note;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }
    public double getNote() { return note; }
    public void setNote(double note) { this.note = note; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composer that = (Composer) o;
        return id == that.id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}