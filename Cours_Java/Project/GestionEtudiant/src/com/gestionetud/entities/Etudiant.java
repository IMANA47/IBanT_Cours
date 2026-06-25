package com.gestionetud.entities;

public class Etudiant {

    private int id_etudiant;
    private String nom;
    private String prenom;
    private int age;

    //construteur par defaut

    public Etudiant() {
        System.out.println("Ange");
    }
    //construteur par paramètre


    public Etudiant(int id_etudiant, String nom, String prenom, int age) {
        this.id_etudiant = id_etudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    //Getters et Setters


    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return id_etudiant +","+nom+","+prenom+","+age+" ans";
    }
}
