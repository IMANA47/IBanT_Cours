package org.filatb.dao;


import org.filatb.modele.Client;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientServiDAO {
    void enregistrerClientServi(Client client, LocalDateTime heurePrise, String guichet);
    int obtenirDernierNumeroTicket();
    int obtenirTotalClientsServis();
    double calculerTempsAttenteMoyen();
    List<Client> listerHistorique(); // optionnel
}
