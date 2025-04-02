package javaCode.controller;

import javaCode.Entities.Ingredient;
import javaCode.Entities.IngredientStock;
import javaCode.dao.IngredientStockDAO;
import java.util.List;

/**
 * IngredientStockController centralise la logique métier pour la gestion du stock d'ingrédients,
 * en utilisant la même structure que pour le stock des boissons (basée sur pourcentageRestant).
 */
public class IngredientStockController {

    private IngredientStockDAO ingredientStockDAO;

    public IngredientStockController() {
        this.ingredientStockDAO = new IngredientStockDAO();
    }

    // Opérations CRUD

    public boolean addIngredientStock(IngredientStock stock) {
        return ingredientStockDAO.insert(stock);
    }

    public boolean updateIngredientStock(IngredientStock stock) {
        return ingredientStockDAO.update(stock);
    }

    public boolean deleteIngredientStock(int id) {
        return ingredientStockDAO.delete(id);
    }

    public IngredientStock getIngredientStockById(int id) {
        return ingredientStockDAO.findById(id);
    }

    public List<IngredientStock> getAllIngredientStocks() {
        return ingredientStockDAO.findAll();
    }

    public List<IngredientStock> getIngredientStocksByIngredient(Ingredient ingredient) {
        return ingredientStockDAO.findByIngredient(ingredient);
    }

    /**
     * Vérifie si le volume total disponible pour un ingrédient est suffisant pour couvrir le volume demandé.
     * Le volume disponible pour chaque entrée est calculé par : (pourcentageRestant/100) * ingredient.getContenance().
     *
     * @param ingredient  l'ingrédient concerné.
     * @param volumeNeeded le volume requis en cl.
     * @return true si le volume total disponible est supérieur ou égal au volumeNeeded, false sinon.
     */
    public boolean isVolumeAvailable(Ingredient ingredient, double volumeNeeded) {
        double totalAvailable = getTotalAvailableVolume(ingredient);
        return totalAvailable >= volumeNeeded;
    }

    /**
     * Met à jour le stock d'un ingrédient après utilisation.
     * La méthode consomme le volume requis en cl en priorisant les entrées dont la date de péremption est la plus proche.
     *
     * @param ingredient l'ingrédient concerné.
     * @param volumeUsed le volume à consommer en cl.
     * @return true si le stock a pu être mis à jour pour couvrir le volume utilisé, false sinon.
     */
    public boolean updateAfterService(Ingredient ingredient, double volumeUsed) {
        List<IngredientStock> stocks = getIngredientStocksByIngredient(ingredient);
        if (stocks == null || stocks.isEmpty()) {
            return false;
        }
        // Trier par date de péremption croissante (les plus proches en premier, null en dernier)
        stocks.sort((s1, s2) -> {
            if (s1.getDatePeremption() == null && s2.getDatePeremption() == null) return 0;
            if (s1.getDatePeremption() == null) return 1;
            if (s2.getDatePeremption() == null) return -1;
            return s1.getDatePeremption().compareTo(s2.getDatePeremption());
        });

        double remaining = volumeUsed;
        for (IngredientStock stock : stocks) {
            double availableVolume = (stock.getPourcentageRestant() / 100.0) * ingredient.getContenance();
            if (availableVolume <= 0) continue;
            if (availableVolume >= remaining) {
                double percentageConsumed = (remaining / ingredient.getContenance()) * 100;
                double newPercentage = stock.getPourcentageRestant() - percentageConsumed;
                if (newPercentage < 0) {
                    newPercentage = 0;
                }
                stock.setPourcentageRestant(newPercentage);
                if (!updateIngredientStock(stock)) {
                    return false;
                }
                remaining = 0;
                break;
            } else {
                stock.setPourcentageRestant(0);
                if (!updateIngredientStock(stock)) {
                    return false;
                }
                remaining -= availableVolume;
            }
        }
        return remaining == 0;
    }

    /**
     * Calcule le volume total disponible pour un ingrédient en sommant pour chaque entrée :
     * (pourcentageRestant / 100) * ingredient.getContenance().
     *
     * @param ingredient l'ingrédient concerné.
     * @return le volume total disponible en cl.
     */
    public double getTotalAvailableVolume(Ingredient ingredient) {
        List<IngredientStock> stocks = getIngredientStocksByIngredient(ingredient);
        double total = 0;
        if (stocks != null) {
            for (IngredientStock stock : stocks) {
                total += (stock.getPourcentageRestant() / 100.0) * ingredient.getContenance();
            }
        }
        return total;
    }
}
