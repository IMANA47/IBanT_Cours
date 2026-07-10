package org.filatb.modele;

import org.filatb.modele.exceptions.FileVideException;

public interface IFile<T> {
    void enfiler(T element) throws IllegalStateException; // peut être pleine
    T defiler() throws FileVideException;
    T tete() throws FileVideException;
    boolean estVide();
    boolean estPleine();
    int taille();
    void vider(); // optionnel pour réinitialiser
}