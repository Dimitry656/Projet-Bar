package javaCode.controller;

import javaCode.Entities.Cocktail;
import javaCode.Entities.Ingredient;
import javaCode.Entities.Boisson;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javaCode.dao.CocktailDAO;

/**
 * Le CocktailController centralise la logique métier liée aux cocktails.
 * En plus des opérations CRUD (ajouter, mettre à jour, supprimer, récupérer),
 * il offre des méthodes pour servir un cocktail (vérifier et mettre à jour le stock)
 * et pour obtenir la liste des cocktails disponibles avec le nombre de portions réalisables
 * compte tenu du stock actuel de boissons et d'ingrédients.
 *  Il permet :
 *  - d'enregistrer un cocktail en base (si cocktail.isSauvegarde() == true),
 *  - de créer des cocktails "sur mesure" sans les enregistrer en base,
 *  - de servir un cocktail (mettre à jour les stocks de boissons et d'ingrédients),
 * - de récupérer la liste des cocktails disponibles (et le nombre de portions réalisables).
 */
public class CocktailController {

    private CocktailDAO cocktailDAO;
    private StockController stockController;
    private IngredientStockController ingredientStockController;

    public CocktailController() {
        this.cocktailDAO = new CocktailDAO();
        this.stockController = new StockController();
        this.ingredientStockController = new IngredientStockController();
    }

    /**
     * Enregistre le cocktail en base si la propriété sauvegarde est true.
     * Si sauvegarde est false (cocktail sur mesure), il ne sera pas enregistré,
     * mais pourra être servi directement.
     *
     * @param cocktail Le cocktail à enregistrer ou à créer en mémoire.
     * @return true si l'opération se déroule correctement, false sinon.
     */
    public boolean addCocktail(Cocktail cocktail) {
        if (cocktail.isSauvegarde()) {
            return cocktailDAO.insert(cocktail);
        } else {
            System.out.println("Cocktail sur mesure : non enregistré en base.");
            return true;
        }
    }

    /**
     * Met à jour un cocktail existant.
     * @param cocktail le cocktail à mettre à jour
     * @return true si l'opération réussit, false sinon.
     */
    public boolean updateCocktail(Cocktail cocktail) {
        return cocktailDAO.update(cocktail);
    }

    /**
     * Supprime un cocktail par son identifiant.
     * @param id l'identifiant du cocktail à supprimer
     * @return true si l'opération réussit, false sinon.
     */
    public boolean deleteCocktail(int id) {
        return cocktailDAO.delete(id);
    }

    /**
     * Récupère un cocktail par son identifiant.
     * @param id l'identifiant du cocktail
     * @return le cocktail correspondant, ou null s'il n'existe pas.
     */
    public Cocktail getCocktailById(int id) {
        return cocktailDAO.findById(id);
    }

    /**
     * Récupère la liste de tous les cocktails.
     * @return une liste de cocktails.
     */
    public List<Cocktail> getAllCocktails() {
        return cocktailDAO.findAll();
    }


    /**
     * Sert un cocktail en vérifiant d'abord que le stock est suffisant pour chacune de ses composantes.
     * Si le stock est suffisant, le volume requis est déduit du stock pour chaque boisson et ingrédient.
     *
     * @param cocktail Le cocktail à servir.
     * @return true si le cocktail peut être servi et le stock est mis à jour, false sinon.
     */
    public boolean serveCocktail(Cocktail cocktail) {
        // Vérifier la disponibilité pour chaque boisson du cocktail
        for (Map.Entry<Boisson, Double> entry : cocktail.getBoissonsUtilisees().entrySet()) {
            Boisson boisson = entry.getKey();
            double requiredVolume = entry.getValue();
            if (!stockController.isVolumeAvailable(boisson, requiredVolume)) {
                return false; // Stock insuffisant pour cette boisson
            }
        }
        // Vérifier la disponibilité pour chaque ingrédient du cocktail
        for (Map.Entry<Ingredient, Double> entry : cocktail.getIngredientsUtilises().entrySet()) {
            Ingredient ingredient = entry.getKey();
            double requiredVolume = entry.getValue();
            if (!ingredientStockController.isVolumeAvailable(ingredient, requiredVolume)) {
                return false; // Stock insuffisant pour cet ingrédient
            }
        }
        // Mettre à jour le stock pour chaque boisson
        for (Map.Entry<Boisson, Double> entry : cocktail.getBoissonsUtilisees().entrySet()) {
            Boisson boisson = entry.getKey();
            double requiredVolume = entry.getValue();
            if (!stockController.updateAfterService(boisson, requiredVolume)) {
                return false; // Échec lors de la mise à jour du stock de boisson
            }
        }
        // Mettre à jour le stock pour chaque ingrédient
        for (Map.Entry<Ingredient, Double> entry : cocktail.getIngredientsUtilises().entrySet()) {
            Ingredient ingredient = entry.getKey();
            double requiredVolume = entry.getValue();
            if (!ingredientStockController.updateAfterService(ingredient, requiredVolume)) {
                return false; // Échec lors de la mise à jour du stock d'ingrédient
            }
        }
        return true;
    }

    /**
     * Retourne une map des cocktails disponibles et le nombre maximum de portions pouvant être servies
     * pour chacun, en fonction du stock actuel de boissons et d'ingrédients.
     *
     * @return Une map où la clé est le cocktail et la valeur est le nombre de portions disponibles.
     */
    public Map<Cocktail, Integer> getAvailableCocktails() {
        List<Cocktail> cocktails = cocktailDAO.findAll();
        Map<Cocktail, Integer> availableCocktails = new HashMap<>();
        for (Cocktail cocktail : cocktails) {
            int servings = calculateAvailableServings(cocktail);
            if (servings > 0) {
                availableCocktails.put(cocktail, servings);
            }
        }
        return availableCocktails;
    }

    /**
     * Calcule le nombre maximum de portions d'un cocktail qui peuvent être servies avec le stock actuel.
     * Pour chaque composant du cocktail (boissons et ingrédients), le nombre de portions possibles est
     * calculé en divisant le volume disponible total par le volume requis pour une portion. Le résultat final
     * est le minimum de ces valeurs, car c'est le composant limitant.
     *
     * @param cocktail Le cocktail à évaluer.
     * @return Le nombre maximum de portions pouvant être servies.
     */
    private int calculateAvailableServings(Cocktail cocktail) {
        int servings = Integer.MAX_VALUE;
        // Pour les boissons
        for (Map.Entry<Boisson, Double> entry : cocktail.getBoissonsUtilisees().entrySet()) {
            Boisson boisson = entry.getKey();
            double requiredVolume = entry.getValue();
            double availableVolume = stockController.getTotalAvailableVolume(boisson);
            int possibleServings = (int) (availableVolume / requiredVolume);
            servings = Math.min(servings, possibleServings);
        }
        // Pour les ingrédients
        for (Map.Entry<Ingredient, Double> entry : cocktail.getIngredientsUtilises().entrySet()) {
            Ingredient ingredient = entry.getKey();
            double requiredVolume = entry.getValue();
            double availableVolume = ingredientStockController.getTotalAvailableVolume(ingredient);
            int possibleServings = (int) (availableVolume / requiredVolume);
            servings = Math.min(servings, possibleServings);
        }
        return servings;
    }

}
