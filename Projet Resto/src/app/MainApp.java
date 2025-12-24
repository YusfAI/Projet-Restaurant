package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.DataStore;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialiser les données
            DataStore.initializeData();
            
            // Charger l'interface de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Login.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/view/css/style.css").toExternalForm());
            
            primaryStage.setTitle("Restaurant Universitaire IHEc - Système de Réservation");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(600);
            
            // Ajouter une icône (si vous avez un fichier icon.png dans resources)
            try {
                Image icon = new Image(getClass().getResourceAsStream("/view/images/icon.png"));
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.out.println("Icône non trouvée, utilisation de l'icône par défaut.");
            }
            
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog(e);
        }
    }
    
    private void showErrorDialog(Exception e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR
        );
        alert.setTitle("Erreur d'initialisation");
        alert.setHeaderText("Impossible de démarrer l'application");
        alert.setContentText("Erreur : " + e.getMessage());
        alert.showAndWait();
    }
    
    @Override
    public void stop() {
        // Sauvegarder les données avant la fermeture
        System.out.println("Application fermée. Nettoyage des ressources...");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
