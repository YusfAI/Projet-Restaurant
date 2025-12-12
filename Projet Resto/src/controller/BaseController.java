package controller;
import model.User;
import service.*;
import view.*;
public abstract class BaseController {
    protected User currentUser;
    protected AuthenticationService authService;
    protected ReservationService reservationService;
    protected ConsoleUI consoleUI;
    public BaseController(User user) {
        this.currentUser = user;
        this.authService = new AuthenticationService();
        this.reservationService = new ReservationService();
        this.consoleUI = new ConsoleUI();
    }
    public abstract void showMenu();
    public abstract void controlChoice(int choice);
    public final void run() {
        showMenu();
        int choice = consoleUI.getMenuChoice();
        controlChoice(choice);
    }
}
