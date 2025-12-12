package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
	
	private static int counter=1;
	
	private String reservationId; 
	private User user; //l'utilisateur qui fait la reservation on peut utiliser que l'etudiant 
	private Meal meal; //le repas reservé
	private LocalDate date; // date de la reservation
	private LocalTime heure; // heure de la reseervation
	private int nbplaces; // le nb de place reservé
	private String status; // pending, confirmé , cancelled
	 
	
	//methode pour generer le'id de reservation unique 
	private static String generateReservationId() {
		return "R" + (counter++); //retourne par exemple R1/R2...
	}
	//constructeur parametré
	public Reservation(User user, Meal meal, LocalDate date, LocalTime heure, int nbplaces,
			String status) {
		super();
		this.reservationId = generateReservationId();
		this.user = user;
		this.meal = meal;
		this.date = date;
		this.heure = heure;
		this.nbplaces = nbplaces;
		this.status = status;
	}
	
	//constructeur parametré 
	
	public String getReservationId() {
		return reservationId;
	}


	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Meal getMeal() {
		return meal;
	}


	public void setMeal(Meal meal) {
		this.meal = meal;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getHeure() {
		return heure;
	}


	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}


	public int getNbplaces() {
		return nbplaces;
	}


	public void setNbplaces(int nbplaces) {
		this.nbplaces = nbplaces;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", user=" + user + ", meal=" + meal + ", date=" + date
				+ ", heure=" + heure + ", nbplaces=" + nbplaces + ", status=" + status + "]";
	}
	
}

