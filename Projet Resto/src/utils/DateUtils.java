package utils;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;
import java.time.Duration;


public class DateUtils {
    

    // FORMATTERS (utilisent les constantes)

    
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern(Constants.DATE_FORMAT); // "dd/MM/yyyy"
    
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern(Constants.TIME_FORMAT); // "HH:mm"
    
    private static final DateTimeFormatter DATETIME_FORMATTER = 
        DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT); // "dd/MM/yyyy HH:mm"

    // MÉTHODES DE PARSING

    
    /**
     * Convertit une chaîne en LocalDate (format: dd/MM/yyyy)
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new DateTimeParseException("Date vide ou nulle", dateStr, 0);
        }
        return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
    }
    
    /**
     * Convertit une chaîne en LocalTime (format: HH:mm)

     */
    public static LocalTime parseTime(String timeStr) throws DateTimeParseException {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            throw new DateTimeParseException("Heure vide ou nulle", timeStr, 0);
        }
        return LocalTime.parse(timeStr.trim(), TIME_FORMATTER);
    }
    
    /**
     * Convertit une chaîne en LocalDateTime (format: dd/MM/yyyy HH:mm)
     */
    public static LocalDateTime parseDateTime(String datetimeStr) throws DateTimeParseException {
        if (datetimeStr == null || datetimeStr.trim().isEmpty()) {
            throw new DateTimeParseException("Date/Heure vide ou nulle", datetimeStr, 0);
        }
        return LocalDateTime.parse(datetimeStr.trim(), DATETIME_FORMATTER);
    }
    
    /**
     * Parse une date avec gestion d'erreur silencieuse
     */
    public static LocalDate parseDateSafe(String dateStr) {
        try {
            return parseDate(dateStr);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Parse une heure avec gestion d'erreur silencieuse
     */
    public static LocalTime parseTimeSafe(String timeStr) {
        try {
            return parseTime(timeStr);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // MÉTHODES DE FORMATAGE

    /**
     * Formate une LocalDate en chaîne (format: dd/MM/yyyy)
     */
    public static String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Formate une LocalTime en chaîne (format: HH:mm)
     */
    public static String formatTime(LocalTime time) {
        if (time == null) return "";
        return time.format(TIME_FORMATTER);
    }
    
    /**
     * Formate une LocalDateTime en chaîne (format: dd/MM/yyyy HH:mm)
     */
    public static String formatDateTime(LocalDateTime datetime) {
        if (datetime == null) return "";
        return datetime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Formate une date pour l'affichage utilisateur
     */
    public static String formatDateForDisplay(LocalDate date) {
        if (date == null) return "Date invalide";
        
        if (isToday(date)) {
            return "Aujourd'hui (" + formatDate(date) + ")";
        } else if (isTomorrow(date)) {
            return "Demain (" + formatDate(date) + ")";
        } else if (isYesterday(date)) {
            return "Hier (" + formatDate(date) + ")";
        } else {
            String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
            String jourSemaine = jours[date.getDayOfWeek().getValue() - 1];
            return jourSemaine + " " + formatDate(date);
        }
    }

    // MÉTHODES DE VÉRIFICATION TEMPORELLE

    
    /**
     * Vérifie si une date est dans le passé (strict)
     */
    public static boolean isDateInPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    
    /**
     * Vérifie si une date/heure est dans le passé (strict)
     */
    public static boolean isDateTimeInPast(LocalDateTime datetime) {
        return datetime != null && datetime.isBefore(LocalDateTime.now());
    }
    
    /**
     * Vérifie si une date est aujourd'hui
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }
    
    /**
     * Vérifie si une date est demain
     */
    public static boolean isTomorrow(LocalDate date) {
        return date != null && date.equals(LocalDate.now().plusDays(1));
    }
    
    /**
     * Vérifie si une date est hier
     */
    public static boolean isYesterday(LocalDate date) {
        return date != null && date.equals(LocalDate.now().minusDays(1));
    }
    
    /**
     * Vérifie si une date est dans le futur
     */
    public static boolean isDateInFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }
    
    /**
     * Vérifie si une heure est dans une plage horaire [start, end)
     */
    public static boolean isTimeInRange(LocalTime time, LocalTime start, LocalTime end) {
        if (time == null || start == null || end == null) return false;
        return !time.isBefore(start) && time.isBefore(end);
    }
    
    /**
     * Vérifie si une heure est valide pour un repas (7h-21h)
     */
    public static boolean isValidMealTime(LocalTime time) {
        if (time == null) return false;
        LocalTime minMealTime = LocalTime.of(12, 0);  // 12h00
        LocalTime maxMealTime = LocalTime.of(14, 0); // 14h00
        return !time.isBefore(minMealTime) && !time.isAfter(maxMealTime);
    }

    // MÉTHODES DE CALCUL TEMPOREL
    
    /**
     * Calcule la différence en heures entre deux dates/heures
     */
    public static long hoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return Duration.between(start, end).toHours();
    }
    
    /**
     * Calcule la différence en minutes entre deux heures
     */
    public static long minutesBetween(LocalTime start, LocalTime end) {
        if (start == null || end == null) return 0;
        return Duration.between(start, end).toMinutes();
    }
    
    /**
     * Calcule l'âge à partir d'une date de naissance
     */
    
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();
        
        // Ajuster si l'anniversaire n'est pas encore passé cette année
        if (today.getMonthValue() < birthDate.getMonthValue() ||
            (today.getMonthValue() == birthDate.getMonthValue() && 
             today.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }
        
        return age;
    }
    
    /**
     * Vérifie si l'âge est >= 16 ans (pour ValidationUtils)
     */
    public static boolean isAtLeast16YearsOld(LocalDate birthDate) {
        return calculateAge(birthDate) >= 16;
    }
    
    /**
     * Ajoute des jours ouvrables (lun-ven) à une date
     */
    public static LocalDate addBusinessDays(LocalDate date, int days) {
        if (date == null) return null;
        
        LocalDate result = date;
        int addedDays = 0;
        
        while (addedDays < days) {
            result = result.plusDays(1);
            // Ne compter que les jours ouvrables (lun-ven)
            if (result.getDayOfWeek() != DayOfWeek.SATURDAY && 
                result.getDayOfWeek() != DayOfWeek.SUNDAY) {
                addedDays++;
            }
        }
        
        return result;
    }
    /**
     * Vérifie si une date est un jour d'ouverture du restaurant (lun-ven)
     */
    public static boolean isRestaurantOpenDay(LocalDate date) {
        if (date == null) return false;
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }
    
    /**
     * Vérifie si un créneau horaire est valide pour une réservation
     */
    public static boolean isValidReservationSlot(LocalTime start, LocalTime end) {
        if (!isValidMealTime(start) || !isValidMealTime(end)) {
            return false;
        }
        
        // Durée minimum 30 min, maximum 3 heures
        long durationMinutes = minutesBetween(start, end);
        return durationMinutes >= 30 && durationMinutes <= 180;
    }
    
    /**
     * Génère les créneaux horaires disponibles pour un jour donné
     */
    public static java.util.List<String> generateAvailableTimeSlots(LocalDate date) {
        java.util.List<String> slots = new java.util.ArrayList<>();
        
        if (!isRestaurantOpenDay(date)) {
            return slots;
        }
        
        // Créneaux standards (adaptables)
        slots.add("07:00 - 10:00 (Petit-déjeuner)");
        slots.add("11:30 - 14:30 (Déjeuner)");
        slots.add("18:00 - 20:30 (Dîner)");
        
        return slots;
    }
    
    /**
     * Vérifie si une annulation est possible (2h avant le repas)
     */
    public static boolean canCancelReservation(LocalDateTime mealDateTime) {
        if (mealDateTime == null) return false;
        
        LocalDateTime now = LocalDateTime.now();
        long hoursBefore = hoursBetween(now, mealDateTime);
        
        return hoursBefore >= Constants.MIN_HOURS_BEFORE_CANCELLATION;
    }
    
    /**
     * Vérifie si une réservation est possible (pas dans le passé)
     */
    public static boolean canMakeReservation(LocalDateTime reservationDateTime) {
        return reservationDateTime != null && 
               !reservationDateTime.isBefore(LocalDateTime.now().plusHours(1));
    }
  
    // MÉTHODES UTILITAIRES D'AFFICHAGE

    /**
     * Affiche une durée en format lisible
     */
    public static String formatDuration(long minutes) {
        if (minutes < 60) {
            return minutes + " min";
        } else {
            long hours = minutes / 60;
            long remainingMinutes = minutes % 60;
            if (remainingMinutes == 0) {
                return hours + " h";
            } else {
                return hours + " h " + remainingMinutes + " min";
            }
        }
    }
    
    /**
     * Génère un horodatage pour les logs
     */
    public static String getTimestampForLog() {
        return LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        );
    }
}