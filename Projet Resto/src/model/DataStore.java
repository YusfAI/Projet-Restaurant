package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    
    // Listes statiques pour stocker les données en mémoire
    private static List<Student> students = new ArrayList<>();
    private static List<Staff> staffMembers = new ArrayList<>();
    private static List<Meal> meals = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static List<DiningHall> diningHalls = new ArrayList<>();
    
    
    // Méthode pour initialiser les données d'exemple
    public static void initializeData() {
        initializeStudents();
        initializeStaff();
        initializeDiningHalls();
        initializeMeals();
    }
    
    
    // Initialiser des étudiants d'exemple
    private static void initializeStudents() {
        students.add(new Student("S001", "Ben Salah Ahmed", "ahmed.bensalah@ihec.tn", "pass123", 
                                "CE2024001", "Licence 2", "Marketing"));
        students.add(new Student("S002", "Gharbi Fatma", "fatma.gharbi@ihec.tn", "pass456", 
                                "CE2024002", "Master 1", "Finance"));
        students.add(new Student("S003", "Mansour Youssef", "youssef.mansour@ihec.tn", "pass789", 
                                "CE2024003", "Licence 3", "Gestion"));
    }
    
    
    // Initialiser le personnel d'exemple
    private static void initializeStaff() {
        staffMembers.add(new Staff("ST001", "Trabelsi Mohamed", "m.trabelsi@restaurant.tn", 
                                  "staff123", "STAFF", "Cuisine", true));
        staffMembers.add(new Staff("ST002", "Kacem Leila", "l.kacem@restaurant.tn", 
                                  "staff456", "STAFF", "Service", true));
        staffMembers.add(new Staff("AD001", "Haddad Sami", "s.haddad@restaurant.tn", 
                                  "admin123", "ADMIN", "Administration", true));
    }
    
    
    // Initialiser les salles de restauration
    private static void initializeDiningHalls() {
        diningHalls.add(new DiningHall("Salle Principale", "H001", 200, 200));
        
    }
    
    
    // Initialiser les repas d'exemple
    private static void initializeMeals() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        
        // Repas d'aujourd'hui
        meals.add(new Meal("M001", "Couscous aux légumes - Salade - Dessert", 4.5, 
                          today, LocalTime.of(12, 0), LocalTime.of(14, 0), true));
        meals.add(new Meal("M002", "Poulet grillé - Frites - Yaourt", 5.0, 
                          today, LocalTime.of(12, 0), LocalTime.of(14, 0), true));
        meals.add(new Meal("M003", "Pâtes Bolognaise - Salade verte - Fruit", 4.0, 
                          today, LocalTime.of(18, 30), LocalTime.of(20, 0), true));
        
        // Repas de demain
        meals.add(new Meal("M004", "Tajine aux olives - Riz - Dessert", 4.5, 
                          tomorrow, LocalTime.of(12, 0), LocalTime.of(14, 0), true));
        meals.add(new Meal("M005", "Poisson grillé - Salade - Fruit", 5.5, 
                          tomorrow, LocalTime.of(12, 0), LocalTime.of(14, 0), true));
        meals.add(new Meal("M006", "Pizza Margherita - Salade - Jus", 4.0, 
                          tomorrow, LocalTime.of(18, 30), LocalTime.of(20, 0), true));
    }
    
    
    // ========== MÉTHODES DE RECHERCHE ==========
    
    // Rechercher un étudiant par ID
    public static Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    
    
    // Rechercher un utilisateur par email (Student ou Staff)
    public static User findUserByEmail(String email) {
        // Recherche dans les étudiants
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        // Recherche dans le personnel
        for (Staff staff : staffMembers) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff;
            }
        }
        return null;
    }
    
    
    // Rechercher un membre du personnel par ID
    public static Staff findStaffById(String id) {
        for (Staff staff : staffMembers) {
            if (staff.getId().equals(id)) {
                return staff;
            }
        }
        return null;
    }
    
    
    // Rechercher un repas par ID
    public static Meal findMealById(String mealId) {
        for (Meal meal : meals) {
            if (meal.getMealId().equals(mealId)) {
                return meal;
            }
        }
        return null;
    }
    
    
    // Rechercher une réservation par ID
    public static Reservation findReservationById(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }
    
    
    // Rechercher une salle par ID
    public static DiningHall findDiningHallById(String hallId) {
        for (DiningHall hall : diningHalls) {
            if (hall.getHallId().equals(hallId)) {
                return hall;
            }
        }
        return null;
    }
    
    
    // Obtenir les réservations d'un étudiant
    public static List<Reservation> getReservationsByStudent(User user) {
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getUser().getId().equals(user.getId())) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }
    
    
    // Obtenir les repas disponibles à une date donnée
    public static List<Meal> getAvailableMealsByDate(LocalDate date) {
        List<Meal> availableMeals = new ArrayList<>();
        for (Meal meal : meals) {
            if (meal.getDateRepas().equals(date) && meal.isDisponible()) {
                availableMeals.add(meal);
            }
        }
        return availableMeals;
    }
    
    
    // Obtenir toutes les réservations pour une date donnée
    public static List<Reservation> getReservationsByDate(LocalDate date) {
        List<Reservation> dateReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getDate().equals(date)) {
                dateReservations.add(reservation);
            }
        }
        return dateReservations;
    }
    
    
    // ========== GETTERS POUR LES LISTES ==========
    
    public static List<Student> getStudents() {
        return students;
    }
    
    public static List<Staff> getStaffMembers() {
        return staffMembers;
    }
    
    public static List<Meal> getMeals() {
        return meals;
    }
    
    public static List<Reservation> getReservations() {
        return reservations;
    }
    
    public static List<DiningHall> getDiningHalls() {
        return diningHalls;
    }
    
    
    // ========== MÉTHODES D'AJOUT ==========
    
    public static void addStudent(Student student) {
        students.add(student);
    }
    
    public static void addStaff(Staff staff) {
        staffMembers.add(staff);
    }
    
    public static void addMeal(Meal meal) {
        meals.add(meal);
    }
    
    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    
    public static void addDiningHall(DiningHall hall) {
        diningHalls.add(hall);
    }
    
    
    // ========== MÉTHODES DE SUPPRESSION ==========
    
    public static boolean removeReservation(String reservationId) {
        return reservations.removeIf(r -> r.getReservationId().equals(reservationId));
    }
    
    public static boolean removeMeal(String mealId) {
        return meals.removeIf(m -> m.getMealId().equals(mealId));
    }
    
    public static boolean removeStudent(String studentId) {
        return students.removeIf(s -> s.getId().equals(studentId));
    }
    
    public static boolean removeStaff(String staffId) {
        return staffMembers.removeIf(s -> s.getId().equals(staffId));
    }
}
