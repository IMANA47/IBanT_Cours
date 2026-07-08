package com.gestionetud.entities;

import java.util.Objects;

/**
 * Représente la relation d'association entre un étudiant, une matière et la note obtenue.
 */
public class Composer {
    private int id;
    private double note;
    private Etudiant etudiant;
    private Matiere matiere;

    /**
     * Constructeur par défaut.
     */
    public Composer() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param id       Identifiant de la note
     * @param note     Note obtenue (entre 0 et 20)
     * @param etudiant L'étudiant concerné
     * @param matiere  La matière concernée
     */
    public Composer(int id, double note, Etudiant etudiant, Matiere matiere) {
        this.id = id;
        this.note = note;
        this.etudiant = etudiant;
        this.matiere = matiere;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composer composer = (Composer) o;
        return id == composer.id &&
                Double.compare(composer.note, note) == 0 &&
                Objects.equals(etudiant, composer.etudiant) &&
                Objects.equals(matiere, composer.matiere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, etudiant, matiere);
    }

    @Override
    public String toString() {
        return "Composer{" +
                "id=" + id +
                ", note=" + note +
                ", etudiant=" + (etudiant != null ? etudiant.getNom() + " " + etudiant.getPrenom() : "null") +
                ", matiere=" + (matiere != null ? matiere.getCode() : "null") +
                '}';
    }
}
