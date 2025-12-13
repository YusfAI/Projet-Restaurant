package service;

import model.User;
import model.Student;
import model.Staff;
import model.DataStore;
import exceptions.AuthenticationException;
import exceptions.UserNotFoundException;

public class AuthenticationService {
    
    private UserService userService;
    
    public AuthenticationService() {
        this.userService = new UserService();
    }
    
    /**
     * Authentifie un utilisateur avec son email et mot de passe
     * @param email Email de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     * @return L'utilisateur authentifié
     * @throws AuthenticationException Si l'authentification échoue
     */
    public User authenticate(String email, String password) throws AuthenticationException {
        // Vérifier que les champs ne sont pas vides
        if (email == null || email.isEmpty()) {
            throw new AuthenticationException("L'email ne peut pas être vide");
        }
        
        if (password == null || password.isEmpty()) {
            throw new AuthenticationException("Le mot de passe ne peut pas être vide");
        }
        
        // Rechercher l'utilisateur par email
        User user = DataStore.findUserByEmail(email);
        
        if (user == null) {
            throw new AuthenticationException("Email ou mot de passe incorrect");
        }
        
        // Vérifier le mot de passe
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Email ou mot de passe incorrect");
        }
        
        // Si c'est un staff, vérifier qu'il est actif
        if (user instanceof Staff) {
            Staff staff = (Staff) user;
            if (!staff.isActif()) {
                throw new AuthenticationException("Votre compte est désactivé. Contactez l'administrateur.");
            }
        }
        
