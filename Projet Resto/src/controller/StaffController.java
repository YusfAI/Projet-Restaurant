package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import service.ServiceRestaurant;
import model.Menu;

public class StaffController {

    @FXML private Label lblTotalReservations;
    @FXML private Label lblNombreStaff;
    @FXML private TextArea txtMenuSemaine;

    @FXML
    void afficherDashboard() {
        // Nombre total de réservations
        lblTotalReservations.setText("Nombre de réservations : " + ServiceRestaurant.getNombreReservations());

        // Nombre de staff
        lblNombreStaff.setText("Nombre de staff : " + ServiceRestaurant.getNombreStaff());

        // Menu de la semaine
        StringBuilder sb = new StringBuilder();
        for (Menu m : ServiceRestaurant.getMenus()) {
            sb.append(m.getDate()).append(" : ").append(m.getRepas()).append("\n");
        }
        txtMenuSemaine.setText(sb.toString());
    }
}
