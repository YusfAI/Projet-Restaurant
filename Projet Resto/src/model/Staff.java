package model;

public class Staff extends User{
	
	private String service;  // cuisine,service,...
	private boolean actif; //salarie est actif ou non 
	
	//constructeur parametré
	public Staff(String id, String nom, String email, String password, String role, String service, boolean actif) {
		
		super(id, nom, email, password);

		this.service = service;
		this.actif = actif;
	}


	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public boolean isActif() {
		return actif;
	}


	public void setActif(boolean actif) {
		this.actif = actif;
	}
	//pour identifier le type d'utilisateur 
	@Override
	public String getRole() {
		return "STAFF";
	}
	
	
	
	

}
