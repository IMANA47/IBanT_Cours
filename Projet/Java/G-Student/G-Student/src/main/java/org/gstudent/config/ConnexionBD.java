package org.gstudent.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBD {
    private static final String URL = "jdbc:h2:~/gstudent_db;DB_CLOSE_DELAY=-1";
    private static final String USER = "root";
    private static final String PASSWORD = "root123#";

    private static Connection connection = null;

    private ConnexionBD() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            initTables();
        }
        return connection;
    }

    private static void initTables() {
        String createUtilisateur = """
                CREATE TABLE IF NOT EXISTS utilisateur (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    login VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(100) NOT NULL,
                    role VARCHAR(20) NOT NULL
                );
                """;
        String createEtudiant = """
                CREATE TABLE IF NOT EXISTS etudiant (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nom VARCHAR(50) NOT NULL,
                    prenom VARCHAR(50) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL
                );
                """;
        String createMatiere = """
                CREATE TABLE IF NOT EXISTS matiere (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nom VARCHAR(50) UNIQUE NOT NULL,
                    coefficient DECIMAL(4,2) CHECK (coefficient >= 0)
                );
                """;
        String createcomposer = """
                CREATE TABLE IF NOT EXISTS composer (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    id_etudiant INT NOT NULL,
                    id_matiere INT NOT NULL,
                    note DECIMAL(4,2) CHECK (note BETWEEN 0 AND 20),
                    FOREIGN KEY (id_etudiant) REFERENCES etudiant(id) ON DELETE CASCADE,
                    FOREIGN KEY (id_matiere) REFERENCES matiere(id) ON DELETE CASCADE,
                    UNIQUE(id_etudiant, id_matiere)
                );
                """;

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUtilisateur);
            stmt.execute(createEtudiant);
            stmt.execute(createMatiere);
            stmt.execute(createcomposer);
            // Insertion d'un utilisateur par défaut (admin/admin)
            stmt.executeUpdate("MERGE INTO utilisateur (login, password, role) KEY(login) VALUES ('admin', 'admin', 'ADMIN')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}