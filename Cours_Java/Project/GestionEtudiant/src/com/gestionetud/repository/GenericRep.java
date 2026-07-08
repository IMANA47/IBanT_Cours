package com.gestionetud.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique pour les opérations CRUD sur les entités.
 *
 * @param <T>  Le type de l'entité
 * @param <ID> Le type de l'identifiant unique de l'entité
 */
public interface GenericRep<T, ID> {
    /**
     * Enregistre une nouvelle entité dans la base de données.
     *
     * @param entity L'entité à enregistrer
     */
    void save(T entity);

    /**
     * Met à jour une entité existante dans la base de données.
     *
     * @param entity L'entité à mettre à jour
     */
    void update(T entity);

    /**
     * Supprime une entité par son identifiant unique.
     *
     * @param id L'identifiant de l'entité à supprimer
     */
    void delete(ID id);

    /**
     * Recherche une entité par son identifiant unique.
     *
     * @param id L'identifiant de l'entité à rechercher
     * @return Un Optional contenant l'entité si trouvée, ou un Optional vide sinon
     */
    Optional<T> findById(ID id);

    /**
     * Récupère toutes les entités de ce type.
     *
     * @return La liste de toutes les entités
     */
    List<T> findAll();
}
