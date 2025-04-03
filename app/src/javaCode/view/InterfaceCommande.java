package javaCode.view;

import java.util.List;
import java.util.Scanner;
import javaCode.controller.CocktailController;

import javaCode.Entities.Cocktail;

public class InterfaceCommande {
    public void commandeCocktail(CocktailController cocktailController, Scanner scanner) {
        System.out.println("Vous avez choisi de commander un produit");
        System.out.println("Voici la liste des produits disponibles :");
        cocktailController.getAllCocktails().toString();
        
        List<Cocktail> cocktailsDispo = cocktailController.getAllCocktails();
        int lastID = cocktailsDispo.size() - 1;
        
        System.out.println("Choissisez le cocktail que vous voulez commander :");
        int idCocktail = scanner.nextInt();
    
        if (idCocktail < 0 || idCocktail > lastID) {
            System.out.println("ID invalide");
            return;
        }
    
        Cocktail cocktail = cocktailController.getCocktailById(idCocktail);
        
        System.out.println("Vous avez choisi le cocktail : " + cocktail.getNom());
        System.out.println("Voulez-vous le commander ? (true/false)");
        boolean commande = scanner.nextBoolean();
        if (commande) {
            System.out.println("Commande validée pour le cocktail : " + cocktail.getNom());
            cocktailController.serveCocktail(cocktail); // Appel de la méthode pour servir le cocktail
        } else {
            System.out.println("Commande annulée.");
        }
    }
}
