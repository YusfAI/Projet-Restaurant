package exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

// Exception pour les créneaux horaires invalides (seulement 12h00 est valide)
public class InvalidTimeSlotException extends Exception {
    
    private static final long serialVersionUID = 1L;
    private Date requestedTime;
    private String reason;
    
    public InvalidTimeSlotException(String message) {
        super(message);
    }
    
    public InvalidTimeSlotException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidTimeSlotException(String message, Date requestedTime) {
        super(message);
        this.requestedTime = requestedTime;
    }
    
    public InvalidTimeSlotException(String message, Date requestedTime, String reason) {
        super(message);
        this.requestedTime = requestedTime;
        this.reason = reason;
    }
    
    public Date getRequestedTime() {
        return requestedTime;
    }
    
    public String getReason() {
        return reason;
    }
    
    public boolean isPastTime() {
        if (requestedTime != null) {
            return requestedTime.before(new Date());
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Créneau horaire invalide : ");
        sb.append(getMessage());
        
        if (requestedTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sb.append(" (Heure demandée : ").append(sdf.format(requestedTime)).append(")");
        }
        
        if (reason != null) {
            sb.append(" - ").append(reason);
        }
        
        return sb.toString();
    }
    
    // Messages d'erreur prédéfinis - ADAPTÉS AU DÉJEUNER À 12H UNIQUEMENT
    public static InvalidTimeSlotException pastDate(Date requestedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new InvalidTimeSlotException(
            "Impossible de réserver pour une date passée. " +
            "La date du " + sdf.format(requestedTime) + " est déjà écoulée. " +
            "Vous pouvez réserver pour demain ou les jours suivants.",
            requestedTime,
            "Date dans le passé"
        );
    }
    
    public static InvalidTimeSlotException wrongTime(Date requestedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return new InvalidTimeSlotException(
            "Le restaurant universitaire ne sert que le déjeuner à 12h00. " +
            "L'heure demandée (" + sdf.format(requestedTime) + ") n'est pas valide. " +
            "Veuillez réserver pour 12h00.",
            requestedTime,
            "Heure invalide"
        );
    }
    
    public static InvalidTimeSlotException tooLateToReserve(Date requestedTime) {
        return new InvalidTimeSlotException(
            "Il est trop tard pour réserver le déjeuner d'aujourd'hui. " +
            "Les réservations doivent être effectuées avant 10h00 le jour même. " +
            "Vous pouvez réserver pour demain.",
            requestedTime,
            "Délai de réservation dépassé"
        );
    }
    
    public static InvalidTimeSlotException tooEarlyToReserve(Date requestedTime, int maxDaysInAdvance) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new InvalidTimeSlotException(
            "Vous ne pouvez pas réserver si loin à l'avance. " +
            "Réservations possibles jusqu'à " + maxDaysInAdvance + " jours maximum. " +
            "(Date demandée : " + sdf.format(requestedTime) + ")",
            requestedTime,
            "Trop à l'avance"
        );
    }
    
    public static InvalidTimeSlotException weekendClosed() {
        return new InvalidTimeSlotException(
            "Le restaurant universitaire est fermé le week-end (samedi et dimanche). " +
            "Veuillez réserver pour un jour de semaine (lundi à vendredi).",
            null,
            "Fermé le week-end"
        );
    }
    
    public static InvalidTimeSlotException restaurantClosed(Date requestedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(requestedTime);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return weekendClosed();
        }
        
        return new InvalidTimeSlotException(
            "Le restaurant est fermé le " + sdf.format(requestedTime) + ". " +
            "Il peut s'agir d'un jour férié ou d'une fermeture exceptionnelle.",
            requestedTime,
            "Restaurant fermé"
        );
    }
    
    public static InvalidTimeSlotException lunchTimeOnly() {
        return new InvalidTimeSlotException(
            "Le restaurant universitaire ne sert que le déjeuner à 12h00 (midi). " +
            "Horaire de service : 12h00 - 14h00 du lundi au vendredi.",
            null,
            "Déjeuner uniquement"
        );
    }
}

