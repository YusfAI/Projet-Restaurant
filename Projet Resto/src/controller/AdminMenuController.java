package controller;
import model.*;
import service.*;
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
        this.adminView = new AdminView();
        this.userService = new UserService();
        this.reservationService = new ReservationService();
        this.diningHallService = new DiningHallService();
        this.dataStore = DataStore.getInstance();
    }
    @Override
    public void showMenu() {
        adminView.displayAdminView();
        int choice = consoleUI.getMenuChoice();
        String[] options = {
            "Gérer les utilisateurs",
            "Voir les statistiques",
            "Gérer restaurant",
            "Générer des rapports",
            "Déconnexion"
        };
        controlAdminChoice(choice);
    }
    private void controlAdminChoice(int choice) {
        switch (choice) {
            case 1:
                manageUsers();
                break;
            case 2:
                viewStatistics();
                break;
            case 3:
                manageDiningHalls();
                break;
            case 4:
                generateReports();
                break;
            case 5:
                logOut();
                break;
            default:
                consoleUI.displayError("Choix invalide");
        }
    }
    private void manageUsers() {
        String[] userOptions = {
            "Voir tous les utilisateurs",
            "Ajouter un utilisateur",
            "Modifier un utilisateur",
            "Supprimer un utilisateur",
            "Retour"
        };
        consoleUI.displayMenu(userOptions);
        int userChoice = consoleUI.getMenuChoice();
        switch (userChoice) {
            case 1:
                showAllUsers();
                break;
            case 2:
                addUser();
                break;
            case 3:
                updateUser();
                break;
            case 4:
                deleteUser();
                break;
            case 5:
                return;
            default:
                consoleUI.displayError("Choix invalide");
        }
    }
    private void showAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        adminView.displayUserList(allUsers);

        for (User user : allUsers) {
            consoleUI.displayMessage(
                "CIN: " + user.getCIN() + " | Nom: " + user.getNom() +" | Role: " + user.getRole() + " | Email: " + user.getEmail());
        }
    }
    private void addUser() {
        String role = consoleUI.getInput("Role: ");
        String nom = consoleUI.getInput("Nom: ");
        String email = consoleUI.getInput("Email: ");
        String password = consoleUI.getInput("Password: ");
        userService.addUser(role, nom, email, password);
        consoleUI.displayMessage("Utilisateur ajouté avec succès !");
    }
    private void updateUser() {
        String cin = consoleUI.getInput("CIN de l'utilisateur à modifier: ");
        User user = dataStore.getUserById(cin);
        if (user == null) {
            consoleUI.displayError("Utilisateur non trouvé !");
            return;
        }
        String newEmail = consoleUI.getInput("Nouveau email [" + user.getEmail() + "] : ");
        user.setEmail(newEmail);
        consoleUI.displayMessage("Modification faite !");
    }
    private void deleteUser() {
        String cin = consoleUI.getInput("CIN de l'utilisateur à supprimer: ");
        User user = dataStore.getUserById(cin);
        if (user == null) {
            consoleUI.displayError("Utilisateur non trouvé !");
            return;
        }
        String rep = consoleUI.getInput("Supprimer " + user.getNom() + " ? (oui/non): ");

        if (rep.equalsIgnoreCase("oui")) {
            userService.deleteUser(cin);
            consoleUI.displayMessage("Utilisateur supprimé !");
        } else {
            consoleUI.displayMessage("Suppression annulée.");
        }
    }
    public void viewStatistics() {
        List<Reservation> res = reservationService.getAllReservations();
        int total = res.size();
        int today = reservationService.getTodayReservations().size();

        consoleUI.displayMessage("Réservations totales : " + total);
        consoleUI.displayMessage("Aujourd'hui : " + today);
    }
    private void manageDiningHalls() {
        String[] options = {
            "Voir capacité",
            "Ajuster capacité",
            "Retour"
        };
        consoleUI.displayMenu(options);
        int choix = consoleUI.getMenuChoice();
        DiningHall hall = diningHallService.getMainHall(); // correction
        switch (choix) {
            case 1:
                showHallStatus(hall);
                break;
            case 2:
                checkCapacite(hall);
                break;
            case 3:
                return;
            default:
                consoleUI.displayError("Choix invalide");
        }
    }
    private void showHallStatus(DiningHall hall) {
        consoleUI.displayMessage("Capacité : " + hall.getCapacite());
    }
    private void checkCapacite(DiningHall hall) {
        int totalRes = reservationService.getAllReservations().size();
        int diff = hall.getCapacite() - totalRes;
        consoleUI.displayMessage("Capacité salle : " + hall.getCapacite());
        consoleUI.displayMessage("Réservations : " + totalRes);
        if (diff < 0) {
            int needChairs = Math.abs(diff);
            int needTables = (needChairs / 4) + 1;
            consoleUI.displayMessage(
                "Ajoutez " + needChairs + " chaises et " + needTables + " tables.");
        }
    }
    private void generateReports() {
        int reservations = reservationService.getTodayReservations().size();
        int staff = 0;
        for (User user : dataStore.getAllUsers()) {
            if (user instanceof Staff) staff++;
        }
        consoleUI.displayMessage("RAPPORT DU JOUR");
        consoleUI.displayMessage("Date : " + java.time.LocalDate.now());
        consoleUI.displayMessage("Réservations : " + reservations);
        consoleUI.displayMessage("Staff : " + staff);
        consoleUI.displayMessage("Menu du jour : Plat du jour");
    }
    private void logOut() {
        consoleUI.displayMessage("Déconnexion réussie. À bientôt " + currentUser.getNom() + " !");
    }
}
