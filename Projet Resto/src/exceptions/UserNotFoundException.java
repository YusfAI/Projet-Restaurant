package exceptions;

public class UserNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;
    private int userId;
    private String userIdentifier;
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UserNotFoundException(String message, int userId) {
        super(message);
        this.userId = userId;
    }
    
    public UserNotFoundException(String message, String userIdentifier) {
        super(message);
        this.userIdentifier = userIdentifier;
    }
    
    public UserNotFoundException(String message, int userId, String userIdentifier) {
        super(message);
        this.userId = userId;
        this.userIdentifier = userIdentifier;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String getUserIdentifier() {
        return userIdentifier;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Utilisateur introuvable : ");
        sb.append(getMessage());
        
        if (userId > 0) {
            sb.append(" (ID : ").append(userId).append(")");
        }
        
        if (userIdentifier != null) {
            sb.append(" (Identifiant : ").append(userIdentifier).append(")");
        }
        
        return sb.toString();
    }
    
    // Messages d'erreur prédéfinis
    public static UserNotFoundException byId(int userId) {
        return new UserNotFoundException(
            "Aucun étudiant trouvé avec l'ID " + userId + " dans le système du restaurant universitaire.",
            userId
        );
    }
    
    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException(
            "Aucun compte étudiant n'est associé à l'adresse email : " + email + ". " +
            "Veuillez vérifier l'email ou vous inscrire.",
            email
        );
    }
    
    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException(
            "Le nom d'utilisateur '" + username + "' n'existe pas. " +
            "Vérifiez votre identifiant ou créez un compte.",
            username
        );
    }
    
    public static UserNotFoundException studentNotFound(int studentId) {
        return new UserNotFoundException(
            "Numéro étudiant " + studentId + " introuvable. " +
            "Assurez-vous d'être inscrit au service de restauration universitaire.",
            studentId
        );
    }
    
    public static UserNotFoundException accountDeleted(String email) {
        return new UserNotFoundException(
            "Ce compte a été supprimé ou désactivé. " +
            "Contactez le service de restauration si vous pensez qu'il s'agit d'une erreur.",
            email
        );
    }
}

