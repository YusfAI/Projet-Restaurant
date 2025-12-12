package service;

import model.DiningHall;
import model.DataStore;
import model.Reservation;
import java.time.LocalDate;
import java.util.List;

public class DiningHallService {
    
    /**
     * Récupère toutes les salles de restaurationn
     * @return Liste de toutes les salles
     */
    public List<DiningHall> getAllDiningHalls() {
        return DataStore.getDiningHalls();
    }
    
    
    /**
     * Recherche une salle par son ID
     * @param hallId L'identifiant de la salle
     * @return La salle trouvée ou null
     */
    public DiningHall findDiningHallById(String hallId) {
        return DataStore.findDiningHallById(hallId);
    }
    
    
    /**
     * Réserve des places dans une salle
     * @param hallId L'identifiant de la salle
     * @param nbPlaces Le nombre de places à réserver
     * @return true si la réservation a réussi
     */
    public boolean reserverPlaces(String hallId, int nbPlaces) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null) {
            return false;
        }
        
        // Vérifier si assez de places disponibles
        if (hall.getPlacesrestantes() < nbPlaces) {
            return false;
        }
        
        // Réserver les places
        hall.setPlacesrestantes(hall.getPlacesrestantes() - nbPlaces);
        return true;
    }
    
    
    /**
     * Libère des places dans une salle
     * @param hallId L'identifiant de la salle
     * @param nbPlaces Le nombre de places à libérer
     * @return true si la libération a réussi
     */
    public boolean libererPlaces(String hallId, int nbPlaces) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null) {
            return false;
        }
        
        // Libérer les places (sans dépasser la capacité totale)
        int newPlacesRestantes = hall.getPlacesrestantes() + nbPlaces;
        if (newPlacesRestantes > hall.getCapacite()) {
            newPlacesRestantes = hall.getCapacite();
        }
        
        hall.setPlacesrestantes(newPlacesRestantes);
        return true;
    }
    
    
    /**
     * Vérifie si une salle est complète
     * @param hallId L'identifiant de la salle
     * @return true si la salle est complète
     */
    public boolean isFull(String hallId) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null) {
            return true; // Considérer comme complète si introuvable
        }
        
        return hall.getPlacesrestantes() == 0;
    }
    
    
    /**
     * Vérifie si une salle peut accueillir un nombre de places donné
     * @param hallId L'identifiant de la salle
     * @param nbPlaces Le nombre de places demandées
     * @return true si la salle a assez de places
     */
    public boolean hasAvailableCapacity(String hallId, int nbPlaces) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null) {
            return false;
        }
        
        return hall.getPlacesrestantes() >= nbPlaces;
    }
    
    
    /**
     * Calcule le taux d'occupation d'une salle
     * @param hallId L'identifiant de la salle
     * @return Le taux d'occupation en pourcentage (0-100)
     */
    public double getTauxOccupation(String hallId) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null || hall.getCapacite() == 0) {
            return 0.0;
        }
        
        int placesOccupees = hall.getCapacite() - hall.getPlacesrestantes();
        return (placesOccupees * 100.0) / hall.getCapacite();
    }
    
    
    /**
     * Récupère le nombre de places restantes dans une salle
     * @param hallId L'identifiant de la salle
     * @return Le nombre de places restantes
     */
    public int getPlacesRestantes(String hallId) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null) {
            return 0;
        }
        
        return hall.getPlacesrestantes();
    }
    
    
    /**
     * Ajoute une nouvelle salle de restauration
     * @param hall La salle à ajouter
     */
    public void addDiningHall(DiningHall hall) {
        DataStore.addDiningHall(hall);
    }
    
    
    /**
     * Réinitialise la capacité d'une salle (remet toutes les places disponibles)
     * @param hallId L'identifiant de la salle
     */
    public void resetCapacity(String hallId) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall != null) {
            hall.setPlacesrestantes(hall.getCapacite());
        }
    }
    
    
    /**
     * Affiche l'état de toutes les salles (pour console/debug)
     * @return String représentant l'état des salles
     */
    public String getAllHallsStatus() {
        StringBuilder status = new StringBuilder();
        status.append("=== ÉTAT DES SALLES DE RESTAURATION ===\n\n");
        
        for (DiningHall hall : DataStore.getDiningHalls()) {
            status.append(String.format("Salle: %s (ID: %s)\n", hall.getNom(), hall.getHallId()));
            status.append(String.format("  Capacité totale: %d places\n", hall.getCapacite()));
            status.append(String.format("  Places restantes: %d places\n", hall.getPlacesrestantes()));
            status.append(String.format("  Taux d'occupation: %.1f%%\n", getTauxOccupation(hall.getHallId())));
            status.append(String.format("  Statut: %s\n\n", isFull(hall.getHallId()) ? "COMPLÈTE" : "DISPONIBLE"));
        }
        
        return status.toString();
    }
    
    
    /**
     * Récupère les détails d'une salle spécifique
     * @param hallId L'identifiant de la salle
     * @return String représentant les détails de la salle
     */
    public String getHallDetails(String hallId) {
        DiningHall hall = DataStore.findDiningHallById(hallId);
        
        if (hall == null) {
            return "Salle introuvable";
        }
        
        return String.format("Salle: %s | ID: %s | Capacité: %d | Disponible: %d | Occupation: %.1f%%",
                hall.getNom(),
                hall.getHallId(),
                hall.getCapacite(),
                hall.getPlacesrestantes(),
                getTauxOccupation(hallId));
    }
}
