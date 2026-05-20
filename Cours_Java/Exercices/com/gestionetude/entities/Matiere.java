package com.gestionetude.entities;

public class Matiere {
    private int id;
    private int code;
    private String libelle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Matiere() {
    }

    public Matiere(int id, int code, String libelle) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
    }
    public void Save(){}
    public void Delete(){}
    public void Update(){}
}
