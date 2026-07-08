package com.gestionetud.service;

import com.gestionetud.entities.Composer;
import com.gestionetud.exception.ValidationException;
import com.gestionetud.repository.ComposerRepository;
import com.gestionetud.repository.GenericRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service gérant la logique métier pour les notes (Compositions) des étudiants.
 */
public class ComposerService {
    private static final Logger logger = LoggerFactory.getLogger(ComposerService.class);
    private final GenericRep<Composer, Integer> repository;

    public ComposerService() {
        this.repository = new ComposerRepository();
    }

    public ComposerService(GenericRep<Composer, Integer> repository) {
        this.repository = repository;
    }

    /**
     * Enregistre une note pour un étudiant dans une matière.
     *
     * @param composer L'association note/étudiant/matière
     */
    public void registerNote(Composer composer) {
        validateComposer(composer);
        logger.info("Enregistrement de la note {} pour l'étudiant ID {} dans la matière ID {}",
                composer.getNote(), composer.getEtudiant().getIdEtudiant(), composer.getMatiere().getIdMatiere());
        repository.save(composer);
    }

    /**
     * Met à jour une note existante.
     *
     * @param composer L'association à mettre à jour
     */
    public void updateNote(Composer composer) {
        if (composer.getId() <= 0) {
            throw new ValidationException("L'identifiant de la note à mettre à jour doit être supérieur à 0.");
        }
        validateComposer(composer);
        logger.info("Mise à jour de la note ID : {}", composer.getId());
        repository.update(composer);
    }

    /**
     * Supprime une note par son ID.
     *
     * @param id L'identifiant de la note
     */
    public void removeNote(int id) {
        if (id <= 0) {
            throw new ValidationException("L'identifiant à supprimer doit être supérieur à 0.");
        }
        logger.info("Suppression de la note ID : {}", id);
        repository.delete(id);
    }

    /**
     * Récupère une note par son ID.
     *
     * @param id L'identifiant de la note
     * @return La note trouvée
     */
    public Optional<Composer> getNoteById(int id) {
        return repository.findById(id);
    }

    /**
     * Récupère toutes les notes enregistrées.
     *
     * @return La liste des notes
     */
    public List<Composer> getAllNotes() {
        return repository.findAll();
    }

    /**
     * Récupère toutes les notes d'un étudiant donné.
     *
     * @param idEtudiant L'identifiant de l'étudiant
     * @return La liste de ses notes
     */
    public List<Composer> getNotesByEtudiant(int idEtudiant) {
        return repository.findAll().stream()
                .filter(c -> c.getEtudiant() != null && c.getEtudiant().getIdEtudiant() == idEtudiant)
                .collect(Collectors.toList());
    }

    /**
     * Calcule la moyenne des notes d'un étudiant.
     *
     * @param idEtudiant L'identifiant de l'étudiant
     * @return La moyenne (0.0 si aucune note n'est enregistrée)
     */
    public double calculateMoyenne(int idEtudiant) {
        logger.info("Calcul de la moyenne pour l'étudiant ID : {}", idEtudiant);
        return repository.findAll().stream()
                .filter(c -> c.getEtudiant() != null && c.getEtudiant().getIdEtudiant() == idEtudiant)
                .mapToDouble(Composer::getNote)
                .average()
                .orElse(0.0);
    }

    private void validateComposer(Composer composer) {
        if (composer == null) {
            throw new ValidationException("La note/composition ne peut pas être nulle.");
        }
        if (composer.getEtudiant() == null || composer.getEtudiant().getIdEtudiant() <= 0) {
            throw new ValidationException("L'étudiant associé doit posséder un identifiant valide.");
        }
        if (composer.getMatiere() == null || composer.getMatiere().getIdMatiere() <= 0) {
            throw new ValidationException("La matière associée doit posséder un identifiant valide.");
        }
        if (composer.getNote() < 0.0 || composer.getNote() > 20.0) {
            throw new ValidationException("La note doit obligatoirement être comprise entre 0.0 et 20.0.");
        }
    }
}
