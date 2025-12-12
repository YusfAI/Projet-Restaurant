package Controller;
import model.*;
import service.UserService;
import view.*;
import java.util.List;
public class AdminMenuController extends BaseController {
	private AdminView adminView;
	private UserService userService;
    private ReservationService reservationService;
    private DiningHallService diningHallService;
    private DataStore dataStore;
	public AdminMenuController(User user) {
		super(user);
		this.adminView=new AdminView();
		this.userService=new UserService();
        this.reservationService = new ReservationService();
        this.diningHallService = new DiningHallService();
        this.dataStore = DataStore.getInstance();
	}
	@override 
	public void showMenu() {
		adminView.displayAdminView();
		int choice=consoleUI.getMenuChoice();
		String[] options = {
	            "Gérer les utilisateurs",  
	            "Voir les statistiques", 
	            "Gérer restaurant",
	            "Générer des rapports",          
	            "Déconnexion"};
		controlAdminChoice(choice);
	}
	private void controlAdminChoice(int choice) {
		switch(choice) {
		case 1: manageUser(); break;
		case 2: viewStatistics(); break;
		case 3: manageDiningHalls(); break;
		case 4:	generateReports(); break;
		case 5: logOut(); break;
		default: consoleUI.displayError("choix invalide");
		}
	}
	private void manageUser() {
		String[] userOptions = {
	            "Voir tous les utilisateurs",
	            "Ajouter un utilisateur",
	            "Modifier un utilisateur",
	            "Supprimer un utilisateur",
	            "Retour"
	        };
		int userChoice=consoleUI.getMenuChoice();
		switch(userChoice) {
	        case 1: showAllUsers(); break;
	        case 2: addUser(); break;
	        case 3: updateUser(); break;
	        case 4: deleteUser(); break;
	        case 5: return;
	        default: consoleUI.displayError("Choix invalide");
		}
		private showAllUsers();{
			List<User>allUsers=userService.getAllUsers();
			admin.View.displayUserList(allUsers);
			for(User user:allUsers) {
				consoleUI.displayMessage("CIN:"+user.getCIN()+ "| Nom:"+user.getNom() + "| Role: " +user.getRole()+ "| Email:" + user.getEmail());
		            ));
			}
		}
		private void addUser() {
	        String type = consoleUI.getInput("Role: ");
	        String nom = consoleUI.getInput("Nom: ");
	        String email = consoleUI.getInput("Email: ");
	        String password = consoleUI.getInput("Password: ");
	        
	        consoleUI.displayMessage("Utilisateur ajouté avec succès!");
		}
		private void updateUser() {
	        String CIN = consoleUI.getInput("CIN de l'utilisateur à modifier: ");
	        User user = DataStore.getInstance().getUserById(userId);
	        if (user == null) {
	            consoleUI.displayError("Utilisateur non trouvé!");
	            return;
	        }
	        String nouveauEmail = consoleUI.getInput("Nouveau email[" + user.getEmail() + "]: ");
	        consoleUI.displayMessage("Modification simulée pour " + user.getEmail());
		}
		private void deleteUser() {
			String CIN = consoleUI.getInput("CIN de l'utilisateur à modifier: ");
	        User user = DataStore.getInstance().getUserById(userId);
	        if (user == null) {
	            consoleUI.displayError("Utilisateur non trouvé!");
	            return;
	        }
	        String reponse = consoleUI.getInput("Supprimer " + user.getCIN() + "? (oui/non): ");
	        if (reponse.equalsIgnoreCase("oui")) {
	            List<User> allUsers = DataStore.getInstance().getAllUsers();
	            for (int i = 0; i < allUsers; i++) {
	                if (users.get(i).getId().equals(userId)) {
	                    users.remove(i);
	                    consoleUI.displayMessage("Utilisateur supprimé!");
	                    return;
	                }
	            }
	        }
	        else {
	            consoleUI.displayMessage("Annulé.");
	        }
		}
		public void viewStatistics() {
	        List<Reservation> reservations = reservationService.getAllReservations();
	        int totalReservations = reservations.size();
	        int todayReservations = reservationService.getTodayReservations().size();
	        
	        consoleUI.displayMessage("RÉSERVATIONS:");
	        consoleUI.displayMessage("  Total: " + totalReservations);
	        consoleUI.displayMessage("  Aujourd'hui: " + todayReservations);
		}
		private void manageDiningHalls() {
		    String[] options = {
		        "Gestion de capacité",
		        "Ajuster capacité (tables/chaises)",
		        "Retour"
		    };
		    
		    consoleUI.displayMenu(options);
		    int choix = consoleUI.getMenuChoice();
		    
		    switch(choix) {
	        case 1: 
	        	showHallStatus(hall); 
	        break;
	        case 2: 
	        	checkCapacite(hall); 
	        break;
	        case 3: return;
	        default: consoleUI.displayError("Choix invalide");
	    }
		}
		private void showHallStatus(DiningHall hall) {
		    consoleUI.displayMessage("ÉTAT DE LA SALLE:");
		    consoleUI.displayMessage("Capacité: " + hall.getCapacite() + " places");
		}
		private void checkCapacite(DiningHall hall) {
		    int totalReservations = reservationService.getAllReservations().size();
		    consoleUI.displayMessage("Réservations totales: " + totalReservations);
		    consoleUI.displayMessage("Capacité salle: " + hall.getCapacite());
		    int difference = hall.getCapacite() - totalReservations;
		    if (difference < 0) {
		        consoleUI.displayMessage("Ajoutez" + Math.abs(difference) + "chaises et "+ ((Math.abs(difference)/4)+1) +"Tables");
		    }
		}
		private void generateReports() {
		    consoleUI.displayMessage("RAPPORT DU JOUR");
		    int reservations = reservationService.getTodayReservations().size();
		    int staff = 0;
		    for (User user : DataStore.getInstance().getAllUsers()) {
		        if (user instanceof Staff) 
		        	staff++;
		    }
		    consoleUI.displayMessage("Date:" + java.time.LocalDate.now());
		    consoleUI.displayMessage("");
		    consoleUI.displayMessage("Réservations: " + reservations);
		    consoleUI.displayMessage("Personnes: " + personnes);
		    consoleUI.displayMessage("Staff: " + staff);
		    consoleUI.displayMessage("");
		    consoleUI.displayMessage("Menu:");
		    consoleUI.displayMessage("  • Déjeuner: Plat du jour");
		    consoleUI.displayMessage("");
		    consoleUI.displayMessage("Salle: " + getDiningHall().getNom());
		}
		private void logOut() {
			consoleUI.displayMessage("Déconnexion réussie. À bientôt " +currentUser.getNom() + "!");
			System.exit(0);
		}
}
