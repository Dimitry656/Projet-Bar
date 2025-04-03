package javaCode.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javaCode.Entities.Boisson;
import javaCode.Entities.Cocktail;
import javaCode.Entities.Ingredient;
import javaCode.controller.*;

public class InterfaceCoktail {
    public void creerCocktail(CocktailController cocktailController, IngredientController ingredientController, Scanner scanner) {
        System.out.println("Vous avez choisi de créer un cocktail");

        String nomCocktail;
        Map<Boisson, Double> boissonsUtilisees = new HashMap<>();
        Map<Ingredient, Double> ingredientsUtilises = new HashMap<>();
        boolean sauvegarde = true;

        System.out.println("Entrez le nom du cocktail :");
        Scanner scannerNom = new Scanner(System.in);
        nomCocktail = scannerNom.next();

        System.out.println("Boissons disponibles :");
        //MANQUE CONTROLLER BOISSON

        System.out.println("Entrez le nombre de boissons utilisées :");
        int nbBoissons = scanner.nextInt();

        for (int i = 0; i < nbBoissons; i++) {
            System.out.print("Entrez le numéro de la boisson " + (i + 1) + " : ");
            int idBoisson = scanner.nextInt();
            System.out.print("Entrez la quantité utilisée (en cl) : ");
            double quantiteBoisson = scanner.nextDouble();
            // Ajout de la boisson au cocktail
            boissonsUtilisees.put(/*BOISSON CONTROLLER.getBoissonByID(idBoisson) */null , quantiteBoisson);
        }

        System.out.println("Ingredients disponibles :");
        ingredientController.getAllIngredients().toString();

        System.out.println("Entrez le nombre d'ingredients utilisées :");
        int nbingredients = scanner.nextInt();

        for (int i = 0; i < nbingredients; i++) {
            System.out.print("Entrez le numéro de l'ingrédient " + (i + 1) + " : ");
            int idIngredient = scanner.nextInt();
            System.out.print("Entrez la quantité utilisée : ");
            double quantiteIngredient = scanner.nextDouble();
            // Ajout de la Ingredient au cocktail
            ingredientsUtilises.put(ingredientController.getIngredientById(idIngredient), quantiteIngredient);
        }

        // Création du cocktail
        Cocktail nouveauCocktail = new Cocktail(nomCocktail, boissonsUtilisees, ingredientsUtilises, sauvegarde);
        cocktailController.addCocktail(nouveauCocktail);
    }
}