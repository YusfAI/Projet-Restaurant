package Controller;
import model.*;
import servie.AuthentificationService;
import exceptions.*;
import view.ConsoleUI;
public class MainController {
	private AuthentificationService authService;
	private ConsoleUI consoleUI;
	private User currentUser;
	public MainController() {
		this.authService=new AuthentificationService();
		this.consoleUI=new ConsoleUI
	}
	public void startApplication() {
		consoleUI.displayWelcome();
		while(true) {
			int choice=consoleUI.displayMainMenu();
			switch (choice) {
			case 1: controlLogin(); break;
			case 2: controlRegistration(); break;
			case 3: consoleUI.displayMessage("Au revoir!"); System.exit(0); break;
			default : consoleUI.displayError("choix invalide");
			}
		}
	}
	private void controlLogin() {
		String nom=consoleUI.prompt("Nom d'utilisateur:");
		String password=consoleUI.promptPassword("Mot de passe:");
		currentUser=authService.authentificate(nom,password);
		if (currentUser==null) {
			consoleUI.displayError("Echec d'authentification");
			return;
		}
		roleController();
	}
	private void roleController() {
		String role=currentUser.getRole();
		switch(role) {
			case "ETUDIANT": StudentMenuController studentControl=new StudentMenuController(currentUser); studentControl.showTab();break;
			case "ADMIN": AdminMenuController adminControl=new AdminMenuController(currentUser); adminControl.showTab(); break;
			case "STAFF" :StaffMenuController staffControl=new StaffMenuController(currentUser); staffControl.showTab(); break;
			default: consoleUI.displayError("Role inconnu");
		}
	}
	private void controlRegistration() {
		consoleUI.displayMessage("Inscription");
	}
}

