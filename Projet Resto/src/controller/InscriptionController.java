package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Etudiant;
import service.ServiceRestaurant;
import exception.ChampsVideException;
import exception.EtudiantDejaInscritException;
import exception.SallePleineException;

import java.time.LocalDate;

public class InscriptionController {

    @FXML private TextField txtCin;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private Label lblMessage;
    @FXML private TextArea txtMenuSemaineEtudiant;

    @FXML
    void inscrire() {
        Etudiant e = new Etudiant(txtCin.getText(), txtNom.getText(), txtPrenom.getText());
        try {
            ServiceRestaurant.inscrireEtudiant(e);
            LocalDate demain = LocalDate.now().plusDays(1);
            lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblMessage.setText("Inscription validée pour le " + demain);

            txtCin.clear();
            txtNom.clear();
            txtPrenom.clear();
        } catch (ChampsVideException | EtudiantDejaInscritException ex){
            lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            lblMessage.setText(((Throwable) ex).getMessage());
        } catch (SallePleineException ex) {
            lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            lblMessage.setText(((Throwable) ex).getMessage() + " (ajouter " + ex.placesManquantes() + " chaise(s))");
        }
    }

    @FXML
    void afficherMenu() {
        StringBuilder sb = new StringBuilder();
        ServiceRestaurant.getMenus().forEach(menu -> 
            sb.append(menu.getDate()).append(" : ").append(menu.getRepas()).append("\n")
        );
        txtMenuSemaineEtudiant.setText(sb.toString());
    }
}

