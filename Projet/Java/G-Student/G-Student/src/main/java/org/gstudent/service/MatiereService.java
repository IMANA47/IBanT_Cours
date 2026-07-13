package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Matiere;
import org.gstudent.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereService {

    public void ajouter(Matiere m) throws DaoException {
        String sql = "INSERT INTO matiere (nom, coefficient) VALUES (?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, m.getNom());
            pstmt.setDouble(2, m.getCoefficient());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) m.setId(rs.getInt(1));
        } catch (SQLException ex) {
            throw new DaoException("Erreur ajout matière", ex);
        }
    }

    public void modifier(Matiere m) throws DaoException {
        String sql = "UPDATE matiere SET nom=?, coefficient=? WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, m.getNom());
            pstmt.setDouble(2, m.getCoefficient());
            pstmt.setInt(3, m.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Erreur modification matière", ex);
        }
    }

    public void supprimer(int id) throws DaoException {
        String sql = "DELETE FROM matiere WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Erreur suppression matière", ex);
        }
    }

    public List<Matiere> listerTous() throws DaoException {
        List<Matiere> list = new ArrayList<>();
        String sql = "SELECT * FROM matiere ORDER BY nom";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Matiere m = new Matiere();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setCoefficient(rs.getDouble("coefficient"));
                list.add(m);
            }
        } catch (SQLException ex) {
            throw new DaoException("Erreur liste matières", ex);
        }
        return list;
    }

    public Matiere trouverParId(int id) throws DaoException {
        String sql = "SELECT * FROM matiere WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Matiere m = new Matiere();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setCoefficient(rs.getDouble("coefficient"));
                return m;
            }
            return null;
        } catch (SQLException ex) {
            throw new DaoException("Erreur recherche matière", ex);
        }
    }
}