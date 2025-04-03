package javaCode.view;

import java.util.Map;
import java.util.Scanner;
import javaCode.Entities.Cocktail;
import javaCode.controller.CocktailController;

public class InterfaceCommande {

    /**
     * Permet à l'utilisateur de commander un cocktail.
     * Cette méthode affiche la liste des cocktails disponibles avec le nombre de portions réalisables,
     * demande à l'utilisateur de sélectionner un cocktail par son ID, puis demande une confirmation.
     * Si l'utilisateur confirme, le cocktail est servi (ce qui met à jour le stock via le CocktailController).
     *
     * @param cocktailController Le contrôleur pour les cocktails.
     * @param scanner            Le Scanner pour lire les entrées utilisateur.
     */
    public void commandeCocktail(CocktailController cocktailController, Scanner scanner) {
        System.out.println("Vous avez choisi de commander un produit.");

        // Récupère la map des cocktails disponibles : clé = Cocktail, valeur = nombre de portions réalisables
        Map<Cocktail, Integer> availableCocktails = cocktailController.getAvailableCocktails();

        if (availableCocktails.isEmpty()) {
            System.out.println("Aucun cocktail disponible pour le moment.");
            return;
        }

        System.out.println("=== Cocktails Disponibles ===");
        // Affiche chaque cocktail avec son ID, nom et nombre de portions disponibles
        for (Map.Entry<Cocktail, Integer> entry : availableCocktails.entrySet()) {
            Cocktail c = entry.getKey();
            int servings = entry.getValue();
            System.out.println("ID: " + c.getId() + " - " + c.getNom() + " | Portions disponibles: " + servings);
        }

        System.out.println("Entrez l'ID du cocktail que vous souhaitez commander :");
        int idCocktail = scanner.nextInt();

        // Recherche du cocktail sélectionné dans la map en se basant sur l'ID
        Cocktail selectedCocktail = null;
        for (Cocktail c : availableCocktails.keySet()) {
            if (c.getId() == idCocktail) {
                selectedCocktail = c;
                break;
            }
        }

        if (selectedCocktail == null) {
            System.out.println("ID invalide ou cocktail non disponible.");
            return;
        }

        System.out.println("Vous avez choisi le cocktail : " + selectedCocktail.getNom());
        System.out.println("Voulez-vous le commander ? (true/false)");
        boolean confirmation = scanner.nextBoolean();

        if (confirmation) {
            boolean served = cocktailController.serveCocktail(selectedCocktail);
            if (served) {
                System.out.println("Commande validée pour le cocktail : " + selectedCocktail.getNom());
            } else {
                System.out.println("Échec lors du service du cocktail (stock insuffisant ou problème technique).");
            }
        } else {
            System.out.println("Commande annulée.");
        }
    }
}
