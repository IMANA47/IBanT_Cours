package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Composer;
import org.gstudent.entities.Etudiant;
import org.gstudent.entities.Matiere;
import org.gstudent.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComposerService {

    private EtudiantService etudiantService = new EtudiantService();
    private MatiereService matiereService = new MatiereService();

    public void ajouter(Composer c) throws DaoException {
        String sql = "INSERT INTO composer (id_etudiant, id_matiere, note) VALUES (?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, c.getEtudiant().getId());
            pstmt.setInt(2, c.getMatiere().getId());
            pstmt.setDouble(3, c.getNote());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) c.setId(rs.getInt(1));
        } catch (SQLException ex) {
            throw new DaoException("Erreur ajout composer", ex);
        }
    }

    public void modifier(Composer c) throws DaoException {
        String sql = "UPDATE composer SET note=? WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, c.getNote());
            pstmt.setInt(2, c.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Erreur modification composer", ex);
        }
    }

    public void supprimer(int id) throws DaoException {
        String sql = "DELETE FROM composer WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Erreur suppression composer", ex);
        }
    }

    public List<Composer> listerTous() throws DaoException {
        List<Composer> list = new ArrayList<>();
        String sql = "SELECT * FROM composer";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
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
        } catch (SQLException ex) {
            throw new DaoException("Erreur liste composers", ex);
        }
        return list;
    }

    // Méthodes pour les statistiques
    public double moyenneGenerale() throws DaoException {
        String sql = "SELECT AVG(note) FROM composer";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
            return 0.0;
        } catch (SQLException ex) {
            throw new DaoException("Erreur moyenne générale", ex);
        }
    }

    public double moyenneParMatiere(int idMatiere) throws DaoException {
        String sql = "SELECT AVG(note) FROM composer WHERE id_matiere = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMatiere);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
            return 0.0;
        } catch (SQLException ex) {
            throw new DaoException("Erreur moyenne par matière", ex);
        }
    }

    public List<Object[]> notesParEtudiant() throws DaoException {
        // Retourne une liste d'objets [nomEtudiant, moyenne]
        List<Object[]> result = new ArrayList<>();
        String sql = """
                SELECT e.nom, e.prenom, AVG(c.note) AS moyenne
                FROM composer c JOIN etudiant e ON c.id_etudiant = e.id
                GROUP BY e.id
                ORDER BY moyenne DESC
                """;
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomComplet = rs.getString("nom") + " " + rs.getString("prenom");
                double moyenne = rs.getDouble("moyenne");
                result.add(new Object[]{nomComplet, moyenne});
            }
        } catch (SQLException ex) {
            throw new DaoException("Erreur notes par étudiant", ex);
        }
        return result;
    }
}