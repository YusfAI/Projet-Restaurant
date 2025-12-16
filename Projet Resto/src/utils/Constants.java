package utils;
public final class Constants {

   
    
    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_ADMIN = "ADMIN";

    public static final String RESERVATION_STATUS_CONFIRMED = "CONFIRMÉE";
    public static final String RESERVATION_STATUS_CANCELLED = "ANNULEE";
    public static final String RESERVATION_STATUS_PENDING = "EN_ATTENTE";
    public static final String RESERVATION_STATUS_COMPLETED = "TERMINÉE";

    public static final String MEAL_TYPE_LUNCH = "DEJEUNER";

    public static final int MAX_RESERVATIONS_PER_STUDENT_PER_DAY = 3;
    public static final int MIN_HOURS_BEFORE_CANCELLATION = 2;
    public static final int MIN_HOURS_BEFORE_RESERVATION = 1;
    public static final int MAX_CAPACITY_PER_HALL = 200;
    public static final int MIN_AGE_FOR_REGISTRATION = 16;
    

    public static final double MIN_MEAL_PRICE_DT = 0.200;
    public static final double MAX_MEAL_PRICE_DT = 5.000;
    
    // Prix par défaut 

    public static final double DEFAULT_LUNCH_PRICE_DT = 0.200;

    // FORMATS DE DATE ET HEURE (pour DateUtils)
    
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String DATE_FORMAT_DB = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";

    // HORAIRES DU RESTAURANT (12h-14h)

    
    public static final String RESTAURANT_OPENING_TIME = "12:00";
    public static final String RESTAURANT_CLOSING_TIME = "14:00";
    
    // Créneaux horaires standards
    public static final String LUNCH_TIME_SLOT = "11:30 - 14:30";

    // DOMAINES EMAIL (IHEc spécifique)
    
    public static final String STUDENT_EMAIL_DOMAIN = "@ihec.ucar.tn";
    public static final String STAFF_EMAIL_DOMAIN = "@ihec.ucar.tn";
    public static final String ADMIN_EMAIL_DOMAIN = "@ihec.ucar.tn";

    // FORMATS DE NUMÉROS (pour ValidationUtils)
    // Format de téléphone tunisien (8 chiffres commençant par 2,5,9)
    public static final String PHONE_REGEX_TUNISIA = "^[259]\\d{7}$";
    
    // Format CIN (8 chiffres)
    public static final String CIN_REGEX = "^\\d{8}$";
    
    // Format de carte étudiante (à adapter selon l'établissement)
    public static final String STUDENT_CARD_REGEX = "^[A-Z0-9]{6,12}$";
    
    // Format email IHEc
    public static final String EMAIL_IHEc_REGEX = "^[A-Za-z0-9._%+-]+@ihec\\.ucar\\.tn$";
    
    // MESSAGES D'ERREUR GÉNÉRIQUES

    
    public static final String ERROR_INVALID_INPUT = "Entrée invalide.";
    public static final String ERROR_DATE_PAST = "La date ne peut pas être dans le passé.";
    public static final String ERROR_DATE_FUTURE = "La date ne peut pas être dans le futur.";
    public static final String ERROR_TIME_SLOT = "Créneau horaire invalide.";
    public static final String ERROR_CAPACITY_EXCEEDED = "Capacité insuffisante.";
    public static final String ERROR_RESERVATION_LIMIT = "Limite de réservations atteinte.";
    public static final String ERROR_MINIMUM_AGE = "Âge minimum non atteint (16 ans requis).";
    public static final String ERROR_PRICE_RANGE = "Le prix doit être entre 0.200 DT et 5.000 DT.";
    

    
    // PARAMÈTRES SYSTÈME   
    // Largeur console pour l'affichage
    public static final int CONSOLE_WIDTH = 80;
    
    // Tentatives de connexion maximum
    public static final int MAX_LOGIN_ATTEMPTS = 3;
    
    // Taille des pages pour l'affichage paginé
    public static final int ITEMS_PER_PAGE = 10;
    
    // Délai d'expiration de session (en minutes)
    public static final int SESSION_TIMEOUT_MINUTES = 30;
    

    // CODES DE SORTIE
    public static final int EXIT_CODE_SUCCESS = 0;
    public static final int EXIT_CODE_ERROR = 1;
    public static final int EXIT_CODE_INVALID_INPUT = 2;
    public static final int EXIT_CODE_UNAUTHORIZED = 3;
    

    // UNITÉS

    
    public static final String CURRENCY_SYMBOL = "DT";
    public static final String CURRENCY_NAME = "Dinar Tunisien";
    

}
    
