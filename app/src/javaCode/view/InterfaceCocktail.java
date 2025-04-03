package javaCode.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javaCode.Entities.Boisson;
import javaCode.Entities.Cocktail;
import javaCode.Entities.Ingredient;
import javaCode.controller.BoissonController;
import javaCode.controller.CocktailController;
import javaCode.controller.IngredientController;

public class InterfaceCocktail {

    /**
     * Permet de créer un cocktail via une interface console.
     * L'utilisateur sélectionne les boissons et ingrédients à utiliser ainsi que les quantités (en cl).
     * Le cocktail est ensuite créé et, s'il est marqué pour sauvegarde, enregistré en base via le CocktailController.
     *
     * @param cocktailController   Le contrôleur pour gérer les cocktails.
     * @param ingredientController Le contrôleur pour gérer les ingrédients.
     * @param boissonController    Le contrôleur pour gérer les boissons.
     * @param scanner              Le Scanner pour lire les entrées utilisateur.
     */
    public void creerCocktail(CocktailController cocktailController, IngredientController ingredientController, BoissonController boissonController, Scanner scanner) {
        System.out.println("Vous avez choisi de créer un cocktail.");

        // Saisie du nom du cocktail
        System.out.println("Entrez le nom du cocktail :");
        String nomCocktail = scanner.next();

        // Préparation des maps pour stocker les composants du cocktail
        Map<Boisson, Double> boissonsUtilisees = new HashMap<>();
        Map<Ingredient, Double> ingredientsUtilises = new HashMap<>();

        // Affichage des boissons disponibles
        List<Boisson> availableBoissons = boissonController.getAllBoissons();
        System.out.println("\n=== Boissons disponibles ===");
        for (Boisson b : availableBoissons) {
            System.out.println("ID: " + b.getId() + " - " + b.getNom());
        }

        System.out.println("\nEntrez le nombre de boissons utilisées :");
        int nbBoissons = scanner.nextInt();

        for (int i = 0; i < nbBoissons; i++) {
            System.out.print("Entrez l'ID de la boisson " + (i + 1) + " : ");
            int idBoisson = scanner.nextInt();
            System.out.print("Entrez la quantité utilisée (en cl) : ");
            double quantiteBoisson = scanner.nextDouble();
            Boisson boisson = boissonController.getBoissonById(idBoisson);
            if (boisson != null) {
                boissonsUtilisees.put(boisson, quantiteBoisson);
            } else {
                System.out.println("Aucune boisson trouvée avec l'ID " + idBoisson);
            }
        }

        // Affichage des ingrédients disponibles
        List<Ingredient> availableIngredients = ingredientController.getAllIngredients();
        System.out.println("\n=== Ingrédients disponibles ===");
        for (Ingredient ing : availableIngredients) {
            System.out.println("ID: " + ing.getId() + " - " + ing.getNom());
        }

        System.out.println("\nEntrez le nombre d'ingrédients utilisés :");
        int nbIngredients = scanner.nextInt();

        for (int i = 0; i < nbIngredients; i++) {
            System.out.print("Entrez l'ID de l'ingrédient " + (i + 1) + " : ");
            int idIngredient = scanner.nextInt();
            System.out.print("Entrez la quantité utilisée (en cl ou unité standard) : ");
            double quantiteIngredient = scanner.nextDouble();
            Ingredient ingredient = ingredientController.getIngredientById(idIngredient);
            if (ingredient != null) {
                ingredientsUtilises.put(ingredient, quantiteIngredient);
            } else {
                System.out.println("Aucun ingrédient trouvé avec l'ID " + idIngredient);
            }
        }

        // Demande si le cocktail doit être sauvegardé (pour une carte fixe)
        System.out.print("\nSauvegarder ce cocktail en base ? (true/false) : ");
        boolean sauvegarde = scanner.nextBoolean();

        // Création du cocktail
        Cocktail nouveauCocktail = new Cocktail(nomCocktail, boissonsUtilisees, ingredientsUtilises, sauvegarde);

        // Persistance ou non du cocktail
        if (cocktailController.addCocktail(nouveauCocktail)) {
            System.out.println("\nCocktail créé avec succès : " + nouveauCocktail);
        } else {
            System.out.println("\nErreur lors de la création du cocktail.");
        }
    }
}
