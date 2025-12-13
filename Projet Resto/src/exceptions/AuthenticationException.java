package exceptions;

// Exception pour les problèmes de connexion (mot de passe incorrect, utilisateur non trouvé, etc.)
public class AuthenticationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    // Constructeur avec message d'erreur
    public AuthenticationException(String message) {
        super(message);
    }
    
    // Constructeur avec message et cause de l'erreur
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

