package controller;
import view*;
import model.*;
import service.*;
import java.util.List;
import java.time.LocalDate;
public class StaffMenuController extends BaseController {
    private ReservationService reservationService;
    private MealService mealService;
    public StaffMenuController(User user) {
        super(user);
        this.reservationService = new ReservationService();
        this.mealService = new MealService();
    }
    @Override
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            consoleUI.displayMessage("SERVICE STAFF");
            consoleUI.displayMessage("Connecté en tant que : " + currentUser.getFullName());
            String[] options = {
                "Voir réservations du jour",
                "Consulter le menu",
                "Voir statistiques",
                "Déconnexion"
            };
            consoleUI.displayMenu(options);
            int choice = consoleUI.getMenuChoice();
            if (choice == 4) {
                exit = true;
            }
            controlChoice(choice);
        }
    }
    @Override
    public void controlChoice(int choice) {
        switch (choice) {
            case 1:
                viewTodayReservations();
                break;
            case 2:
                viewMenu();
                break;
            case 3:
                viewStats();
                break;
            case 4:
                logout();
                break;
            default:
                consoleUI.displayError("Choix invalide");
        }
    }
    private void viewTodayReservations() {
        consoleUI.displayMessage("RÉSERVATIONS DU JOUR");
        LocalDate today = LocalDate.now();
        List<Reservation> reservations =
                reservationService.getReservationsByDate(today);
        if (reservations == null || reservations.isEmpty()) {
            consoleUI.displayMessage("Aucune réservation aujourd'hui.");
            return;
        }
        for (Reservation res : reservations) {
            consoleUI.displayMessage("- " + res.getUser().getFullName() +" (" + res.getNumberOfPeople() + " pers.) - " +res.getMeal().getName() +" à " + res.getTime());
        }
        consoleUI.displayMessage("Total : " + reservations.size() + " réservations");
    }
    private void viewMenu() {
        consoleUI.displayMessage("MENU DU JOUR");
        Meal mealOfTheDay = mealService.getMenu();
        if (mealOfTheDay == null) {
            consoleUI.displayMessage("Aucun menu disponible.");
            return;
        }
        consoleUI.displayMessage("Plat du jour : " + mealOfTheDay.getName());
    }
    private void viewStats() {
        consoleUI.displayMessage("STATISTIQUES");
        int totalReservations =reservationService.getAllReservations().size();
        int todayReservations =reservationService.getReservationsByDate(LocalDate.now()).size();
        int totalStudents = 0;
        for (User user : DataStore.getInstance().getAllUsers()) {
            if (user instanceof Student) {
                totalStudents++;
            }
        }
        consoleUI.displayMessage("Chiffres clés :");
        consoleUI.displayMessage("• Réservations totales : " + totalReservations);
        consoleUI.displayMessage("• Réservations aujourd'hui : " + todayReservations);
        consoleUI.displayMessage("• Étudiants inscrits : " + totalStudents);
    }
    private void logout() {
        consoleUI.displayMessage("Déconnexion réussie.");
    }
}
