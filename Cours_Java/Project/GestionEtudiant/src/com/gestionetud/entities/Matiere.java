package com.gestionetud.entities;

public class Etudiant {
    private int id_matiere;
    private String code;
    private String libelle;

    // constructeur par defaut
    public Matiere() {

    }
    // constructeur par paramètre

    public Matiere(int id_matiere, String code, String libelle) {
        this.id_matiere = id_matiere;
        this.code = code;
        this.libelle = libelle;
    }

    public int getId_matiere() {
        return id_matiere;
    }
    public void setId_matiere(int id_matiere) {
        this.id_matiere = id_matiere;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "id=" + id_matiere +
                ", code=" + code + '\''+
                ", libelle=" + libelle + '\''+
                '}';
    }
}