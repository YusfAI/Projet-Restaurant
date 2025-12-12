package model;

public class Student extends User {
	
	private String numCarte; //le numero de la carte etudiant
	private String niveau; //le niveau scolaire
	private String specialite; //la specialité 
	
	
	//constructeur parametré
	public Student(String id, String nom, String email, String password, String numCarte, String niveau,
			String specialite) {
		super(id, nom, email, password);
		this.numCarte = numCarte;
		this.niveau = niveau;
		this.specialite = specialite;
	}

	//getters et setters
	public String getNumCarte() {
		return numCarte;
	}



	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}



	public String getNiveau() {
		return niveau;
	}



	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}



	public String getSpecialite() {
		return specialite;
	}



	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}


	//pour identifier le type d'utilisateur
	@Override
	public String getRole() {
		return "STUDENT";
	}

	//methode toString
	@Override
	public String toString() {
		return "Student [numCarte=" + numCarte + ", niveau=" + niveau + ", specialite=" + specialite + super.toString();
	}
}

