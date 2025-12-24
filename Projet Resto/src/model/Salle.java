package model;

public class Salle {
    private int capacite;
    private int reservations;

    public Salle(int capacite) {
        this.capacite = capacite;
        this.reservations = 0;
    }

    public int getCapacite() { return capacite; }
    
    public void setCapacite(int capacite) { 
    	this.capacite = capacite; 
    }
    public void setReservations(int reservations) {
		this.reservations = reservations;
	}

    public int getReservations() { return reservations; }

    public boolean ajouterReservation() {
        if (reservations < capacite) {
            reservations++;
            return true;
        }
        return false;
    }

    public int placesManquantes() {
        return reservations - capacite;
    } 
}

