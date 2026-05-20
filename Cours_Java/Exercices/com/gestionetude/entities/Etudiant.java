package com.gestionetude.entities;

// Fichier: Etudiant.java
public class Etudiant {
    // Attributs privés (encapsulation)
    private String nom;
    private int age;
    private double moyenne;

    // Constructeur
    public Etudiant(String nom, int age, double moyenne) {
        this.nom = nom;
        this.age = age;
        this.moyenne = moyenne;
    }

    public String getNom() {
        return nom;
    }
    
    public int getAge() {
        return age;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
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
