package org.filatb.modele;

import org.filatb.modele.exceptions.FileVideException;
import java.util.ArrayList;
import java.util.List;

public class FileCirculaire<T> implements IFile<T> {
    private T[] elements;
    private int tete;
    private int queue;
    private int taille;
    private final int capacite;

    @SuppressWarnings("unchecked")
    public FileCirculaire(int capacite) {
        if (capacite <= 0) throw new IllegalArgumentException("Capacité doit être > 0");
        this.capacite = capacite;
        elements = (T[]) new Object[capacite];
        tete = 0;
        queue = 0;
        taille = 0;
    }

    @Override
    public void enfiler(T element) {
        if (estPleine()) {
            throw new IllegalStateException("La file est pleine !");
        }
        elements[queue] = element;
        queue = (queue + 1) % capacite;
        taille++;
    }

    @Override
    public T defiler() {
        if (estVide()) {
            throw new FileVideException("Impossible de défiler : file vide");
        }
        T element = elements[tete];
        elements[tete] = null;
        tete = (tete + 1) % capacite;
        taille--;
        return element;
    }

    @Override
    public T tete() {
        if (estVide()) {
            throw new FileVideException("File vide, pas de tête");
        }
        return elements[tete];
    }

    @Override
    public boolean estVide() {
        return taille == 0;
    }

    @Override
    public boolean estPleine() {
        return taille == capacite;
    }

    @Override
    public int taille() {
        return taille;
    }

    @Override
    public void vider() {
        while (!estVide()) {
            defiler();
        }
    }

    // NOUVELLE MÉTHODE POUR EXPOSER LA LISTE
    public List<T> toList() {
        List<T> result = new ArrayList<>(taille);
        for (int i = 0; i < taille; i++) {
            result.add(elements[(tete + i) % capacite]);
        }
        return result;
    }
}