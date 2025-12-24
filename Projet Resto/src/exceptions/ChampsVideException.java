package exception;

public class ChampsVideException extends Exception {

    private String champVide;

    public ChampsVideException(String message) {
        super(message);
        this.champVide = null;
    }

    public ChampsVideException(String message, String champVide) {
        super(message);
        this.champVide = champVide;
    }

    public ChampsVideException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getChampVide() {
        return champVide;
    }

    @Override
    public String toString() {
        if (champVide != null) {
            return super.getMessage() + " (Champ vide: " + champVide + ")";
        }
        return super.getMessage();
    }
}
