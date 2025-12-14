package exceptions;

public class ReservationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    private int reservationId;
    private ReservationErrorType errorType;
    
    public enum ReservationErrorType {
        CREATION_FAILED,
        UPDATE_FAILED,
        CANCELLATION_FAILED,
        NOT_FOUND,
        INVALID_DATA,
        SYSTEM_ERROR
    }
    
    public ReservationException(String message) {
        super(message);
        this.errorType = ReservationErrorType.SYSTEM_ERROR;
    }
    
    public ReservationException(String message, Throwable cause) {
        super(message, cause);
        this.errorType = ReservationErrorType.SYSTEM_ERROR;
    }
    
    public ReservationException(String message, ReservationErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    
    public ReservationException(String message, int reservationId, ReservationErrorType errorType) {
        super(message);
        this.reservationId = reservationId;
        this.errorType = errorType;
    }
    
    public ReservationException(String message, int reservationId, ReservationErrorType errorType, Throwable cause) {
        super(message, cause);
        this.reservationId = reservationId;
        this.errorType = errorType;
    }
    
    public int getReservationId() {
        return reservationId;
    }
    
    public ReservationErrorType getErrorType() {
        return errorType;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Erreur de réservation : ");
        sb.append(getMessage());
        
        if (errorType != null) {
            sb.append(" [Type : ").append(errorType).append("]");
        }
        
        if (reservationId > 0) {
            sb.append(" (Réservation #").append(reservationId).append(")");
        }
        
        return sb.toString();
    }
    
    // Messages d'erreur prédéfinis - ADAPTÉS AU DÉJEUNER
    public static ReservationException notFound(int reservationId) {
        return new ReservationException(
            "La réservation #" + reservationId + " est introuvable. " +
            "Elle a peut-être été annulée ou n'existe pas.",
            reservationId,
            ReservationErrorType.NOT_FOUND
        );
    }
    
    public static ReservationException creationFailed(String reason) {
        return new ReservationException(
            "Échec de la réservation du déjeuner : " + reason + ". " +
            "Veuillez réessayer ou contacter le service restauration.",
            ReservationErrorType.CREATION_FAILED
        );
    }
    
    public static ReservationException cancellationFailed(int reservationId, String reason) {
        return new ReservationException(
            "Impossible d'annuler votre réservation pour le déjeuner : " + reason,
            reservationId,
            ReservationErrorType.CANCELLATION_FAILED
        );
    }
    
    public static ReservationException alreadyCancelled(int reservationId) {
        return new ReservationException(
            "Cette réservation de déjeuner a déjà été annulée.",
            reservationId,
            ReservationErrorType.INVALID_DATA
        );
    }
    
    public static ReservationException tooLateToCancel(int reservationId) {
        return new ReservationException(
            "Il est trop tard pour annuler votre réservation. " +
            "Les annulations doivent être effectuées avant 10h00 le jour même.",
            reservationId,
            ReservationErrorType.CANCELLATION_FAILED
        );
    }
    
    public static ReservationException invalidData(String fieldName) {
        return new ReservationException(
            "Données invalides : le champ '" + fieldName + "' est requis ou incorrect pour votre réservation.",
            ReservationErrorType.INVALID_DATA
        );
    }
    
    public static ReservationException systemError() {
        return new ReservationException(
            "Une erreur système s'est produite lors de votre réservation. " +
            "Veuillez réessayer dans quelques instants ou contacter le support technique.",
            ReservationErrorType.SYSTEM_ERROR
        );
    }
    
    public static ReservationException lunchOnly() {
        return new ReservationException(
            "Seul le déjeuner (12h00) est disponible à la réservation. " +
            "Le restaurant universitaire ne sert pas de petit-déjeuner ni de dîner.",
            ReservationErrorType.INVALID_DATA
        );
    }
}
