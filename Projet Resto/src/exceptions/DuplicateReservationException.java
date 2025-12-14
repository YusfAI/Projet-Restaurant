package exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

// Exception quand l'étudiant a déjà réservé son déjeuner
public class DuplicateReservationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    private int existingReservationId;
    private Date reservationDate;
    private int userId;
    
    public DuplicateReservationException(String message) {
        super(message);
    }
    
    public DuplicateReservationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DuplicateReservationException(String message, int userId, int existingReservationId) {
        super(message);
        this.userId = userId;
        this.existingReservationId = existingReservationId;
    }
    
    public DuplicateReservationException(String message, int userId, int existingReservationId, Date reservationDate) {
        super(message);
        this.userId = userId;
        this.existingReservationId = existingReservationId;
        this.reservationDate = reservationDate;
    }
    
    public int getExistingReservationId() {
        return existingReservationId;
    }
    
    public Date getReservationDate() {
        return reservationDate;
    }
    
    public int getUserId() {
        return userId;
    }
    
    @Override
    public String toString() {
        if (existingReservationId > 0) {
            return String.format("Réservation en double : %s (Réservation existante #%d)", 
                getMessage(), existingReservationId);
        }
        return "Réservation en double : " + getMessage();
    }
    
    // Messages d'erreur prédéfinis - ADAPTÉS AU DÉJEUNER UNIQUEMENT
    public static DuplicateReservationException alreadyReservedToday(int userId, int reservationId, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new DuplicateReservationException(
            "Vous avez déjà réservé votre déjeuner pour le " + sdf.format(date) + " (12h00). " +
            "Une seule réservation par jour est autorisée. " +
            "Vous pouvez modifier ou annuler votre réservation existante.",
            userId,
            reservationId,
            date
        );
    }
    
    public static DuplicateReservationException pendingReservationExists(int userId, int reservationId) {
        return new DuplicateReservationException(
            "Vous avez déjà une réservation en attente de confirmation pour le déjeuner. " +
            "Veuillez la confirmer ou l'annuler avant d'en créer une nouvelle.",
            userId,
            reservationId
        );
    }
    
    public static DuplicateReservationException alreadyReservedThisWeek(int userId, int count, int maxPerWeek) {
        return new DuplicateReservationException(
            "Vous avez déjà " + count + " réservation(s) cette semaine. " +
            "Maximum autorisé : " + maxPerWeek + " repas par semaine.",
            userId,
            0
        );
    }
}



