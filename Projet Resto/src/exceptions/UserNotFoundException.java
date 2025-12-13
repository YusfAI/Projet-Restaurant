package exceptions;

// Exception quand l'utilisateur n'existe pas dans la base de données
public class UserNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    // Constructeur avec message d'erreur
    public UserNotFoundException(String message) {
        super(message);
    }
    
    // Constructeur avec message et cause de l'erreur
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
