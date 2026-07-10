package org.filatb.dao;

import org.filatb.modele.Client;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ClientServiDAOImpl implements ClientServiDAO {

    @Override
    public void enregistrerClientServi(Client client, LocalDateTime heurePrise, String guichet) {
        String sql = "INSERT INTO client_servi (numero_ticket, nom, motif, priorite, heure_arrivee, heure_prise_en_charge, guichet) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, client.getNumeroTicket());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getMotif());
            pstmt.setBoolean(4, client.isPrioritaire());
            pstmt.setTimestamp(5, Timestamp.valueOf(client.getHeureArrivee().atDate(LocalDateTime.now().toLocalDate())));
            pstmt.setTimestamp(6, Timestamp.valueOf(heurePrise));
            pstmt.setString(7, guichet);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement du client servi", e);
        }
    }

    @Override
    public int obtenirDernierNumeroTicket() {
        String sql = "SELECT COALESCE(MAX(numero_ticket), 0) FROM client_servi";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int obtenirTotalClientsServis() {
        String sql = "SELECT COUNT(*) FROM client_servi";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double calculerTempsAttenteMoyen() {
        String sql = "SELECT AVG(TIMESTAMPDIFF(SECOND, heure_arrivee, heure_prise_en_charge)) FROM client_servi";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Client> listerHistorique() {
        // Implémentez si besoin
        return new ArrayList<>();
    }
}