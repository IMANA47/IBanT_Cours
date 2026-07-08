package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Composer;
import com.gestionetud.entities.Etudiant;
import com.gestionetud.entities.Matiere;
import com.gestionetud.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du repository pour la gestion des relations de composition (notes des étudiants).
 * Note : La table 'composer' en base de données utilise une clé primaire composite (id_etudiant, id_matiere).
 * Le champ 'id' de l'entité Composer est géré ici de manière virtuelle ou fictive (valeur 0).
 */
public class ComposerRepository implements GenericRep<Composer, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ComposerRepository.class);
    private final Connection connection;
    private final EtudiantRepository etudiantRepository;
    private final MatiereRepository matiereRepository;

    public ComposerRepository() {
        this.connection = ConnexionBD.getInstance();
        this.etudiantRepository = new EtudiantRepository();
        this.matiereRepository = new MatiereRepository();
    }

    @Override
    public void save(Composer entity) {
        String sql = "INSERT INTO composer(note, id_etudiant, id_matiere) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, entity.getNote());
            ps.setInt(2, entity.getEtudiant().getIdEtudiant());
            ps.setInt(3, entity.getMatiere().getIdMatiere());
            ps.executeUpdate();
            logger.info("Note enregistrée avec succès : {} pour l'étudiant ID {} dans la matière ID {}",
                    entity.getNote(), entity.getEtudiant().getIdEtudiant(), entity.getMatiere().getIdMatiere());
        } catch (SQLException e) {
            logger.error("Erreur lors de l'enregistrement de la note/composition", e);
            throw new DatabaseException("Impossible d'enregistrer la note.", e);
        }
    }

    /**
     * Met à jour la note d'une association. La mise à jour est basée sur la clé composite (id_etudiant, id_matiere).
     */
    @Override
    public void update(Composer entity) {
        String sql = "UPDATE composer SET note = ? WHERE id_etudiant = ? AND id_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, entity.getNote());
            ps.setInt(2, entity.getEtudiant().getIdEtudiant());
            ps.setInt(3, entity.getMatiere().getIdMatiere());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Note mise à jour avec succès pour l'étudiant ID {} dans la matière ID {}",
                        entity.getEtudiant().getIdEtudiant(), entity.getMatiere().getIdMatiere());
            } else {
                logger.warn("Aucune note mise à jour (association inexistante)");
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de la note/composition", e);
            throw new DatabaseException("Impossible de mettre à jour la note.", e);
        }
    }

    /**
     * Supprime toutes les notes d'un étudiant.
     */
    @Override
    public void delete(Integer idEtudiant) {
        String sql = "DELETE FROM composer WHERE id_etudiant = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            int rowsAffected = ps.executeUpdate();
            logger.info("Notes supprimées pour l'étudiant ID {} (Lignes affectées : {})", idEtudiant, rowsAffected);
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression des notes/composition", e);
            throw new DatabaseException("Impossible de supprimer la note.", e);
        }
    }

    /**
     * Recherche la première note d'un étudiant par son ID d'étudiant.
     */
    @Override
    public Optional<Composer> findById(Integer idEtudiant) {
        String sql = "SELECT * FROM composer WHERE id_etudiant = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    int idMatiere = result.getInt("id_matiere");
                    
                    Etudiant etudiant = etudiantRepository.findById(idEtudiant).orElse(null);
                    Matiere matiere = matiereRepository.findById(idMatiere).orElse(null);

                    Composer composer = new Composer(
                            0, // ID virtuel (clé composite utilisée en BD)
                            result.getDouble("note"),
                            etudiant,
                            matiere
                    );
                    return Optional.of(composer);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la note par ID d'étudiant", e);
            throw new DatabaseException("Impossible de rechercher la note.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Composer> findAll() {
        List<Composer> list = new ArrayList<>();
        String sql = "SELECT * FROM composer";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                int idEtudiant = result.getInt("id_etudiant");
                int idMatiere = result.getInt("id_matiere");

                Etudiant etudiant = etudiantRepository.findById(idEtudiant).orElse(null);
                Matiere matiere = matiereRepository.findById(idMatiere).orElse(null);

                list.add(new Composer(
                        0, // ID virtuel
                        result.getDouble("note"),
                        etudiant,
                        matiere
                ));
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération de toutes les notes", e);
            throw new DatabaseException("Impossible de lister les notes.", e);
        }
        return list;
    }
}
