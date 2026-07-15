package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Etudiant;
import org.gstudent.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantService {
    private static final Logger logger = LoggerFactory.getLogger(EtudiantService.class);

    public void ajouter(Etudiant e) throws DaoException, SQLException {
        String sql = "INSERT INTO etudiant (nom, prenom, email) VALUES (?, ?, ?)";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, e.getNom());
                pstmt.setString(2, e.getPrenom());
                pstmt.setString(3, e.getEmail());
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) e.setId(rs.getInt(1));
                ConnexionBD.commitTransaction();
                logger.info("Student added: {} {}", e.getNom(), e.getPrenom());
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error adding student", ex);
            throw new DaoException("Erreur ajout étudiant", ex);
        }
    }

    public void modifier(Etudiant e) throws DaoException, SQLException {
        String sql = "UPDATE etudiant SET nom=?, prenom=?, email=? WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, e.getNom());
                pstmt.setString(2, e.getPrenom());
                pstmt.setString(3, e.getEmail());
                pstmt.setInt(4, e.getId());
                pstmt.executeUpdate();
                ConnexionBD.commitTransaction();
                logger.info("Student updated: {} {} (ID: {})", e.getNom(), e.getPrenom(), e.getId());
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error updating student", ex);
            throw new DaoException("Erreur modification étudiant", ex);
        }
    }

    public void supprimer(int id) throws DaoException, SQLException {
        String sql = "DELETE FROM etudiant WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                ConnexionBD.commitTransaction();
                logger.info("Student deleted with ID: {}", id);
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error deleting student", ex);
            throw new DaoException("Erreur suppression étudiant", ex);
        }
    }

    public List<Etudiant> listerTous() throws DaoException {
        List<Etudiant> list = new ArrayList<>();
        String sql = "SELECT * FROM etudiant ORDER BY nom, prenom";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Etudiant e = new Etudiant();
                    e.setId(rs.getInt("id"));
                    e.setNom(rs.getString("nom"));
                    e.setPrenom(rs.getString("prenom"));
                    e.setEmail(rs.getString("email"));
                    list.add(e);
                }
                logger.debug("Retrieved {} students", list.size());
            }
        } catch (SQLException ex) {
            logger.error("Error listing students", ex);
            throw new DaoException("Erreur liste étudiants", ex);
        }
        return list;
    }

    public Etudiant trouverParId(int id) throws DaoException {
        String sql = "SELECT * FROM etudiant WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Etudiant e = new Etudiant();
                    e.setId(rs.getInt("id"));
                    e.setNom(rs.getString("nom"));
                    e.setPrenom(rs.getString("prenom"));
                    e.setEmail(rs.getString("email"));
                    logger.debug("Found student with ID: {}", id);
                    return e;
                }
                return null;
            }
        } catch (SQLException ex) {
            logger.error("Error finding student by ID", ex);
            throw new DaoException("Erreur recherche étudiant", ex);
        }
    }
}