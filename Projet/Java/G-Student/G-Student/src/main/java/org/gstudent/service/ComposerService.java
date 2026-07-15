package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Composer;
import org.gstudent.entities.Etudiant;
import org.gstudent.entities.Matiere;
import org.gstudent.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComposerService {
    private static final Logger logger = LoggerFactory.getLogger(ComposerService.class);

    private EtudiantService etudiantService = new EtudiantService();
    private MatiereService matiereService = new MatiereService();

    public void ajouter(Composer c) throws DaoException, SQLException {
        String sql = "INSERT INTO Composer (id_etudiant, id_matiere, note) VALUES (?, ?, ?)";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, c.getEtudiant().getId());
                pstmt.setInt(2, c.getMatiere().getId());
                pstmt.setDouble(3, c.getNote());
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) c.setId(rs.getInt(1));
                ConnexionBD.commitTransaction();
                logger.info("Grade added: student {}, subject {}, note {}", 
                    c.getEtudiant().getNom(), c.getMatiere().getNom(), c.getNote());
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error adding grade", ex);
            throw new DaoException("Erreur ajout Composer", ex);
        }
    }

    public void modifier(Composer c) throws DaoException, SQLException {
        String sql = "UPDATE Composer SET note=? WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, c.getNote());
                pstmt.setInt(2, c.getId());
                pstmt.executeUpdate();
                ConnexionBD.commitTransaction();
                logger.info("Grade updated: ID {}, new note {}", c.getId(), c.getNote());
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error updating grade", ex);
            throw new DaoException("Erreur modification Composer", ex);
        }
    }

    public void supprimer(int id) throws DaoException, SQLException {
        String sql = "DELETE FROM Composer WHERE id=?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                ConnexionBD.commitTransaction();
                logger.info("Grade deleted with ID: {}", id);
            }
        } catch (SQLException ex) {
            ConnexionBD.rollbackTransaction();
            logger.error("Error deleting grade", ex);
            throw new DaoException("Erreur suppression Composer", ex);
        }
    }

    public List<Composer> listerTous() throws DaoException {
        List<Composer> list = new ArrayList<>();
        String sql = "SELECT * FROM Composer";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Composer c = new Composer();
                    c.setId(rs.getInt("id"));
                    int idEtu = rs.getInt("id_etudiant");
                    int idMat = rs.getInt("id_matiere");
                    c.setEtudiant(etudiantService.trouverParId(idEtu));
                    c.setMatiere(matiereService.trouverParId(idMat));
                    c.setNote(rs.getDouble("note"));
                    list.add(c);
                }
                logger.debug("Retrieved {} grades", list.size());
            }
        } catch (SQLException ex) {
            logger.error("Error listing grades", ex);
            throw new DaoException("Erreur liste composers", ex);
        }
        return list;
    }

    public double moyenneGenerale() throws DaoException {
        String sql = "SELECT AVG(note) FROM Composer";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    double avg = rs.getDouble(1);
                    logger.debug("General average: {}", avg);
                    return avg;
                }
                return 0.0;
            }
        } catch (SQLException ex) {
            logger.error("Error calculating general average", ex);
            throw new DaoException("Erreur moyenne générale", ex);
        }
    }

    public double moyenneParMatiere(int idMatiere) throws DaoException {
        String sql = "SELECT AVG(note) FROM Composer WHERE id_matiere = ?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idMatiere);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    double avg = rs.getDouble(1);
                    logger.debug("Average for subject {}: {}", idMatiere, avg);
                    return avg;
                }
                return 0.0;
            }
        } catch (SQLException ex) {
            logger.error("Error calculating average by subject", ex);
            throw new DaoException("Erreur moyenne par matière", ex);
        }
    }

    public List<Object[]> notesParEtudiant() throws DaoException {
        List<Object[]> result = new ArrayList<>();
        String sql = """
                SELECT e.nom, e.prenom, AVG(c.note) AS moyenne
                FROM Composer c JOIN etudiant e ON c.id_etudiant = e.id
                GROUP BY e.id
                ORDER BY moyenne DESC
                """;
        try {
            Connection conn = ConnexionBD.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String nomComplet = rs.getString("nom") + " " + rs.getString("prenom");
                    double moyenne = rs.getDouble("moyenne");
                    result.add(new Object[]{nomComplet, moyenne});
                }
                logger.debug("Retrieved grades for {} students", result.size());
            }
        } catch (SQLException ex) {
            logger.error("Error retrieving grades by student", ex);
            throw new DaoException("Erreur notes par étudiant", ex);
        }
        return result;
    }
}