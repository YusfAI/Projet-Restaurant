package exceptions;

// Exception quand l'étudiant a déjà une réservation pour le même repas
public class DuplicateReservationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    // Constructeur avec message d'erreur
    public DuplicateReservationException(String message) {
        super(message);
    }
    
    // Constructeur avec message et cause de l'erreur
    public DuplicateReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}


