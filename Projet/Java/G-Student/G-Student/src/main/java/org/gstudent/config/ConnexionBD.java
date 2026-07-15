package org.gstudent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBD {
    private static final Logger logger = LoggerFactory.getLogger(ConnexionBD.class);
    
    private static final String URL = "jdbc:h2:./data/gstudent_db;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE;MODE=MySQL";
    private static final String USER = "root";
    private static final String PASSWORD = "root123#";

    private static Connection connection = null;
    private static boolean tablesInitialized = false;

    private ConnexionBD() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            logger.info("Creating new database connection");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            if (!tablesInitialized) {
                initTables();
                tablesInitialized = true;
            }
        }
        return connection;
    }

    public static void commitTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            logger.debug("Transaction committed");
        }
    }

    public static void rollbackTransaction() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.rollback();
                    logger.warn("Transaction rolled back");
                }
            } catch (SQLException e) {
                logger.error("Error rolling back transaction", e);
            }
        }
    }

    private static void initTables() {
        logger.info("Initializing database tables");
        
        String createUtilisateur = "CREATE TABLE IF NOT EXISTS utilisateur (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    login VARCHAR(50) UNIQUE NOT NULL," +
                "    password VARCHAR(100) NOT NULL," +
                "    role VARCHAR(20) NOT NULL" +
                ");";
        String createEtudiant = "CREATE TABLE IF NOT EXISTS etudiant (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    nom VARCHAR(50) NOT NULL," +
                "    prenom VARCHAR(50) NOT NULL," +
                "    email VARCHAR(100) UNIQUE NOT NULL" +
                ");";
        String createMatiere = "CREATE TABLE IF NOT EXISTS matiere (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    nom VARCHAR(50) UNIQUE NOT NULL," +
                "    coefficient DECIMAL(4,2) CHECK (coefficient >= 0)" +
                ");";
        String createComposer = "CREATE TABLE IF NOT EXISTS Composer (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    id_etudiant INT NOT NULL," +
                "    id_matiere INT NOT NULL," +
                "    note DECIMAL(4,2) CHECK (note BETWEEN 0 AND 20)," +
                "    FOREIGN KEY (id_etudiant) REFERENCES etudiant(id) ON DELETE CASCADE," +
                "    FOREIGN KEY (id_matiere) REFERENCES matiere(id) ON DELETE CASCADE," +
                "    UNIQUE(id_etudiant, id_matiere)" +
                ");";

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUtilisateur);
            stmt.execute(createEtudiant);
            stmt.execute(createMatiere);
            stmt.execute(createComposer);
            connection.commit();
            
            // Insertion d'un utilisateur par défaut (admin/admin)
            stmt.executeUpdate("MERGE INTO utilisateur (login, password, role) KEY(login) VALUES ('admin', 'admin', 'ADMIN')");
            connection.commit();
            
            logger.info("Database tables initialized successfully");
        } catch (SQLException e) {
            logger.error("Error initializing database tables", e);
            rollbackTransaction();
        }
    }

    public static void startWebServer() {
        try {
            org.h2.tools.Server server = org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
            server.start();
            logger.info("H2 Web Console started at: http://localhost:8082");
            logger.info("JDBC URL: jdbc:h2:./data/gstudent_db");
            logger.info("Username: root");
            logger.info("Password: root123#");
        } catch (SQLException e) {
            logger.error("Error starting H2 web server", e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.info("Database connection closed");
                }
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }
}