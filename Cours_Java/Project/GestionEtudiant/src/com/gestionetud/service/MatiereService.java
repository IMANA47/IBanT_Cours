package com.gestionetud.service;

import com.gestionetud.entities.Matiere;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.repository.GenericRep;
import com.gestionetud.repository.MatiereRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service gérant la logique métier pour les matières.
 */
public class MatiereService {
    private static final Logger logger = LoggerFactory.getLogger(MatiereService.class);
    private final GenericRep<Matiere, Integer> repository;

    public MatiereService() {
        this.repository = new MatiereRepository();
    }

    public MatiereService(GenericRep<Matiere, Integer> repository) {
        this.repository = repository;
    }

    /**
     * Enregistre une nouvelle matière.
     *
     * @param matiere La matière à enregistrer
     */
    public void registerMatiere(Matiere matiere) {
        validateMatiere(matiere);
        logger.info("Tentative d'enregistrement de la matière : {} ({})", matiere.getLibelle(), matiere.getCode());
        repository.save(matiere);
    }

    /**
     * Met à jour les informations d'une matière existante.
     *
     * @param matiere La matière à mettre à jour
     */
    public void updateMatiere(Matiere matiere) {
        if (matiere.getIdMatiere() <= 0) {
            throw new ValidationException("L'identifiant de la matière à mettre à jour doit être supérieur à 0.");
        }
        validateMatiere(matiere);
        logger.info("Tentative de mise à jour de la matière ID : {}", matiere.getIdMatiere());
        repository.update(matiere);
    }

    /**
     * Supprime une matière par son ID.
     *
     * @param id L'identifiant de la matière
     */
    public void removeMatiere(int id) {
        if (id <= 0) {
            throw new ValidationException("L'identifiant à supprimer doit être supérieur à 0.");
        }
        logger.info("Suppression de la matière ID : {}", id);
        repository.delete(id);
    }

    /**
     * Récupère une matière par son ID.
     *
     * @param id L'identifiant de la matière
     * @return La matière correspondante
     */
    public Optional<Matiere> getMatiereById(int id) {
        return repository.findById(id);
    }

    /**
     * Récupère toutes les matières.
     *
     * @return La liste des matières
     */
    public List<Matiere> getAllMatieres() {
        return repository.findAll();
    }

    private void validateMatiere(Matiere matiere) {
        if (matiere == null) {
            throw new ValidationException("La matière ne peut pas être nulle.");
        }
        if (matiere.getCode() == null || matiere.getCode().trim().isEmpty()) {
            throw new ValidationException("Le code de la matière ne peut pas être vide.");
        }
        if (matiere.getLibelle() == null || matiere.getLibelle().trim().isEmpty()) {
            throw new ValidationException("Le libellé de la matière ne peut pas être vide.");
        }
    }
}
