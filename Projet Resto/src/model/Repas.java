package model;

import java.time.LocalDate;
public class Repas {

    private String nom;
    double prix = 0.2;
    private LocalDate date;

    public Repas(String nom, LocalDate date) {
        this.nom = nom;
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDate() {
        return date;
    }

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Repas [nom=" + nom + ", date=" + date + "]";
	}
    
}

