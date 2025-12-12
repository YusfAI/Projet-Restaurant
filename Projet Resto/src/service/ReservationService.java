package service;

import model.Reservation;
import model.Student;
import model.Meal;
import model.DataStore;
import model.DiningHall;
import exceptions.ReservationException;
import exceptions.CapacityExceededException;
import exceptions.DuplicateReservationException;
import exceptions.UserNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



//les erreurs dans ce code sont due a l'inexistance de quelques methodes qui seront eventuellement creer dans le package exceptions

public class ReservationService {
    
    private final MealService mealService;
    private final DiningHallService diningHallService;
    private final UserService userService;
    
    public ReservationService() {
        this.mealService = new MealService();
        this.diningHallService = new DiningHallService();
        this.userService = new UserService();
    }
    
    /**
     * Crée une nouvelle réservation
     * @param studentId ID de l'étudiant
     * @param mealId ID du repas
     * @param nbPlaces Nombre de places à réserver
     * @return La réservation créée
     * @throws ReservationException Si la réservation échoue
     */
    public Reservation createReservation(String studentId, String mealId, int nbPlaces) 
            throws ReservationException, UserNotFoundException {
        
        // 1. Vérifier que l'étudiant existe
        Student student = userService.findStudentById(studentId);
        
        // 2. Vérifier que le repas existe et est disponible
        Meal meal = mealService.findMealById(mealId);
        if (meal == null) {
            throw new ReservationException("Repas non trouvé");
        }
        
        if (!mealService.canBeReserved(meal)) {
            throw new ReservationException("Ce repas n'est plus disponible pour réservation");
        }
        
        // 3. Vérifier si l'étudiant a déjà une réservation pour ce repas
        if (hasReservationForMeal(studentId, mealId)) {
            throw new DuplicateReservationException("Vous avez déjà une réservation pour ce repas");
        }
        
        // 4. Vérifier la capacité
        List<DiningHall> halls = diningHallService.getAllDiningHalls();
        if (halls.isEmpty()) {
            throw new ReservationException("Aucune salle de restaurant disponible");
        }
        
        String hallId = halls.get(0).getHallId();
        if (!diningHallService.hasAvailableCapacity(hallId, nbPlaces)) {
            throw new CapacityExceededException("Capacité insuffisante. Places disponibles: " + 
                                               diningHallService.getPlacesRestantes(hallId));
        }
        
        // 5. Créer la réservation
        Reservation reservation = new Reservation(
            student,
            meal,
            LocalDate.now(),
            LocalTime.now(),
            nbPlaces,
            "CONFIRMÉ"
        );
        
        // 6. Réserver les places dans la salle
        if (!diningHallService.reserverPlaces(hallId, nbPlaces)) {
            throw new ReservationException("Échec de la réservation des places");
        }
        
        // 7. Ajouter la réservation
        DataStore.addReservation(reservation);
        
        return reservation;
    }
    
    /**
     * Annule une réservation
     * @param reservationId ID de la réservation
     * @return true si l'annulation a réussi
     * @throws ReservationException Si la réservation ne peut être annulée
     */
    public boolean cancelReservation(String reservationId) throws ReservationException {
        Reservation reservation = DataStore.findReservationById(reservationId);
        
        if (reservation == null) {
            throw new ReservationException("Réservation non trouvée");
        }
        
        if (!canBeCancelled(reservation)) {
            throw new ReservationException("Cette réservation ne peut plus être annulée");
        }
        
        // Libérer les places dans la salle
        List<DiningHall> halls = diningHallService.getAllDiningHalls();
        if (!halls.isEmpty()) {
            String hallId = halls.get(0).getHallId();
            diningHallService.libererPlaces(hallId, reservation.getNbplaces());
        }
        
        reservation.setStatus("ANNULÉ");
        
        return true;
    }
    
    /**
     * Vérifie si un étudiant a déjà une réservation pour un repas donné
     * @param studentId ID de l'étudiant
     * @param mealId ID du repas
     * @return true si l'étudiant a déjà réservé ce repas
     */
    private boolean hasReservationForMeal(String studentId, String mealId) {
        List<Reservation> studentReservations = DataStore.getReservationsByStudent(
            DataStore.findStudentById(studentId)
        );
        
        for (Reservation reservation : studentReservations) {
            if ("CONFIRMÉ".equals(reservation.getStatus()) && 
                reservation.getMeal().getMealId().equals(mealId)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Vérifie si une réservation peut être annulée
     * @param reservation La réservation à vérifier
     * @return true si la réservation peut être annulée
     */
    private boolean canBeCancelled(Reservation reservation) {
        LocalTime mealStartTime = reservation.getMeal().getHeureDebut();
        LocalTime currentTime = LocalTime.now();
        
        if (reservation.getDate().equals(LocalDate.now())) {
            return currentTime.isBefore(mealStartTime.minusHours(2));
        }
        
        return reservation.getDate().isAfter(LocalDate.now());
    }
    
    /**
     * Récupère les réservations d'un étudiant
     * @param studentId ID de l'étudiant
     * @return Liste des réservations de l'étudiant
     */
    public List<Reservation> getStudentReservations(String studentId) {
        Student student = DataStore.findStudentById(studentId);
        if (student == null) {
            return List.of();
        }
        return DataStore.getReservationsByStudent(student);
    }
    
    /**
     * Récupère toutes les réservations
     * @return Liste de toutes les réservations
     */
    public List<Reservation> getAllReservations() {
        return DataStore.getReservations();
    }
    
    /**
     * Récupère les réservations pour une date donnée
     * @param date Date à rechercher
     * @return Liste des réservations pour cette date
     */
    public List<Reservation> getReservationsByDate(LocalDate date) {
        return DataStore.getReservationsByDate(date);
    }
    
    /**
     * Récupère les réservations actives (CONFIRMÉ)
     * @return Liste des réservations actives
     */
    public List<Reservation> getActiveReservations() {
        return DataStore.getReservations().stream()
            .filter(r -> "CONFIRMÉ".equals(r.getStatus()))
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Confirme une réservation (changer le statut)
     * @param reservationId ID de la réservation
     * @return true si la confirmation a réussi
     */
    public boolean confirmReservation(String reservationId) {
        Reservation reservation = DataStore.findReservationById(reservationId);
        
        if (reservation != null) {
            reservation.setStatus("CONFIRMÉ");
            return true;
        }
        
        return false;
    }
    
    /**
     * Récupère le nombre de réservations actives (CONFIRMÉ)
     * @return Le nombre de réservations actives
     */
    public int getActiveReservationsCount() {
        return (int) DataStore.getReservations().stream()
            .filter(r -> "CONFIRMÉ".equals(r.getStatus()))
            .count();
    }
}