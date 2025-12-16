package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleUtils {
    private static final Set<DayOfWeek> OPEN_DAYS = new HashSet<>(Arrays.asList(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ));

    
    private static final LocalTime OPENING_TIME = LocalTime.of(12, 0);   // 12h00
    private static final LocalTime CLOSING_TIME = LocalTime.of(14, 0);   // 14h00

    
    // Déjeuner : 12h00 - 14h00 
    private static final TimeSlot LUNCH_SLOT = 
        new TimeSlot(OPENING_TIME, CLOSING_TIME);
    
    // Créneaux disponibles à l'intérieur de la plage déjeuner
    private static final List<TimeSlot> AVAILABLE_LUNCH_SLOTS = Arrays.asList(
        new TimeSlot(LocalTime.of(12, 0), LocalTime.of(12, 30)),
        new TimeSlot(LocalTime.of(12, 30), LocalTime.of(13, 0)),
        new TimeSlot(LocalTime.of(13, 0), LocalTime.of(13, 30)),
        new TimeSlot(LocalTime.of(13, 30), LocalTime.of(14, 0))
    );
    

    private static final int SLOT_DURATION_MINUTES = 30;   // 30 minutes par créneau
    private static final int MAX_SLOTS_PER_DAY = 4;        // 4 créneaux de 30min entre 12h-14h

    // MÉTHODES DE VÉRIFICATION D'OUVERTURE

    
    /**
     * Vérifie si le restaurant est ouvert à une date donnée.
     * param date La date à vérifier
     * return true si ouvert (lundi à vendredi), false sinon
     */
    public static boolean isRestaurantOpen(LocalDate date) {
        if (date == null) return false;
        return OPEN_DAYS.contains(date.getDayOfWeek());
    }

    /**
     * Vérifie si le restaurant est ouvert maintenant (jour + heure).
     */
    public static boolean isRestaurantOpenNow() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        return isRestaurantOpen(today) && isDuringOpeningHours(now);
    }
    
    /**
     * Vérifie si une heure est dans les heures d'ouverture (12h-14h).
     */
    public static boolean isDuringOpeningHours(LocalTime time) {
        if (time == null) return false;
        return !time.isBefore(OPENING_TIME) && time.isBefore(CLOSING_TIME);
    }
    
    /**
     * Vérifie si un créneau horaire est dans les heures d'ouverture.
     */
    public static boolean isTimeSlotDuringOpeningHours(LocalTime start, LocalTime end) {
        return isDuringOpeningHours(start) && isDuringOpeningHours(end);
    }

    // MÉTHODES POUR LES CRÉNEAUX HORAIRES

    
    /**
     * Vérifie si un créneau horaire est valide pour le déjeuner.
     * - Doit être entre 12h et 14h
     * - Début avant fin
     * - Durée exacte de 30 minutes
     */
    public static boolean isValidLunchTimeSlot(LocalTime start, LocalTime end) {
        if (start == null || end == null) return false;
        
        // Vérifier que début < fin
        if (!start.isBefore(end)) {
            return false;
        }
        
        // Vérifier les heures d'ouverture
        if (!isTimeSlotDuringOpeningHours(start, end)) {
            return false;
        }
        
        // Vérifier la durée (exactement 30 minutes)
        long durationMinutes = Duration.between(start, end).toMinutes();
        return durationMinutes == SLOT_DURATION_MINUTES;
    }
    
    /**
     * Vérifie si un créneau horaire est valide (méthode générale).
     */
    public static boolean isValidTimeSlot(LocalTime start, LocalTime end) {
        return isValidLunchTimeSlot(start, end);
    }
    
    /**
     * Vérifie si deux créneaux se chevauchent.
     */
    public static boolean hasTimeOverlap(LocalTime start1, LocalTime end1,
                                        LocalTime start2, LocalTime end2) {
        if (start1 == null || end1 == null || start2 == null || end2 == null) {
            return false;
        }
        
        // Deux créneaux se chevauchent si:
        // start1 < end2 ET start2 < end1
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
    
    /**
     * Calcule la durée d'un créneau en minutes.
     */
    public static int calculateDurationMinutes(LocalTime start, LocalTime end) {
        if (start == null || end == null) return 0;
        return (int) Duration.between(start, end).toMinutes();
    }
    
    /**
     * Formate une durée en texte lisible.
     */
    public static String formatDuration(int minutes) {
        if (minutes < 60) {
            return minutes + " minutes";
        } else {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            if (remainingMinutes == 0) {
                return hours + " heure" + (hours > 1 ? "s" : "");
            } else {
                return hours + " heure" + (hours > 1 ? "s" : "") + 
                       " et " + remainingMinutes + " minute" + (remainingMinutes > 1 ? "s" : "");
            }
        }
    }

    // MÉTHODES POUR LES CRÉNEAUX DISPONIBLES
    
    /**
     * Retourne tous les créneaux disponibles pour le déjeuner.
     */
    public static List<TimeSlot> getAllLunchSlots() {
        return new ArrayList<>(AVAILABLE_LUNCH_SLOTS);
    }
    
    /**
     * Retourne les créneaux disponibles pour une date donnée.
     * (Filtre les créneaux déjà réservés/passés)
     */
    public static List<TimeSlot> getAvailableTimeSlots(LocalDate date, List<TimeSlot> bookedSlots) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        
        if (!isRestaurantOpen(date)) {
            return availableSlots;
        }
        
        LocalDateTime now = LocalDateTime.now();
        boolean isToday = date.equals(LocalDate.now());
        
        for (TimeSlot slot : AVAILABLE_LUNCH_SLOTS) {
            // Vérifier si le créneau est dans le passé (si c'est aujourd'hui)
            if (isToday) {
                LocalDateTime slotDateTime = LocalDateTime.of(date, slot.getStartTime());
                if (slotDateTime.isBefore(now)) {
                    continue; // Créneau passé, skip
                }
            }
            
            // Vérifier si le créneau est déjà réservé
            boolean isBooked = false;
            for (TimeSlot bookedSlot : bookedSlots) {
                if (slot.equals(bookedSlot)) {
                    isBooked = true;
                    break;
                }
            }
            
            if (!isBooked) {
                availableSlots.add(slot);
            }
        }
        
        return availableSlots;
    }
    
    /**
     * Retourne les créneaux disponibles pour aujourd'hui.
     */
    public static List<TimeSlot> getTodayAvailableTimeSlots(List<TimeSlot> bookedSlots) {
        return getAvailableTimeSlots(LocalDate.now(), bookedSlots);
    }
    
    /**
     * Retourne le prochain créneau disponible.
     */
    public static TimeSlot getNextAvailableSlot(LocalDate date, List<TimeSlot> bookedSlots) {
        List<TimeSlot> available = getAvailableTimeSlots(date, bookedSlots);
        return available.isEmpty() ? null : available.get(0);
    }

    // MÉTHODES POUR LES RÉSERVATIONS

    /**
     * Vérifie si une réservation peut être annulée (au moins 2h avant).
     */
    public static boolean canCancelReservation(LocalDateTime mealDateTime) {
        if (mealDateTime == null) return false;
        
        LocalDateTime now = LocalDateTime.now();
        long hoursBetween = Duration.between(now, mealDateTime).toHours();
        
        return hoursBetween >= Constants.MIN_HOURS_BEFORE_CANCELLATION;
    }
    
    /**
     * Vérifie si une réservation peut être faite (au moins 1h avant).
     */
    public static boolean canMakeReservation(LocalDateTime reservationDateTime) {
        if (reservationDateTime == null) return false;
        
        LocalDateTime now = LocalDateTime.now();
        long hoursBetween = Duration.between(now, reservationDateTime).toHours();
        
        return hoursBetween >= Constants.MIN_HOURS_BEFORE_RESERVATION;
    }
    
    /**
     * Vérifie si une réservation peut être faite pour aujourd'hui.
     */
    public static boolean canMakeReservationForToday() {
        LocalTime now = LocalTime.now();
        // Peut réserver si on est avant 13h (1h avant la fermeture)
        return now.isBefore(LocalTime.of(13, 0));
    }
    
    /**
     * Calcule la date/heure limite pour annuler une réservation.
     */
    public static LocalDateTime getCancellationDeadline(LocalDateTime mealDateTime) {
        if (mealDateTime == null) return null;
        return mealDateTime.minusHours(Constants.MIN_HOURS_BEFORE_CANCELLATION);
    }

    // MÉTHODES POUR LA GÉNÉRATION DE JOURS

    /**
     * Génère une liste de jours ouvrés à partir d'aujourd'hui.
     */
    public static List<LocalDate> getNextOpenDays(int numberOfDays) {
        List<LocalDate> openDays = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        int daysAdded = 0;
        
        while (daysAdded < numberOfDays) {
            currentDate = currentDate.plusDays(1);
            if (isRestaurantOpen(currentDate)) {
                openDays.add(currentDate);
                daysAdded++;
            }
        }
        
        return openDays;
    }
    
    /**
     * Retourne les 5 prochains jours ouvrés.
     */
    public static List<LocalDate> getNext5OpenDays() {
        return getNextOpenDays(5);
    }

    // ============================================
    // CLASSE INTERNE TIMESLOT
    // ============================================
    
    /**
     * Classe interne pour représenter un créneau horaire.
     */
    public static class TimeSlot {
        private LocalTime startTime;
        private LocalTime endTime;
        
        public TimeSlot(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
        
        public LocalTime getStartTime() { 
            return startTime; 
        }
        
        public LocalTime getEndTime() { 
            return endTime; 
        }
        
        /**
         * Vérifie si une heure est contenue dans ce créneau.
         */
        public boolean contains(LocalTime time) {
            if (time == null) return false;
            return !time.isBefore(startTime) && time.isBefore(endTime);
        }
        
        /**
         * Retourne le créneau formaté (ex: "12:00 - 12:30")
         */
        public String getFormattedSlot() {
            return DateUtils.formatTime(startTime) + " - " + DateUtils.formatTime(endTime);
        }
        
        /**
         * Retourne la durée en minutes.
         */
        public int getDurationMinutes() {
            return ScheduleUtils.calculateDurationMinutes(startTime, endTime);
        }
        
        /**
         * Retourne la durée formatée.
         */
        public String getFormattedDuration() {
            return formatDuration(getDurationMinutes());
        }
        
        @Override
        public String toString() {
            return getFormattedSlot() + " (" + getFormattedDuration() + ")";
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TimeSlot timeSlot = (TimeSlot) obj;
            return startTime.equals(timeSlot.startTime) && endTime.equals(timeSlot.endTime);
        }
        
        @Override
        public int hashCode() {
            return 31 * startTime.hashCode() + endTime.hashCode();
        }
    }

}