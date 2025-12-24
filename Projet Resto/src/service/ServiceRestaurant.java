package service;

import model.Etudiant;
import model.Salle;
import model.Menu;
import model.Staff;
import exception.ChampsVideException;
import exception.EtudiantDejaInscritException;
import exception.SallePleineException;

import java.util.ArrayList;

public class ServiceRestaurant {

    private static ArrayList<Etudiant> etudiants = new ArrayList<>();
    private static ArrayList<Staff> staffs = new ArrayList<>();
    private static ArrayList<Menu> menus = new ArrayList<>();
    private static Salle salle = new Salle(50);

    // Étudiants
    public static void inscrireEtudiant(Etudiant e)
            throws ChampsVideException, EtudiantDejaInscritException, SallePleineException {

        if (e.getCin().isEmpty() || e.getNom().isEmpty() || e.getPrenom().isEmpty())
            throw new ChampsVideException("Tous les champs doivent être remplis !");

        for (Etudiant ex : etudiants) {
            if (ex.getCin().equals(e.getCin()))
                throw new EtudiantDejaInscritException("Étudiant déjà inscrit !", e.getCin());
        }

        if (!salle.ajouterReservation()) {
            int manque = salle.getReservations() - salle.getCapacite() + 1;
            throw new SallePleineException(manque);
        }

        etudiants.add(e);
    }

    public static int getNombreReservations() { return etudiants.size(); }
    public static ArrayList<Etudiant> getEtudiants() { return etudiants; }

    // Staff
    public static void ajouterStaff(Staff s) { staffs.add(s); }
    public static int getNombreStaff() { return staffs.size(); }

    // Menu
    public static void ajouterMenu(Menu m) { 
    	menus.add(m); 
    }
    public static ArrayList<Menu> getMenus() { 
    	return menus; 
    }

    // Salle
    public static Salle getSalle() { return salle; }
}
