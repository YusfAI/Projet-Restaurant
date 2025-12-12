package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Meal {
	
	private String mealId;        //identifiant unique des repas
	private String description;   //texte descriptive du plat(ingrediants, plats,etc..)
	private double prix;          //gestion des prix des repas 
	private LocalDate dateRepas;  //la date que le repas est servi
	private LocalTime heureDebut; //heure a partir de laquelle le repas est dispo
	private LocalTime heureFin;   //heure jusqu'a laquelle le repas est disponible 
	private boolean disponible;   //true/ false indique la dispnibilité du repas 
	
	
	// constructeur parametré
	public Meal(String mealId, String description, double prix, LocalDate dateRepas, LocalTime heureDebut,
			LocalTime heureFin, boolean disponible) {
		super();
		this.mealId = mealId;
		this.description = description;
		this.prix = prix;
		this.dateRepas = dateRepas;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.disponible = disponible;
	}


	// getters et setters
	public String getMealId() {
		return mealId;
	}



	public void setMealId(String mealId) {
		this.mealId = mealId;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public double getPrix() {
		return prix;
	}



	public void setPrix(double prix) {
		this.prix = prix;
	}



	public LocalDate getDateRepas() {
		return dateRepas;
	}



	public void setDateRepas(LocalDate dateRepas) {
		this.dateRepas = dateRepas;
	}



	public LocalTime getHeureDebut() {
		return heureDebut;
	}



	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}



	public LocalTime getHeureFin() {
		return heureFin;
	}



	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}



	public boolean isDisponible() {
		return disponible;
	}



	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}


	@Override
	public String toString() {
		return "Meal [mealId=" + mealId + ", description=" + description + ", prix=" + prix + ", dateRepas=" + dateRepas
				+ ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", disponible=" + disponible + "]";
	}
	
}
