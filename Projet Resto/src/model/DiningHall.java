package model;

public class DiningHall {
	
	private String nom; // ces deux attributs sont a discuter !!
	private String hallId;
	private int capacite; // la capcite totale de la salle
	private int placesrestantes;
	
	//constructeur parametré
	public DiningHall(String nom, String hallId, int capacite, int placesrestantes) {
		super();
		this.nom = nom;// a discuter
		this.hallId = hallId;
		this.capacite = capacite;
		this.placesrestantes = placesrestantes;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getHallId() {
		return hallId;
	}


	public void setHallId(String hallId) {
		this.hallId = hallId;
	}


	public int getCapacite() {
		return capacite;
	}


	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}


	public int getPlacesrestantes() {
		return placesrestantes;
	}


	public void setPlacesrestantes(int placesrestantes) {
		this.placesrestantes = placesrestantes;
	}


	@Override
	public String toString() {
		return "DiningHall [nom=" + nom + ", hallId=" + hallId + ", capacite=" + capacite + ", placesrestantes="
				+ placesrestantes + "]";
	}

}

