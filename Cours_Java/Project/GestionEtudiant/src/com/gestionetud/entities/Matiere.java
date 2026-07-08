package com.gestionetud.entities;

import java.util.Objects;

/**
 * Représente une matière d'enseignement.
 */
public class Matiere {
    private int idMatiere;
    private String code;
    private String libelle;

    /**
     * Constructeur par défaut.
     */
    public Matiere() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param idMatiere Identifiant unique de la matière
     * @param code      Code abrégé de la matière
     * @param libelle   Libellé/nom descriptif de la matière
     */
    public Matiere(int idMatiere, String code, String libelle) {
        this.idMatiere = idMatiere;
        this.code = code;
        this.libelle = libelle;
    }

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matiere matiere = (Matiere) o;
        return idMatiere == matiere.idMatiere &&
                Objects.equals(code, matiere.code) &&
                Objects.equals(libelle, matiere.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatiere, code, libelle);
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "idMatiere=" + idMatiere +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}