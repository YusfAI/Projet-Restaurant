package controller;
import model.*;
import service.MealService;
import service.ReservationService;
import view.*;
import java.time.LocalDate;
import java.util.List;
public class StudentMenuController extends BaseController {
    private StudentView studentView;
    private MealService mealService;
    private ReservationService reservationService;

    public StudentMenuController(User user) {
        super(user);
        this.studentView = new StudentView();
        this.mealService = new MealService();
        this.reservationService = new ReservationService();
    }
    @Override
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            studentView.displayStudentDashboard((Student) currentUser);
            int choice = consoleUI.getMenuChoice();
            controlChoice(choice);
            if (choice == 5) {
                exit = true;
            }
        }
    }
    @Override
    public void controlChoice(int choice) {
        switch (choice) {
            case 1:
                makeReservation();
                break;
            case 2:
                viewMyReservations();
                break;
            case 3:
                cancelReservation();
                break;
            case 4:
                viewAvailableMenu();
                break;
            case 5:
                break;
            default:
                consoleUI.displayError("Choix invalide");
        }
    }
    private void makeReservation() {
        Meal mealOfTheDay = mealService.getMenu();
        if (mealOfTheDay == null) {
            studentView.displayError("Aucun repas disponible aujourd'hui.");
            return;
        }
        studentView.displayMeal(mealOfTheDay);
        LocalDate date = consoleUI.promptDate("Date (YYYY-MM-DD): ");
        if (date == null || date.isBefore(LocalDate.now())) {
            studentView.displayError("Date invalide.");
            return;
        }
        boolean confirm = consoleUI.promptConfirmation("Confirmer la réservation ?");
        if (!confirm) {
            studentView.displayMessage("Réservation annulée.");
            return;
        }
        Reservation reservation = reservationService.createReservation(currentUser,mealOfTheDay,date,1);
        if (reservation == null) {
            studentView.displayError("Impossible de créer la réservation.");
            return;
        }
        studentView.displayReservationConfirmation(reservation);
    }
    private void viewMyReservations() {
        List<Reservation> reservations =reservationService.getReservationsByStudent(currentUser);
        if (reservations == null || reservations.isEmpty()) {
            studentView.displayMessage("Aucune réservation trouvée.");
            return;
        }
        studentView.displayReservations(reservations);
    }
    private void cancelReservation() {
        List<Reservation> reservations =reservationService.getReservationsByStudent(currentUser);
        if (reservations == null || reservations.isEmpty()) {
            studentView.displayMessage("Aucune réservation à annuler.");
            return;
        }
        studentView.displayReservations(reservations);
        int reservationId =consoleUI.promptInt("ID de la réservation à annuler : ");
        boolean success =reservationService.cancelReservation(reservationId, currentUser);
        if (!success) {
            studentView.displayError("Annulation impossible.");
            return;
        }
        studentView.displayMessage("Réservation annulée avec succès.");
    }
    private void viewAvailableMenu() {
        Meal mealOfTheDay = mealService.getMenu();
        if (mealOfTheDay == null) {
            studentView.displayMessage("Aucun menu disponible aujourd'hui.");
            return;
        }
        studentView.displayMeal(mealOfTheDay);
    }
}
