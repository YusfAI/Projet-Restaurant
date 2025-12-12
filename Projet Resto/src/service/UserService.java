package service;

import model.User;
import model.Student;
import model.Staff;
import model.DataStore;
import exceptions.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


//les erreurs dans ce code sont due a l'inexistance de quelques methode qui seront eventuellement creer dans le package exceptions




public class UserService {
    
    /**
     * Recherche un utilisateur par son ID (Student ou Staff)
     * @param userId ID de l'utilisateur
     * @return L'utilisateur trouvé
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     */
    public User findUserById(String Id) throws UserNotFoundException { 
        // Chercher d'abord dans les étudiants
        User user = DataStore.findStudentById(Id);
        
        // Si pas trouvé, chercher dans le staff
        if (user == null) {
            user = DataStore.findStaffById(Id);
        }
        
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé avec l'ID: " + Id);
        }
        
        return user;
    }
    
    /**
     * Recherche un utilisateur par email (Student ou Staff)
     * @param email Email de l'utilisateur
     * @return L'utilisateur trouvé
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     */
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = DataStore.findUserByEmail(email);
        
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé avec l'email: " + email);
        }
        
        return user;
    }
    
    /**
     * Recherche un étudiant par son ID
     * @param studentId ID de l'étudiant
     * @return L'étudiant trouvé
     * @throws UserNotFoundException Si l'étudiant n'existe pas
     */
    public Student findStudentById(String studentId) throws UserNotFoundException {
        Student student = DataStore.findStudentById(studentId);
        
        if (student == null) {
            throw new UserNotFoundException("Étudiant non trouvé avec l'ID: " + studentId);
        }
        
        return student;
    }
    
    /**
     * Recherche un membre du staff par son ID
     * @param staffId ID du staff
     * @return Le membre du staff
     * @throws UserNotFoundException Si le staff n'existe pas
     */
    public Staff findStaffById(String staffId) throws UserNotFoundException {
        Staff staff = DataStore.findStaffById(staffId);
        
        if (staff == null) {
            throw new UserNotFoundException("Membre du staff non trouvé avec l'ID: " + staffId);
        }
        
        return staff;
    }
    
    /**
     * Vérifie si un email est déjà utilisé
     * @param email Email à vérifier
     * @return true si l'email est déjà utilisé
     */
    public boolean isEmailTaken(String email) {
        return DataStore.findUserByEmail(email) != null;
    }
    
    /**
     * Ajoute un nouvel étudiant
     * @param student L'étudiant à ajouter
     * @throws IllegalArgumentException Si l'email est déjà pris
     */
    public void addStudent(Student student) throws IllegalArgumentException {
        if (isEmailTaken(student.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
        }
        
        if (student.getNumCarte() == null || student.getNumCarte().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de carte étudiant est obligatoire");
        }
        
        DataStore.addStudent(student);
    }
    
    /**
     * Ajoute un nouveau membre du staff
     * @param staff Le staff à ajouter
     * @throws IllegalArgumentException Si l'email est déjà pris
     */
    public void addStaff(Staff staff) throws IllegalArgumentException {
        if (isEmailTaken(staff.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
        }
        
        DataStore.addStaff(staff);
    }
    
    /**
     * Supprime un utilisateur par son ID
     * @param userId ID de l'utilisateur
     * @return true si la suppression a réussi
     */
    public boolean deleteUser(String userId) {
        boolean removed = DataStore.removeStudent(userId);
        
        if (!removed) {
            removed = DataStore.removeStaff(userId);
        }
        
        return removed;
    }
    
    /**
     * Met à jour le profil d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param nom Nouveau nom (peut être null pour ne pas modifier)
     * @param email Nouvel email (peut être null pour ne pas modifier)
     * @param password Nouveau mot de passe (peut être null pour ne pas modifier)
     * @return L'utilisateur mis à jour
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws IllegalArgumentException Si l'email est déjà pris
     */
    public User updateUser(String userId, String nom, String email, String password) 
            throws UserNotFoundException, IllegalArgumentException {
        
        User user = findUserById(userId);
        
        if (nom != null && !nom.isEmpty()) {
            user.setNom(nom);
        }
        
        if (email != null && !email.isEmpty()) {
            User existingUser = DataStore.findUserByEmail(email);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
            }
            user.setEmail(email);
        }
        
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        
        return user;
    }
    
    /**
     * Récupère tous les étudiants
     * @return Liste de tous les étudiants
     */
    public List<Student> getAllStudents() {
        return DataStore.getStudents();
    }
    
    /**
     * Récupère tous les membres du staff
     * @return Liste de tous les staffs
     */
    public List<Staff> getAllStaff() {
        return DataStore.getStaffMembers();
    }
    
    /**
     * Récupère tous les utilisateurs actifs (Staff qui sont actifs)
     * @return Liste des staffs actifs
     */
    public List<Staff> getActiveStaff() {
        return DataStore.getStaffMembers().stream()
            .filter(Staff::isActif)
            .collect(Collectors.toList());
    }
    
    /**
     * Récupère le nombre total d'utilisateurs par rôle
     * @param role Le rôle à compter ("STUDENT" ou "STAFF")
     * @return Le nombre d'utilisateurs avec ce rôle
     */
    public int getUserCountByRole(String role) {
        if ("STUDENT".equalsIgnoreCase(role)) {
            return DataStore.getStudents().size();
        } else if ("STAFF".equalsIgnoreCase(role)) {
            return DataStore.getStaffMembers().size();
        }
        return 0;
    }
    
    /**
     * Vérifie si un utilisateur existe avec cet ID
     * @param userId ID à vérifier
     * @return true si l'utilisateur existe
     */
    public boolean userExists(String userId) {
        return DataStore.findStudentById(userId) != null || 
               DataStore.findStaffById(userId) != null;
    }
    
    /**
     * Vérifie si un étudiant peut faire des réservations
     * @param studentId ID de l'étudiant
     * @return true si l'étudiant peut réserver
     */
    public boolean canStudentReserve(String studentId) {
        Student student = DataStore.findStudentById(studentId);
        return student != null && 
               student.getNumCarte() != null && 
               !student.getNumCarte().isEmpty();
    }
    
    /**
     * Recherche des étudiants par niveau scolaire
     * @param niveau Le niveau à rechercher
     * @return Liste des étudiants de ce niveau
     */
    public List<Student> findStudentsByLevel(String niveau) {
        return DataStore.getStudents().stream()
            .filter(student -> student.getNiveau() != null && 
                              student.getNiveau().equalsIgnoreCase(niveau))
            .collect(Collectors.toList());
    }
    
    /**
     * Recherche des étudiants par spécialité
     * @param specialite La spécialité à rechercher
     * @return Liste des étudiants de cette spécialité
     */
    public List<Student> findStudentsBySpecialty(String specialite) {
        return DataStore.getStudents().stream()
            .filter(student -> student.getSpecialite() != null && 
                              student.getSpecialite().equalsIgnoreCase(specialite))
            .collect(Collectors.toList());
    }
    
    /**
     * Recherche du staff par service
     * @param service Le service à rechercher
     * @return Liste du staff de ce service
     */
    public List<Staff> findStaffByService(String service) {
        return DataStore.getStaffMembers().stream()
            .filter(staff -> staff.getService() != null && 
                            staff.getService().equalsIgnoreCase(service))
            .collect(Collectors.toList());
    }
    
    /**
     * Génère un ID unique pour un nouvel utilisateur basé sur le type
     * @param userType "STUDENT" ou "STAFF"
     * @return Un nouvel ID unique
     */
    public String generateUserId(String userType) {
        int nextNumber = 0;
        
        if ("STUDENT".equalsIgnoreCase(userType)) {
            nextNumber = DataStore.getStudents().size() + 1;
            return String.format("S%03d", nextNumber);
        } else if ("STAFF".equalsIgnoreCase(userType)) {
            nextNumber = DataStore.getStaffMembers().size() + 1;
            return String.format("ST%03d", nextNumber);
        }
        
        return "U" + System.currentTimeMillis();
    }
}