package controller;
import model.*;
import service.AuthentificationService;
import view.ConsoleUI;
public class MainController {
    private AuthentificationService authService;
    private ConsoleUI consoleUI;
    private User currentUser;
    public MainController() {
        this.authService = new AuthentificationService();
        this.consoleUI = new ConsoleUI();
    }
    public void startApplication() {
        consoleUI.displayWelcome();
        while (true) {
            int choice = consoleUI.displayMainMenu();
            switch (choice) {
                case 1:
                    controlLogin();
                    break;
                case 2:
                    controlRegistration();
                    break;
                case 3:
                    consoleUI.displayMessage("Au revoir !");
                    System.exit(0);
                    break;
                default:
                    consoleUI.displayError("Choix invalide !");
            }
        }
    }
    private void controlLogin() {
        String login = consoleUI.prompt("Nom d'utilisateur : ");
        String password = consoleUI.promptPassword("Mot de passe : ");
        currentUser = authService.authenticate(login, password);
        if (currentUser == null) {
            consoleUI.displayError("Échec d'authentification !");
            return;
        }
		showUserMenu();
    }
    private void showUserMenu() {
        String role = currentUser.getRole();
        switch (role.toUpperCase()) {
            case "ETUDIANT":
                new StudentMenuController(currentUser).showTab();
                break;
            case "ADMIN":
                new AdminMenuController(currentUser).showTab();
                break;
            case "STAFF":
                new StaffMenuController(currentUser).showTab();
                break;

            default:
                consoleUI.displayError("Rôle inconnu !");
        }
    }
    private void controlRegistration() {
        consoleUI.displayMessage("Inscription");
    }
}

