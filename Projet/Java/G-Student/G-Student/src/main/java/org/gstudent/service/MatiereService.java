package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Matiere;
import org.gstudent.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereService {
    private static final Logger logger = LoggerFactory.getLogger(MatiereService.class);

    public void ajouter(Matiere m) throws DaoException, SQLException {
        String sql = "INSERT INTO matiere (nom, coefficient) VALUES (?, ?)";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, m.getNom());
                pstmt.setDouble(2, m.getCoefficient());
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) m.setId(rs.getInt(1));
                ConnexionBD.commitTransaction();
                logger.info("Subject added: {} (coeff: {})", m.getNom(), m.getCoefficient());
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error adding subject", ex);
            throw new DaoException("Erreur ajout matière", ex);
        }
    }

    public void modifier(Matiere m) throws DaoException, SQLException {
        String sql = "UPDATE matiere SET nom=?, coefficient=? WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, m.getNom());
                pstmt.setDouble(2, m.getCoefficient());
                pstmt.setInt(3, m.getId());
                pstmt.executeUpdate();
                ConnexionBD.commitTransaction();
                logger.info("Subject updated: {} (ID: {})", m.getNom(), m.getId());
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error updating subject", ex);
            throw new DaoException("Erreur modification matière", ex);
        }
    }

    public void supprimer(int id) throws DaoException, SQLException {
        String sql = "DELETE FROM matiere WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                ConnexionBD.commitTransaction();
                logger.info("Subject deleted with ID: {}", id);
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error deleting subject", ex);
            throw new DaoException("Erreur suppression matière", ex);
        }
    }

    public List<Matiere> listerTous() throws DaoException {
        List<Matiere> list = new ArrayList<>();
        String sql = "SELECT * FROM matiere ORDER BY nom";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Matiere m = new Matiere();
                    m.setId(rs.getInt("id"));
                    m.setNom(rs.getString("nom"));
                    m.setCoefficient(rs.getDouble("coefficient"));
                    list.add(m);
                }
                logger.debug("Retrieved {} subjects", list.size());
            }
        } catch (SQLException ex) {
            logger.error("Error listing subjects", ex);
            throw new DaoException("Erreur liste matières", ex);
        }
        return list;
    }

    public Matiere trouverParId(int id) throws DaoException {
        String sql = "SELECT * FROM matiere WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Matiere m = new Matiere();
                    m.setId(rs.getInt("id"));
                    m.setNom(rs.getString("nom"));
                    m.setCoefficient(rs.getDouble("coefficient"));
                    logger.debug("Found subject with ID: {}", id);
                    return m;
                }
                return null;
            }
        } catch (SQLException ex) {
            logger.error("Error finding subject by ID", ex);
            throw new DaoException("Erreur recherche matière", ex);
        }
    }
}