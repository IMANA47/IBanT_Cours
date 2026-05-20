package com.gestionetude.entities;

// Fichier: Etudiant.java
public class Etudiant {
    // Attributs privés (encapsulation)
    private String nom;
    private String prenom;
    private int age;

    // Constructeur
    public Etudiant(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Vos informations sont les suivantes :" +
                nom +" "+ age+ "ans";
    }
/* 
    public Etudiant(String nom, int age, double moyenne) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("L'âge doit être positif.");
        }
        if (moyenne < 0 || moyenne > 20) {
            throw new IllegalArgumentException("La moyenne doit être comprise entre 0 et 20.");
        }
        this.nom = nom;
        this.age = age;
        this.moyenne = moyenne;
    }

*/    
}
