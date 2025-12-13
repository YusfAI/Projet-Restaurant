package exceptions;

// Exception générale pour tous les problèmes liés aux réservations
public class ReservationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    // Constructeur avec message d'erreur
    public ReservationException(String message) {
        super(message);
    }
    
    // Constructeur avec message et cause de l'erreur
    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}