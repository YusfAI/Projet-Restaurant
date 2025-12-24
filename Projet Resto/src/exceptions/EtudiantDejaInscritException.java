package exception;

public class EtudiantDejaInscritException extends Exception {

    private String cinEtudiant;

    public EtudiantDejaInscritException(String message) {
        super(message);
    }

    public EtudiantDejaInscritException(String message, String cinEtudiant) {
        super(message);
        this.cinEtudiant = cinEtudiant;
    }

    public EtudiantDejaInscritException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCinEtudiant() {
        return cinEtudiant;
    }

    @Override
    public String toString() {
        if (cinEtudiant != null) {
            return super.getMessage() + " (CIN: " + cinEtudiant + ")";
        }
        return super.getMessage();
    }
}

