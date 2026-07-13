package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Utilisateur;
import org.gstudent.exception.DaoException;

import java.sql.*;

public class UtilisateurService {

    public Utilisateur authentifier(String login, String password) throws DaoException {
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND password = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setLogin(rs.getString("login"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Erreur d'authentification", e);
        }
    }
}