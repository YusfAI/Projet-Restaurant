package exceptions;

// Exception quand l'horaire de réservation est invalide (horaire passé, restaurant fermé, etc.)
public class InvalidTimeSlotException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    // Constructeur avec message d'erreur
    public InvalidTimeSlotException(String message) {
        super(message);
    }
    
    // Constructeur avec message et cause de l'erreur
    public InvalidTimeSlotException(String message, Throwable cause) {
        super(message, cause);
    }
}

