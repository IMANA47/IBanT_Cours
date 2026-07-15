package org.gstudent.service;

import org.gstudent.config.ConnexionBD;
import org.gstudent.entities.Utilisateur;
import org.gstudent.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UtilisateurService {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurService.class);

    public Utilisateur authentifier(String login, String password) throws DaoException {
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND password = ?";
        try {
            Connection conn = ConnexionBD.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, login);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Utilisateur u = new Utilisateur();
                    u.setId(rs.getInt("id"));
                    u.setLogin(rs.getString("login"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    logger.info("User authenticated: {} (role: {})", login, u.getRole());
                    return u;
                }
                logger.warn("Authentication failed for login: {}", login);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error during authentication", e);
            throw new DaoException("Erreur d'authentification", e);
        }
    }
}