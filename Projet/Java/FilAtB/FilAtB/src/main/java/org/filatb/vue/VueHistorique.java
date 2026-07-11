package org.filatb.vue;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.filatb.modele.ClientServiDTO;

import java.util.List;

public class VueHistorique {

    public static void afficher(List<ClientServiDTO> historique) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Historique des clients servis");

        TableView<ClientServiDTO> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<ClientServiDTO, Integer> colTicket = new TableColumn<>("Ticket");
        colTicket.setCellValueFactory(new PropertyValueFactory<>("numeroTicket"));

        TableColumn<ClientServiDTO, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<ClientServiDTO, String> colMotif = new TableColumn<>("Motif");
        colMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));

        TableColumn<ClientServiDTO, String> colArrivee = new TableColumn<>("Arrivée");
        colArrivee.setCellValueFactory(new PropertyValueFactory<>("heureArriveeFormatee"));

        TableColumn<ClientServiDTO, String> colPrise = new TableColumn<>("Prise en charge");
        colPrise.setCellValueFactory(new PropertyValueFactory<>("heurePriseFormatee"));

        TableColumn<ClientServiDTO, String> colGuichet = new TableColumn<>("Guichet");
        colGuichet.setCellValueFactory(new PropertyValueFactory<>("guichet"));

        table.getColumns().addAll(colTicket, colNom, colMotif, colArrivee, colPrise, colGuichet);
        table.setItems(FXCollections.observableArrayList(historique));

        VBox root = new VBox(table);
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }
}