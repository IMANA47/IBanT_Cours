package org.gstudent.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.gstudent.exception.DaoException;
import org.gstudent.service.ComposerService;
import org.gstudent.service.MatiereService;

import java.util.List;

public class StatsController {

    @FXML private Label lblMoyenneGenerale;
    @FXML private Label lblMoyenneParMatiere;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    private ComposerService ComposerService = new ComposerService();
    private MatiereService matiereService = new MatiereService();

    @FXML
    public void initialize() {
        // On pourrait appeler chargerStatistiques() depuis le main, ou le faire ici
        // Mais on le fera via un rafraîchissement automatique ou bouton.
        chargerStatistiques();
    }

    public void chargerStatistiques() {
        try {
            // Moyenne générale
            double moyGen = ComposerService.moyenneGenerale();
            lblMoyenneGenerale.setText(String.format("%.2f / 20", moyGen));

            // Affichage d'un exemple de moyenne par matière (on prend la première matière pour simplifier)
            // Idéalement, on pourrait avoir un tableau ou un graphique avec toutes les matières.
            var matieres = matiereService.listerTous();
            if (!matieres.isEmpty()) {
                double moyMat = ComposerService.moyenneParMatiere(matieres.get(0).getId());
                lblMoyenneParMatiere.setText(matieres.get(0).getNom() + " : " + String.format("%.2f", moyMat));
            }

            // Graphique : répartition des notes (tranches)
            List<Object[]> notesParEtu = ComposerService.notesParEtudiant();
            int[] tranches = new int[4]; // 0-5, 5-10, 10-15, 15-20
            for (Object[] obj : notesParEtu) {
                double note = (double) obj[1];
                if (note < 5) tranches[0]++;
                else if (note < 10) tranches[1]++;
                else if (note < 15) tranches[2]++;
                else tranches[3]++;
            }

            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Effectif");
            series.getData().add(new XYChart.Data<>("0-4.99", tranches[0]));
            series.getData().add(new XYChart.Data<>("5-9.99", tranches[1]));
            series.getData().add(new XYChart.Data<>("10-14.99", tranches[2]));
            series.getData().add(new XYChart.Data<>("15-20", tranches[3]));
            barChart.getData().add(series);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}