package com.gestionetud.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionBD {
    private static Connection con;
    private static String url;
    private static String user;
    private static String pass;

    public  static Connection getInstance() {
        if (con == null)  {
            try {
                //Recuperer les parametres du fichier
                Properties prop = new Properties();
                try (InputStream stream = ConnexionBD.class.getResourceAsStream("/com/gestionetud/data.properties")) {
                    if (stream == null) {
                        throw new IOException("data.properties resource not found");
                    }
                    prop.load(stream);
                }
                url = prop.getProperty("dburl");
                user = prop.getProperty("user");
                pass = prop.getProperty("password");
                con = DriverManager.getConnection(url,user,pass);
            }catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            System.out.println("connection established " + url);


        }
        return con;
    }
}
