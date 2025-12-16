package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class ValidationUtils {

    
    // Email IHEc : doit se terminer par @ihec.ucar.tn
    private static final Pattern EMAIL_IHEc_PATTERN = 
        Pattern.compile("^[A-Za-z0-9._%+-]+@ihec\\.ucar\\.tn$", Pattern.CASE_INSENSITIVE);
    
    
    // Mot de passe : minimum 6 caractères 
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^.{6,}$");
    
    // Nom/Prénom : lettres, espaces, tirets, apostrophes (caractères français/arabes)
    private static final Pattern NAME_PATTERN = 
        Pattern.compile("^[A-Za-zÀ-ÿ\\s\\-']{2,50}$");
    
    // Numéro de téléphone tunisien : 8 chiffres commençant par 2, 5, 9
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[259]\\d{7}$");
    
    
    // Code CIN (Carte d'Identité Nationale) : 8 chiffres
    private static final Pattern CIN_PATTERN = 
        Pattern.compile("^\\d{8}$");
    
    // Matricule étudiant : format flexible (chiffres et lettres)
    private static final Pattern student_card_PATTERN = 
        Pattern.compile("^[A-Z0-9]{6,12}$");
    
    // Prix en dinars tunisiens : 0.200 à 5.000 avec 3 décimales max
    private static final Pattern PRICE_PATTERN = 
        Pattern.compile("^[0-5](\\.\\d{1,3})?$");

   
    // MÉTHODES DE VALIDATION

    /**
     * Valide un email IHEc (doit terminer par @ihec.ucar.tn)
     */
    
    public static boolean isValidEmailIHEc(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_IHEc_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Valide un mot de passe (minimum 6 caractères)
     */
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Valide un nom ou prénom
     */
    
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    /**
     * Valide un numéro de téléphone tunisien (8 chiffres)
     */
    public static boolean isValidTunisianPhone(String phone) {
        if (phone == null) return false;
        // Retirer les espaces, points, tirets
        String cleaned = phone.replaceAll("[\\s.-]", "");
        return PHONE_PATTERN.matcher(cleaned).matches();
    }
    
    /**
     * Valide un numéro CIN (8 chiffres)
     */
    public static boolean isValidCIN(String cin) {
        if (cin == null) return false;
        return CIN_PATTERN.matcher(cin.trim()).matches();
    }
    
    /**
     * Valide un matricule étudiant
     */
    public static boolean isValidStudentMatricule(String matricule) {
        if (matricule == null || matricule.trim().isEmpty()) {
            return false;
        }
        return student_card_PATTERN.matcher(matricule.trim().toUpperCase()).matches();
    }
    
    /**
     * Valide un prix en dinars tunisiens (0.200 DT à 5.000 DT)
     */
    public static boolean isValidPriceDT(double price) {
        // Vérifie la plage 0.200 à 5.000
        if (price < 0.200 || price > 5.000) {
            return false;
        }
        
        // Vérifie le format (max 3 décimales)
        String priceStr = String.format("%.3f", price);
        return PRICE_PATTERN.matcher(priceStr).matches();
    }
    
    /**
     * Valide un prix à partir d'une chaîne
     */
    public static boolean isValidPriceString(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Remplacer la virgule par un point si nécessaire
            String normalized = priceStr.trim().replace(',', '.');
            double price = Double.parseDouble(normalized);
            return isValidPriceDT(price);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // VALIDATION DES DATES
    
    /**
     * Valide une date de naissance (âge minimum 16 ans)
     */
    public static boolean isValidBirthDate(LocalDate birthDate) {
        if (birthDate == null) return false;
        
        LocalDate today = LocalDate.now();
        LocalDate minBirthDate = today.minusYears(16);
        
        // Doit être avant la date d'il y a 16 ans
        return birthDate.isBefore(minBirthDate) || birthDate.equals(minBirthDate);
    }
    
    /**
     * Valide une date de naissance à partir d'une chaîne (format dd/MM/yyyy)
     */
    
    public static boolean isValidBirthDateString(String birthDateStr) {
        try {
            LocalDate birthDate = DateUtils.parseDate(birthDateStr);
            return isValidBirthDate(birthDate);
        } catch (Exception e) {
            return false;
        }
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

    // VALIDATION DES CRÉNEAUX HORAIRES
    
    /**
     * Valide un créneau horaire 
     */
    public static boolean isValidTimeSlot(LocalTime start, LocalTime end) {
        if (start == null || end == null) return false;
        
        // Le début doit être avant la fin
        if (!start.isBefore(end)) {
            return false;
        }
        
        // Durée minimum : 30 minutes
        // Durée maximum : 1 heure
        long durationMinutes = java.time.Duration.between(start, end).toMinutes();
        if (durationMinutes < 30 || durationMinutes > 60) {
            return false;
        }
        
        // Heures d'ouverture du restaurant (7h-21h)
        LocalTime opening = LocalTime.of(12, 0);
        LocalTime closing = LocalTime.of(14, 0);
        
        return !start.isBefore(opening) && !end.isAfter(closing);
    }
    
    /**
     * Valide qu'un créneau ne chevauche pas un autre
     */
    public static boolean hasNoTimeOverlap(LocalTime start1, LocalTime end1,
                                          LocalTime start2, LocalTime end2) {
        if (start1 == null || end1 == null || start2 == null || end2 == null) {
            return false;
        }
        
        // Deux créneaux se chevauchent si:
        // start1 < end2 ET start2 < end1
        boolean overlaps = start1.isBefore(end2) && start2.isBefore(end1);
        
        return !overlaps;
    }

    // VALIDATION DES CAPACITÉS ET LIMITES

    /**
     * Valide le nombre de réservations (limite: 3 par jour)
     */
    public static boolean isValidReservationCount(int count) {
        return count >= 0 && count <= 3;
    }
    
    /**
     * Vérifie si un étudiant peut faire une nouvelle réservation
     */
    public static boolean canMakeNewReservation(int currentReservationsToday) {
        return currentReservationsToday < 3;
    }
    
    /**
     * Valide une capacité de salle 
     */
    public static boolean isValidHallCapacity(int capacity) {
        return capacity > 0 && capacity <= 500;  
    }
    
    /**
     * Valide un nombre de personnes par réservation (1 à 3)
     */
    public static boolean isValidNumberOfPeople(int number) {
        return number >= 1 && number <= 3;
    }
    
    // VALIDATION GÉNÉRIQUE
    
    /**
     * Vérifie qu'une chaîne n'est pas vide
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Valide un entier dans une plage
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    /**
     * Valide un double dans une plage
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }
    
    /**
     * Valide un choix de menu (0 à maxOption)
     */
    public static boolean isValidMenuChoice(int choice, int maxOption) {
        return choice >= 0 && choice <= maxOption;
    }

    // MESSAGES D'ERREUR

    
    /**
     * Retourne le message d'erreur pour un email invalide
     */
    public static String getEmailErrorMessage() {
        return "L'email doit être au format : votrenom@ihec.ucar.tn";
    }
    
    /**
     * Retourne le message d'erreur pour un mot de passe invalide
     */
    
    public static String getPasswordErrorMessage() {
        return "Le mot de passe doit contenir au moins 6 caractères";
    }
    
    /**
     * Retourne le message d'erreur pour l'âge
     */
    public static String getAgeErrorMessage() {
        return "Vous devez avoir au moins 16 ans pour utiliser ce service";
    }
    
    /**
     * Retourne le message d'erreur pour le prix
     */
    public static String getPriceErrorMessage() {
        return "Le prix doit être compris entre 0.200 DT et 5.000 DT";
    }
    
    /**
     * Retourne le message d'erreur pour les réservations
     */
    public static String getReservationLimitErrorMessage() {
        return "Limite atteinte : maximum 3 réservations par jour";
    }
    
    /**
     * Retourne le message d'erreur pour un créneau horaire
     */
    
    public static String getTimeSlotErrorMessage() {
        return "Le créneau horaire doit :\n" +
               "- Être entre 7h et 21h\n" +
               "- Durée : 30min à 3h\n" +
               "- Heure début < Heure fin";
    }
    
    /**
     * Retourne le message d'erreur pour un numéro de téléphone
     */
    
    public static String getPhoneErrorMessage() {
        return "Le numéro de téléphone doit :\n" +
               "- Contenir 8 chiffres\n" +
               "- Commencer par 2, 5 ou 9\n" +
               "- Exemple : 50123456";
    }
    

    // MÉTHODES DE FORMATAGE
 
    
    /**
     * Formate un prix en dinars tunisiens
     */
    public static String formatPriceDT(double price) {
        return String.format("%.3f DT", price);
    }
    
    /**
     * Formate un prix à partir d'une chaîne
     */
    public static String formatPriceString(String priceStr) {
        try {
            double price = Double.parseDouble(priceStr.replace(',', '.'));
            return formatPriceDT(price);
        } catch (Exception e) {
            return "Prix invalide";
        }
    }
    
    /**
     * Formate un numéro de téléphone
     */
    public static String formatPhoneNumber(String phone) {
        if (!isValidTunisianPhone(phone)) {
            return phone; // Retourne tel quel si invalide
        }
        
        String cleaned = phone.replaceAll("[\\s.-]", "");
        // Format: XX XXX XXX
        return cleaned.substring(0, 2) + " " + 
               cleaned.substring(2, 5) + " " + 
               cleaned.substring(5, 8);
    }
}