package com.gestionetud.service;

import com.gestionetud.entities.Etudiant;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.repository.EtudiantRepository;
import com.gestionetud.repository.GenericRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service gérant la logique métier pour les étudiants.
 */
public class EtudiantService {
    private static final Logger logger = LoggerFactory.getLogger(EtudiantService.class);
    private final GenericRep<Etudiant, Integer> repository;

    public EtudiantService() {
        this.repository = new EtudiantRepository();
    }

    public EtudiantService(GenericRep<Etudiant, Integer> repository) {
        this.repository = repository;
    }

    /**
     * Enregistre un nouvel étudiant après validation des données.
     *
     * @param etudiant L'étudiant à enregistrer
     * @throws ValidationException Si l'étudiant est invalide
     */
    public void registerEtudiant(Etudiant etudiant) {
        validateEtudiant(etudiant);
        logger.info("Tentative d'enregistrement de l'étudiant : {} {}", etudiant.getNom(), etudiant.getPrenom());
        repository.save(etudiant);
    }

    /**
     * Met à jour les informations d'un étudiant existant.
     *
     * @param etudiant L'étudiant avec les nouvelles informations
     * @throws ValidationException Si les données de l'étudiant sont invalides
     */
    public void updateEtudiant(Etudiant etudiant) {
        if (etudiant.getIdEtudiant() <= 0) {
            throw new ValidationException("L'identifiant de l'étudiant à mettre à jour doit être supérieur à 0.");
        }
        validateEtudiant(etudiant);
        logger.info("Tentative de mise à jour de l'étudiant ID : {}", etudiant.getIdEtudiant());
        repository.update(etudiant);
    }

    /**
     * Supprime un étudiant.
     *
     * @param id L'identifiant de l'étudiant
     */
    public void removeEtudiant(int id) {
        if (id <= 0) {
            throw new ValidationException("L'identifiant à supprimer doit être supérieur à 0.");
        }
        logger.info("Suppression de l'étudiant ID : {}", id);
        repository.delete(id);
    }

    /**
     * Récupère un étudiant par son ID.
     *
     * @param id L'identifiant de l'étudiant
     * @return L'étudiant s'il existe
     */
    public Optional<Etudiant> getEtudiantById(int id) {
        return repository.findById(id);
    }

    /**
     * Récupère tous les étudiants.
     *
     * @return Liste des étudiants
     */
    public List<Etudiant> getAllEtudiants() {
        return repository.findAll();
    }

    private void validateEtudiant(Etudiant etudiant) {
        if (etudiant == null) {
            throw new ValidationException("L'étudiant ne peut pas être nul.");
        }
        if (etudiant.getNom() == null || etudiant.getNom().trim().isEmpty()) {
            throw new ValidationException("Le nom de l'étudiant ne peut pas être vide.");
        }
        if (etudiant.getPrenom() == null || etudiant.getPrenom().trim().isEmpty()) {
            throw new ValidationException("Le prénom de l'étudiant ne peut pas être vide.");
        }
        if (etudiant.getAge() <= 0 || etudiant.getAge() > 120) {
            throw new ValidationException("L'âge de l'étudiant doit être compris entre 1 et 120 ans.");
        }
    }
}
