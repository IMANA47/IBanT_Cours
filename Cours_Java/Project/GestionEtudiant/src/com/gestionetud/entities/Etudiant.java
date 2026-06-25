package com.gestionetud.entities;

public class Etudiant {

    private int id;
    private String nom;
    private String prenom;
    private String age;

    //construteur par defaut

    public Etudiant() {
        System.out.println("Ange");
    }
    //construteur par paramètre


    public Etudiant(int id, String nom, String prenom, String age) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    //Getters et Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return id +","+nom+","+prenom+","+age+" ans";
    }
}