        return user;
    }
    
    /**
     * Authentifie un utilisateur avec son ID et mot de passe
     * @param userId ID de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     * @return L'utilisateur authentifié
     * @throws AuthenticationException Si l'authentification échoue
     */
    public User authenticateById(String userId, String password) throws AuthenticationException {
        if (userId == null || userId.isEmpty()) {
            throw new AuthenticationException("L'identifiant ne peut pas être vide");
        }
        
        if (password == null || password.isEmpty()) {
            throw new AuthenticationException("Le mot de passe ne peut pas être vide");
        }
        
        try {
            User user = userService.findUserById(userId);
            
            if (!user.getPassword().equals(password)) {
                throw new AuthenticationException("Identifiant ou mot de passe incorrect");
            }
            
            // Si c'est un staff, vérifier qu'il est actif
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                if (!staff.isActif()) {
                    throw new AuthenticationException("Votre compte est désactivé. Contactez l'administrateur.");
                }
            }
            
            return user;
            
        } catch (UserNotFoundException e) {
            throw new AuthenticationException("Identifiant ou mot de passe incorrect");
        }
    }
    
    /**
     * Vérifie si un email existe dans le système
     * @param email Email à vérifier
     * @return true si l'email existe
     */
    public boolean emailExists(String email) {
        return DataStore.findUserByEmail(email) != null;
    }
    
    /**
     * Vérifie si un utilisateur est un étudiant
     * @param user L'utilisateur à vérifier
     * @return true si l'utilisateur est un étudiant
     */
    public boolean isStudent(User user) {
        return user instanceof Student;
    }
    
    /**
     * Vérifie si un utilisateur est un membre du staff
     * @param user L'utilisateur à vérifier
     * @return true si l'utilisateur est du staff
     */
    public boolean isStaff(User user) {
        return user instanceof Staff;
    }
    
    /**
     * Vérifie si un utilisateur est un administrateur
     * @param user L'utilisateur à vérifier
     * @return true si l'utilisateur est admin
     */
    public boolean isAdmin(User user) {
        return user instanceof Staff && "ADMIN".equalsIgnoreCase(user.getRole());
    }
    
    /**
     * Change le mot de passe d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param oldPassword Ancien mot de passe
     * @param newPassword Nouveau mot de passe
     * @return true si le changement a réussi
     * @throws AuthenticationException Si l'ancien mot de passe est incorrect
     */
    public boolean changePassword(String userId, String oldPassword, String newPassword) 
            throws AuthenticationException {
        
        if (newPassword == null || newPassword.length() < 6) {
            throw new AuthenticationException("Le nouveau mot de passe doit contenir au moins 6 caractères");
        }
        
        try {
            User user = userService.findUserById(userId);
            
            if (!user.getPassword().equals(oldPassword)) {
                throw new AuthenticationException("L'ancien mot de passe est incorrect");
            }
            
            user.setPassword(newPassword);
            return true;
            
        } catch (UserNotFoundException e) {
            throw new AuthenticationException("Utilisateur non trouvé");
        }
    }
    
    /**
     * Réinitialise le mot de passe d'un utilisateur (pour admin)
     * @param userId ID de l'utilisateur
     * @param newPassword Nouveau mot de passe
     * @return true si la réinitialisation a réussi
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     */
    public boolean resetPassword(String userId, String newPassword) throws UserNotFoundException {
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
        }
        
        User user = userService.findUserById(userId);
        user.setPassword(newPassword);
        return true;
    }
    
    /**
     * Valide le format d'un email
     * @param email Email à valider
     * @return true si l'email est valide
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Validation simple d'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Valide la force d'un mot de passe
     * @param password Mot de passe à valider
     * @return true si le mot de passe est suffisamment fort
     */
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        
        // Le mot de passe doit contenir au moins 6 caractères
        // (Vous pouvez ajouter plus de règles si nécessaire)
        return true;
    }
    
    /**
     * Enregistre un nouvel étudiant
     * @param id ID de l'étudiant
     * @param nom Nom de l'étudiant
     * @param email Email de l'étudiant
     * @param password Mot de passe
     * @param numCarte Numéro de carte étudiant
     * @param niveau Niveau scolaire
     * @param specialite Spécialité
     * @return L'étudiant créé
     * @throws AuthenticationException Si l'inscription échoue
     */
    public Student registerStudent(String id, String nom, String email, String password,
                                   String numCarte, String niveau, String specialite) 
            throws AuthenticationException {
        
        // Validation des champs
        if (!isValidEmail(email)) {
            throw new AuthenticationException("Email invalide");
        }
        
        if (!isStrongPassword(password)) {
            throw new AuthenticationException("Le mot de passe doit contenir au moins 6 caractères");
        }
        
        if (emailExists(email)) {
            throw new AuthenticationException("Cet email est déjà utilisé");
        }
        
        if (numCarte == null || numCarte.isEmpty()) {
            throw new AuthenticationException("Le numéro de carte étudiant est obligatoire");
        }
        
        // Créer le nouvel étudiant
        Student student = new Student(id, nom, email, password, numCarte, niveau, specialite);
        
        try {
            userService.addStudent(student);
            return student;
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }
    
    /**
     * Enregistre un nouveau membre du staff (réservé aux admins)
     * @param id ID du staff
     * @param nom Nom du staff
     * @param email Email du staff
     * @param password Mot de passe
     * @param service Service du staff
     * @param actif Statut actif/inactif
     * @return Le staff créé
     * @throws AuthenticationException Si l'inscription échoue
     */
    public Staff registerStaff(String id, String nom, String email, String password,
                              String service, boolean actif) 
            throws AuthenticationException {
        
        // Validation des champs
        if (!isValidEmail(email)) {
            throw new AuthenticationException("Email invalide");
        }
        
        if (!isStrongPassword(password)) {
            throw new AuthenticationException("Le mot de passe doit contenir au moins 6 caractères");
        }
        
        if (emailExists(email)) {
            throw new AuthenticationException("Cet email est déjà utilisé");
        }
        
        // Créer le nouveau staff
        Staff staff = new Staff(id, nom, email, password, "STAFF", service, actif);
        
        try {
            userService.addStaff(staff);
            return staff;
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }
    
    /**
     * Déconnecte un utilisateur (pour logging ou cleanup si nécessaire)
     * @param user L'utilisateur à déconnecter
     */
    public void logout(User user) {
        // Pour l'instant, cette méthode ne fait rien de spécial
        // Elle peut être étendue pour logger la déconnexion ou nettoyer des sessions
        System.out.println("Déconnexion de l'utilisateur: " + user.getNom());
    }
    
    /**
     * Vérifie si un utilisateur a les droits d'accès à une fonctionnalité
     * @param user L'utilisateur
     * @param requiredRole Le rôle requis
     * @return true si l'utilisateur a les droits
     */
    public boolean hasRole(User user, String requiredRole) {
        if (user == null || requiredRole == null) {
            return false;
        }
        
        return user.getRole().equalsIgnoreCase(requiredRole);
    }
}
