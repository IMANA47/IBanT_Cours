package com.gestionetud.entities;

import java.util.Objects;

/**
 * Représente un étudiant inscrit.
 */
public class Etudiant {
    private int idEtudiant;
    private String nom;
    private String prenom;
    private int age;

    /**
     * Constructeur par défaut.
     */
    public Etudiant() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param idEtudiant Identifiant unique de l'étudiant
     * @param nom        Nom de famille de l'étudiant
     * @param prenom     Prénom de l'étudiant
     * @param age        Âge de l'étudiant
     */
    public Etudiant(int idEtudiant, String nom, String prenom, int age) {
        this.idEtudiant = idEtudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return idEtudiant == etudiant.idEtudiant &&
                age == etudiant.age &&
                Objects.equals(nom, etudiant.nom) &&
                Objects.equals(prenom, etudiant.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtudiant, nom, prenom, age);
    }

    @Override
    public String toString() {
        return idEtudiant + "," + nom + "," + prenom + "," + age + " ans";
    }
}
