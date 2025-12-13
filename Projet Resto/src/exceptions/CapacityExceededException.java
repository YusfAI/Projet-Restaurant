package exceptions;

// Exception quand le restaurant est plein (plus de places disponibles)
public class CapacityExceededException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    // Constructeur avec message d'erreur
    public CapacityExceededException(String message) {
        super(message);
    }
    
    // Constructeur avec message et cause de l'erreur
    public CapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}

