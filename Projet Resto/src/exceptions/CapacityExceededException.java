package exceptions;
// Exception quand le restaurant universitaire est plein (capacité dépassée pour le déjeuner)
public class CapacityExceededException extends Exception {
    
    private static final long serialVersionUID = 1L;
    private int maxCapacity;
    private int currentCapacity;
    
    public CapacityExceededException(String message) {
        super(message);
    }
    
    public CapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CapacityExceededException(String message, int maxCapacity, int currentCapacity) {
        super(message);
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public int getCurrentCapacity() {
        return currentCapacity;
    }
    
    public int getAvailableCapacity() {
        return maxCapacity - currentCapacity;
    }
    
    @Override
    public String toString() {
        if (maxCapacity > 0) {
            return String.format("Capacité dépassée : %s (Places réservées : %d/%d, Disponibles : %d)", 
                getMessage(), currentCapacity, maxCapacity, getAvailableCapacity());
        }
        return "Capacité dépassée : " + getMessage();
    }
    
    // Messages d'erreur prédéfinis - ADAPTÉS AU RESTAURANT UNIVERSITAIRE
    public static CapacityExceededException restaurantFull(int maxCapacity, int currentCapacity) {
        return new CapacityExceededException(
            "Désolé, toutes les places pour le déjeuner sont réservées. " +
            "Veuillez réessayer demain ou consulter les disponibilités pour les prochains jours.",
            maxCapacity,
            currentCapacity
        );
    }
    
    public static CapacityExceededException lunchFull(int maxCapacity, int currentCapacity) {
        int disponibles = maxCapacity - currentCapacity;
        if (disponibles <= 0) {
            return new CapacityExceededException(
                "Le restaurant universitaire est complet pour aujourd'hui . " +
                "Plus aucune place disponible. Réservez pour demain !",
                maxCapacity,
                currentCapacity
            );
        }
        return new CapacityExceededException(
            "Seulement " + disponibles + " place(s) restante(s) pour le déjeuner.",
            maxCapacity,
            currentCapacity
        );
    }
    
    public static CapacityExceededException dailyLimitReached() {
        return new CapacityExceededException(
            "Vous avez déjà réservé votre repas pour aujourd'hui. " +
            "Une seule réservation par jour est autorisée (déjeuner à 12h00)."
        );
    }
    
    public static CapacityExceededException weeklyLimitReached(int maxPerWeek) {
        return new CapacityExceededException(
            "Vous avez atteint le nombre maximum de réservations pour cette semaine (" + maxPerWeek + " repas). " +
            "Revenez la semaine prochaine."
        );
    }
}
