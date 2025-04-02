
package javaCode;
import javaCode.Entities.Boisson;
import javaCode.Entities.Ingredient;
import javaCode.Entities.Stock;
import javaCode.Entities.IngredientStock;
import javaCode.Entities.Cocktail;

import javaCode.dao.BoissonDAO;
import javaCode.dao.IngredientDAO;


import javaCode.controller.IngredientStockController;
import javaCode.controller.StockController;
import javaCode.controller.CocktailController;



import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        System.out.println("=== Test Insertion et Récupération de Boissons ===");
        // Création de boissons (ici, on utilise une implémentation anonyme de la classe abstraite Boisson)
        Boisson whisky = new Boisson("Whisky", 1000.0, 50.0, 40.0, 5.0) {};
        Boisson vodka = new Boisson("Vodka", 1000.0, 45.0, 38.0, 2.0) {};

        // Insertion dans la base via BoissonDAO
        BoissonDAO boissonDAO = new BoissonDAO();
        boissonDAO.insert(whisky);
        boissonDAO.insert(vodka);

        // Récupération et affichage
        Boisson retrievedWhisky = boissonDAO.findById(whisky.getId());
        Boisson retrievedVodka = boissonDAO.findById(vodka.getId());
        System.out.println("Boisson insérée 1 : " + retrievedWhisky);
        System.out.println("Boisson insérée 2 : " + retrievedVodka);

        System.out.println("\n=== Test Insertion et Récupération d'Ingrédients ===");
        // Création d'ingrédients
        Ingredient citron = new Ingredient("Citron", 100.0, 0.50, 2.0, 100.0);
        Ingredient menthe = new Ingredient("Menthe", 50.0, 0.30, 1.0, 200.0);

        // Insertion via IngredientDAO
        IngredientDAO ingredientDAO = new IngredientDAO();
        ingredientDAO.insert(citron);
        ingredientDAO.insert(menthe);

        // Récupération et affichage
        Ingredient retrievedCitron = ingredientDAO.findById(citron.getId());
        Ingredient retrievedMenthe = ingredientDAO.findById(menthe.getId());
        System.out.println("Ingrédient inséré 1 : " + retrievedCitron);
        System.out.println("Ingrédient inséré 2 : " + retrievedMenthe);

        System.out.println("\n=== Test Insertion de Stock pour Boissons ===");
        // Création d'entrées de stock pour les boissons
        StockController stockController = new StockController();
        // Pour le whisky, deux bouteilles : une avec 80% restant et une avec 100% restant
        Stock stock1 = new Stock(whisky, null, 80.0);
        Stock stock2 = new Stock(whisky, null, 100.0);
        stockController.addStock(stock1);
        stockController.addStock(stock2);
        // Pour la vodka, une bouteille avec 50% restant
        Stock stock3 = new Stock(vodka, null, 50.0);
        stockController.addStock(stock3);

        // Affichage du stock de boissons
        List<Stock> allStocks = stockController.getAllStocks();
        System.out.println("Stock de boissons :");
        for (Stock s : allStocks) {
            System.out.println(s);
        }

        System.out.println("\n=== Test Insertion de Stock pour Ingrédients ===");
        // Création d'entrées de stock pour les ingrédients via IngredientStockController
        IngredientStockController ingredientStockController = new IngredientStockController();
        // Pour le citron, deux entrées : 90% et 100%
        IngredientStock ingStock1 = new IngredientStock(citron, null, 90.0);
        IngredientStock ingStock2 = new IngredientStock(citron, null, 100.0);
        ingredientStockController.addIngredientStock(ingStock1);
        ingredientStockController.addIngredientStock(ingStock2);
        // Pour la menthe, une entrée avec 80% restant
        IngredientStock ingStock3 = new IngredientStock(menthe, null, 80.0);
        ingredientStockController.addIngredientStock(ingStock3);

        // Affichage du stock d'ingrédients
        List<IngredientStock> allIngredientStocks = ingredientStockController.getAllIngredientStocks();
        System.out.println("Stock d'ingrédients :");
        for (IngredientStock is : allIngredientStocks) {
            System.out.println(is);
        }

        System.out.println("\n=== Test Insertion et Récupération de Cocktails ===");
        // Création d'un cocktail : par exemple, un Whisky Sour
        Map<Boisson, Double> boissonsUtilisees = new HashMap<>();
        // On utilise 50 cl de whisky
        boissonsUtilisees.put(whisky, 50.0);
        Map<Ingredient, Double> ingredientsUtilises = new HashMap<>();
        // On utilise 10 cl de citron et 5 cl de menthe
        ingredientsUtilises.put(citron, 10.0);
        ingredientsUtilises.put(menthe, 5.0);

        Cocktail cocktail1 = new Cocktail("Whisky Sour", boissonsUtilisees, ingredientsUtilises, true);
        CocktailController cocktailController = new CocktailController();
        cocktailController.addCocktail(cocktail1);

        // Récupération et affichage du cocktail
        Cocktail retrievedCocktail = cocktailController.getCocktailById(cocktail1.getId());
        System.out.println("Cocktail inséré : " + retrievedCocktail);

        System.out.println("\n=== Test Disponibilité et Service de Cocktail ===");
        // Afficher les cocktails disponibles et le nombre de portions possibles
        System.out.println("Cocktails disponibles et portions réalisables :");
        System.out.println(cocktailController.getAvailableCocktails());

        // Tenter de servir le cocktail
        boolean served = cocktailController.serveCocktail(cocktail1);
        System.out.println("Tentative de service du cocktail '" + cocktail1.getNom() + "' : " + (served ? "Réussi" : "Échoué"));

        System.out.println("\n=== Stock mis à jour après service (Boissons) ===");
        for (Stock s : stockController.getAllStocks()) {
            System.out.println(s);
        }

        System.out.println("\n=== Stock mis à jour après service (Ingrédients) ===");
        for (IngredientStock is : ingredientStockController.getAllIngredientStocks()) {
            System.out.println(is);
        }
    }
}
