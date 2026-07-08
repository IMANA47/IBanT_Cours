package com.gestionetud.repository;

import com.gestionetud.config.ConnexionBD;
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
 * Implémentation du repository pour la gestion des matières en base de données.
 */
public class MatiereRepository implements GenericRep<Matiere, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MatiereRepository.class);
    private final Connection connection;

    public MatiereRepository() {
        this.connection = ConnexionBD.getInstance();
    }

    @Override
    public void save(Matiere entity) {
        String sql = "INSERT INTO matiere(code, libelle) VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getCode());
            ps.setString(2, entity.getLibelle());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setIdMatiere(rs.getInt(1));
                }
            }
            logger.info("Matière enregistrée avec succès (ID : {}) : {} ({})", entity.getIdMatiere(), entity.getLibelle(), entity.getCode());
        } catch (SQLException e) {
            logger.error("Erreur lors de l'enregistrement de la matière", e);
            throw new DatabaseException("Impossible d'enregistrer la matière.", e);
        }
    }

    @Override
    public void update(Matiere entity) {
        String sql = "UPDATE matiere SET code = ?, libelle = ? WHERE id_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getCode());
            ps.setString(2, entity.getLibelle());
            ps.setInt(3, entity.getIdMatiere());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Matière mise à jour avec succès (ID : {})", entity.getIdMatiere());
            } else {
                logger.warn("Aucune matière mise à jour (ID : {} inexistant)", entity.getIdMatiere());
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de la matière", e);
            throw new DatabaseException("Impossible de mettre à jour la matière.", e);
        }
    }

    @Override
    public void delete(Integer idMatiere) {
        String sql = "DELETE FROM matiere WHERE id_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idMatiere);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Matière supprimée avec succès (ID : {})", idMatiere);
            } else {
                logger.warn("Aucune matière supprimée (ID : {} inexistant)", idMatiere);
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression de la matière", e);
            throw new DatabaseException("Impossible de supprimer la matière.", e);
        }
    }

    @Override
    public Optional<Matiere> findById(Integer idMatiere) {
        String sql = "SELECT * FROM matiere WHERE id_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idMatiere);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    Matiere matiere = new Matiere(
                            result.getInt("id_matiere"),
                            result.getString("code"),
                            result.getString("libelle")
                    );
                    return Optional.of(matiere);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la matière par ID", e);
            throw new DatabaseException("Impossible de rechercher la matière.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Matiere> findAll() {
        List<Matiere> matieres = new ArrayList<>();
        String sql = "SELECT * FROM matiere";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                matieres.add(new Matiere(
                        result.getInt("id_matiere"),
                        result.getString("code"),
                        result.getString("libelle")
                ));
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération de la liste des matières", e);
            throw new DatabaseException("Impossible de lister les matières.", e);
        }
        return matieres;
    }
}
