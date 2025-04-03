//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javaCode.view.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import javaCode.controller.*;
import javaCode.Entities.*;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        CocktailController cocktailController = new CocktailController();
        IngredientController ingredientController = new IngredientController();
        IngredientStockController ingredientStockController = new IngredientStockController();

        InterfaceCommande interfaceCommande = new InterfaceCommande();
        InterfaceCoktail interfaceCoktail = new InterfaceCoktail();


        System.out.println("Bjr, bienvenue au bar");

        while (true) {
            System.out.println("Que voulez-vous faire ? (rentrer le numéro correspondant)");
            System.out.println("1. Commander un produit");
            System.out.println("2. Créer un cocktail");
            System.out.println("3. Quitter");

            Scanner scanner = new Scanner(System.in);
            int choix = scanner.nextInt();
            if (choix == 1) {
                interfaceCommande.commandeCocktail(cocktailController, scanner);
            } else if (choix == 2) {
                interfaceCoktail.creerCocktail(cocktailController, ingredientController, scanner);
            } else if (choix == 3) {
                System.out.println("Merci d'avoir utilisé notre service. Au revoir !");
                break;
            } else {
                System.out.println("Choix invalide, veuillez réessayer.");
            }
        }

    }
}