package model;

public abstract class User {
	private String id; //l'identifiant unique de l'utilisateur
	private String nom; // le nom de l'utilisateur 
	private String email; // adresse mail
	private String password; //le mot de passe
	
	
	// le constructeur parametré
	public User(String id, String nom, String email, String password) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.password = password;
	}
	// constructeur parametré


	// getters et setters 
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	//oblige chaque sous-classe à définir le rôle spécifique de l'utilisateur.
	public abstract String getRole();


	@Override
	public String toString() {
		return "User [id=" + id + ", nom=" + nom + ", email=" + email + "]";
	}

}

