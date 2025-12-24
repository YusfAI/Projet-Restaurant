package exception;
public class SallePleineException extends Exception {

    private int placesManquantes;

    public SallePleineException(int placesManquantes) {
        super("La salle est pleine ! Il manque " + placesManquantes + " place(s).");
        this.placesManquantes = placesManquantes;
    }

    public SallePleineException(int placesManquantes, Throwable cause) {
        super("La salle est pleine ! Il manque " + placesManquantes + " place(s).", cause);
        this.placesManquantes = placesManquantes;
    }

    public int placesManquantes() {
        return placesManquantes;
    }

    public String getMessageDetaille() {
        return super.getMessage() + " → Suggestions: Ajouter " + placesManquantes + " chaise(s) ou reporter l'inscription.";
    }
}


