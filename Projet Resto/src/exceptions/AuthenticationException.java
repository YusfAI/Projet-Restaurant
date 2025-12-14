package exceptions;

public class AuthenticationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    private String errorCode;
    
    // Constructeur simple
    public AuthenticationException(String message) {
        super(message);
    }
    
    // Constructeur avec cause
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructeur avec code d'erreur
    public AuthenticationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    // Constructeur complet
    public AuthenticationException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String toString() {
        if (errorCode != null) {
            return "Erreur d'authentification [" + errorCode + "] : " + getMessage();
        }
        return "Erreur d'authentification : " + getMessage();
    }
    
    // Messages d'erreur prédéfinis
    public static AuthenticationException invalidCredentials() {
        return new AuthenticationException("Email ou mot de passe incorrect", "AUTH_001");
    }
    
    public static AuthenticationException accountLocked() {
        return new AuthenticationException("Votre compte a été verrouillé. Contactez l'administrateur.", "AUTH_002");
    }
    
    public static AuthenticationException accountNotActivated() {
        return new AuthenticationException("Votre compte n'est pas encore activé. Vérifiez votre email.", "AUTH_003");
    }
    
    public static AuthenticationException sessionExpired() {
        return new AuthenticationException("Votre session a expiré. Veuillez vous reconnecter.", "AUTH_004");
    }
    
    public static AuthenticationException insufficientPermissions() {
        return new AuthenticationException("Vous n'avez pas les permissions nécessaires pour cette action.", "AUTH_005");
    }
}

