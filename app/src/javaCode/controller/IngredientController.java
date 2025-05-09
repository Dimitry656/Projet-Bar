package javaCode.controller;

import javaCode.Entities.Ingredient;
import javaCode.dao.IngredientDAO;
import java.util.List;

/**
 * IngredientController centralise la logique métier liée aux ingrédients.
 * Il fournit des méthodes CRUD pour gérer les ingrédients ainsi que des méthodes de gestion de stock :
 * - Vérifier la disponibilité du volume requis.
 * - Mettre à jour le stock après utilisation (par exemple, lors de la préparation d'un cocktail).
 * - Obtenir la quantité totale disponible.
 */
public class IngredientController {

    private IngredientDAO ingredientDAO;

    public IngredientController() {
        this.ingredientDAO = new IngredientDAO();
    }

    /**
     * Ajoute un nouvel ingrédient.
     *
     * @param ingredient l'ingrédient à ajouter.
     * @return true si l'insertion réussit, false sinon.
     */
    public boolean addIngredient(Ingredient ingredient) {
        return ingredientDAO.insert(ingredient);
    }

    /**
     * Met à jour un ingrédient existant.
     *
     * @param ingredient l'ingrédient à mettre à jour.
     * @return true si la mise à jour réussit, false sinon.
     */
    public boolean updateIngredient(Ingredient ingredient) {
        return ingredientDAO.update(ingredient);
    }

    /**
     * Supprime un ingrédient par son identifiant.
     *
     * @param id l'identifiant de l'ingrédient à supprimer.
     * @return true si la suppression réussit, false sinon.
     */
    public boolean deleteIngredient(int id) {
        return ingredientDAO.delete(id);
    }

    /**
     * Récupère un ingrédient par son identifiant.
     *
     * @param id l'identifiant de l'ingrédient.
     * @return l'ingrédient correspondant, ou null s'il n'existe pas.
     */
    public Ingredient getIngredientById(int id) {
        return ingredientDAO.findById(id);
    }

    public Ingredient getIngredientByNom(String nom) {return ingredientDAO.findByNom(nom);}

    /**
     * Récupère la liste de tous les ingrédients.
     *
     * @return une liste d'ingrédients.
     */
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }

    /**
     * Vérifie si la quantité disponible pour un ingrédient est suffisante pour couvrir le volume requis.
     *
     * @param ingredient  l'ingrédient concerné.
     * @param volumeNeeded le volume requis.
     * @return true si la quantité disponible est supérieure ou égale au volume requis, false sinon.
     */
    public boolean isVolumeAvailable(Ingredient ingredient, double volumeNeeded) {
        return ingredient.getQuantiteDisponible() >= volumeNeeded;
    }

    /**
     * Met à jour le stock de l'ingrédient après utilisation.
     * Déduit le volume utilisé de la quantité disponible et met à jour l'ingrédient en base.
     *
     * @param ingredient l'ingrédient concerné.
     * @param volumeUsed le volume utilisé.
     * @return true si l'opération réussit, false sinon.
     */
    public boolean updateAfterService(Ingredient ingredient, double volumeUsed) {
        if (!isVolumeAvailable(ingredient, volumeUsed)) {
            return false;
        }
        double newQuantity = ingredient.getQuantiteDisponible() - volumeUsed;
        ingredient.setQuantiteDisponible(newQuantity);
        return ingredientDAO.update(ingredient);
    }

    /**
     * Retourne la quantité totale disponible pour l'ingrédient.
     *
     * @param ingredient l'ingrédient concerné.
     * @return la quantité disponible.
     */
    public double getTotalAvailableVolume(Ingredient ingredient) {
        return ingredient.getQuantiteDisponible();
    }
}
