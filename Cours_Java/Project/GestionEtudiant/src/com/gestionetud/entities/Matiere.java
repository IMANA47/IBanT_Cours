package com.gestionetud.entities;

public class Matiere {
    private int id;
    private String code;
    private String libelle;

    // constructeur par defaut
    public Matiere() {

    }
    // constructeur par paramètre

    public Matiere(int id, String code, String libelle) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", code=" + code + '\''+
                ", libelle=" + libelle + '\''+
                '}';
    }
}