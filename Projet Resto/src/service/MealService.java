package service;

import model.Meal;
import model.DataStore;
import model.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MealService {
    
    /**
     * Récupère tous les repas disponibless
     * @return Liste de tous les repas
     */
    public List<Meal> getAllMeals() {
        return DataStore.getMeals();
    }
    
    
    /**
     * Récupère les repas disponibles pour une date spécifique
     * @param date La date recherchée
     * @return Liste des repas disponibles à cette date
     */
    public List<Meal> getAvailableMealsByDate(LocalDate date) {
        return DataStore.getAvailableMealsByDate(date);
    }
    
    
    /**
     * Récupère les repas disponibles pour aujourd'hui
     * @return Liste des repas disponibles aujourd'hui
     */
    public List<Meal> getTodayMeals() {
        return getAvailableMealsByDate(LocalDate.now());
    }
    
    
    /**
     * Recherche un repas par son ID
     * @param mealId L'identifiant du repas
     * @return Le repas trouvé ou null
     */
    public Meal findMealById(String mealId) {
        return DataStore.findMealById(mealId);
    }
    
    
    /**
     * Vérifie si un repas est disponible à l'heure actuelle
     * @param meal Le repas à vérifier
     * @return true si le repas est disponible maintenant
     */
    public boolean isMealAvailableNow(Meal meal) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        // Vérifie si c'est la bonne date
        if (!meal.getDateRepas().equals(today)) {
            return false;
        }
        
        // Vérifie si l'heure actuelle est dans le créneau
        if (now.isBefore(meal.getHeureDebut()) || now.isAfter(meal.getHeureFin())) {
            return false;
        }
        
        return meal.isDisponible();
    }
    
    
    /**
     * Vérifie si un repas peut être réservé (date future ou aujourd'hui dans le créneau)
     * @param meal Le repas à vérifier
     * @return true si le repas peut être réservé
     */
    public boolean canBeReserved(Meal meal) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        // Si le repas n'est pas disponible
        if (!meal.isDisponible()) {
            return false;
        }
        
        // Si le repas est pour une date future
        if (meal.getDateRepas().isAfter(today)) {
            return true;
        }
        
        // Si le repas est pour aujourd'hui, vérifier l'heure
        if (meal.getDateRepas().equals(today)) {
            return now.isBefore(meal.getHeureFin());
        }
        
        // Si le repas est dans le passé
        return false;
    }
    
    
    /**
     * Ajoute un nouveau repas
     * @param meal Le repas à ajouter
     */
    public void addMeal(Meal meal) {
        DataStore.addMeal(meal);
    }
    
    
    /**
     * Modifie la disponibilité d'un repas
     * @param mealId L'identifiant du repas
     * @param disponible true pour rendre disponible, false sinon
     */
    public void updateMealAvailability(String mealId, boolean disponible) {
        Meal meal = DataStore.findMealById(mealId);
        if (meal != null) {
            meal.setDisponible(disponible);
        }
    }
    
    
    /**
     * Supprime un repas
     * @param mealId L'identifiant du repas à supprimer
     * @return true si la suppression a réussi
     */
    public boolean deleteMeal(String mealId) {
        return DataStore.removeMeal(mealId);
    }
    
    
    /**
     * Affiche les détails d'un repas (pour debug/console)
     * @param meal Le repas à afficher
     * @return String représentant le repas
     */
    public String getMealDetails(Meal meal) {
        return String.format("ID: %s | Description: %s | Prix: %.2f DT | Date: %s | Horaire: %s - %s | Disponible: %s",
                meal.getMealId(),
                meal.getDescription(),
                meal.getPrix(),
                meal.getDateRepas(),
                meal.getHeureDebut(),
                meal.getHeureFin(),
                meal.isDisponible() ? "Oui" : "Non");
    }
}
