package org.gstudent.entities;

import java.util.Objects;

public class Matiere {
    private int id;
    private String nom;
    private double coefficient;

    public Matiere() {}

    public Matiere(int id, String nom, double coefficient) {
        this.id = id;
        this.nom = nom;
        this.coefficient = coefficient;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matiere matiere = (Matiere) o;
        return id == matiere.id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return nom + " (coeff " + coefficient + ")";
    }
}