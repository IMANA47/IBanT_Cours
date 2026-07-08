package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
import com.gestionetud.entities.Etudiant;
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
 * Implémentation du repository pour la gestion des étudiants en base de données.
 */
public class EtudiantRepository implements GenericRep<Etudiant, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(EtudiantRepository.class);
    private final Connection connection;

    public EtudiantRepository() {
        this.connection = ConnexionBD.getInstance();
    }

    @Override
    public void save(Etudiant entity) {
        String sql = "INSERT INTO etudiant(nom, prenom, age) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getNom());
            ps.setString(2, entity.getPrenom());
            ps.setInt(3, entity.getAge());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setIdEtudiant(rs.getInt(1));
                }
            }
            logger.info("Étudiant enregistré avec succès (ID : {}) : {} {}", entity.getIdEtudiant(), entity.getNom(), entity.getPrenom());
        } catch (SQLException e) {
            logger.error("Erreur lors de l'enregistrement de l'étudiant", e);
            throw new DatabaseException("Impossible d'enregistrer l'étudiant.", e);
        }
    }

    @Override
    public void update(Etudiant entity) {
        String sql = "UPDATE etudiant SET nom = ?, prenom = ?, age = ? WHERE id_etudiant = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNom());
            ps.setString(2, entity.getPrenom());
            ps.setInt(3, entity.getAge());
            ps.setInt(4, entity.getIdEtudiant());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Étudiant mis à jour avec succès (ID : {})", entity.getIdEtudiant());
            } else {
                logger.warn("Aucun étudiant mis à jour (ID : {} inexistant)", entity.getIdEtudiant());
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de l'étudiant", e);
            throw new DatabaseException("Impossible de mettre à jour l'étudiant.", e);
        }
    }

    @Override
    public void delete(Integer idEtudiant) {
        String sql = "DELETE FROM etudiant WHERE id_etudiant = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Étudiant supprimé avec succès (ID : {})", idEtudiant);
            } else {
                logger.warn("Aucun étudiant supprimé (ID : {} inexistant)", idEtudiant);
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression de l'étudiant", e);
            throw new DatabaseException("Impossible de supprimer l'étudiant.", e);
        }
    }

    @Override
    public Optional<Etudiant> findById(Integer idEtudiant) {
        String sql = "SELECT * FROM etudiant WHERE id_etudiant = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    Etudiant etudiant = new Etudiant(
                            result.getInt("id_etudiant"),
                            result.getString("nom"),
                            result.getString("prenom"),
                            result.getInt("age")
                    );
                    return Optional.of(etudiant);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de l'étudiant par ID", e);
            throw new DatabaseException("Impossible de rechercher l'étudiant.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                etudiants.add(new Etudiant(
                        result.getInt("id_etudiant"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getInt("age")
                ));
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération de la liste des étudiants", e);
            throw new DatabaseException("Impossible de lister les étudiants.", e);
        }
        return etudiants;
    }
}
