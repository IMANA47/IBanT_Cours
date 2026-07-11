package org.filatb.vue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public static void afficherHistorique(List<ClientServiDTO> historique) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Historique des clients servis");

        TableView<ClientServiDTO> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ClientServiDTO, Integer> colNum = new TableColumn<>("Ticket");
        colNum.setCellValueFactory(new PropertyValueFactory<>("numeroTicket"));

        TableColumn<ClientServiDTO, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<ClientServiDTO, String> colMotif = new TableColumn<>("Motif");
        colMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));

        TableColumn<ClientServiDTO, String> colArrivee = new TableColumn<>("Arrivée");
        colArrivee.setCellValueFactory(new PropertyValueFactory<>("heureArrivee"));

        TableColumn<ClientServiDTO, String> colPrise = new TableColumn<>("Prise en charge");
        colPrise.setCellValueFactory(new PropertyValueFactory<>("heurePriseEnCharge"));

        TableColumn<ClientServiDTO, String> colGuichet = new TableColumn<>("Guichet");
        colGuichet.setCellValueFactory(new PropertyValueFactory<>("guichet"));

        table.getColumns().addAll(colNum, colNom, colMotif, colArrivee, colPrise, colGuichet);

        ObservableList<ClientServiDTO> data = FXCollections.observableArrayList(historique);
        table.setItems(data);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }
}