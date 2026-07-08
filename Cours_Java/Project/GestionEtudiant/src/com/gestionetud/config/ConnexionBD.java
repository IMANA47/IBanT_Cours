package com.gestionetud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestionnaire de connexion à la base de données (Singleton thread-safe).
 */
public class ConnexionBD {
    private static final Logger logger = LoggerFactory.getLogger(ConnexionBD.class);
    private static volatile Connection connection;

    private ConnexionBD() {
        // Empêche l'instanciation externe
    }

    /**
     * Retourne l'unique instance de la connexion JDBC.
     *
     * @return L'instance Connection active
     */
    public static Connection getInstance() {
        if (connection == null) {
            synchronized (ConnexionBD.class) {
                if (connection == null) {
                    try {
                        Properties prop = new Properties();
                        // Tentative de chargement depuis le dossier courant, sinon depuis le classpath
                        try (InputStream input = new FileInputStream("data.properties")) {
                            prop.load(input);
                            logger.debug("data.properties chargé depuis le système de fichiers.");
                        } catch (IOException e) {
                            try (InputStream input = ConnexionBD.class.getClassLoader().getResourceAsStream("data.properties")) {
                                if (input != null) {
                                    prop.load(input);
                                    logger.debug("data.properties chargé depuis le classpath.");
                                } else {
                                    throw new IOException("Fichier data.properties introuvable en local ou dans le classpath.");
                                }
                            }
                        }

                        String url = prop.getProperty("dburl");
                        String user = prop.getProperty("user");
                        String pass = prop.getProperty("password");

                        connection = DriverManager.getConnection(url, user, pass);
                        logger.info("Connexion établie avec la base de données : {}", url);
                    } catch (IOException | SQLException e) {
                        logger.error("Échec de l'initialisation de la connexion à la base de données.", e);
                        throw new RuntimeException("Impossible de se connecter à la base de données.", e);
                    }
                }
            }
        }
        return connection;
    }
}
