//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javaCode.view.*;

import java.util.Scanner;
import javaCode.controller.*;

public class Main {
    public static void main(String[] args) {
         int choix= 0;

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        CocktailController cocktailController = new CocktailController();
        IngredientController ingredientController = new IngredientController();
        IngredientStockController ingredientStockController = new IngredientStockController();
        BoissonController boissonController = new BoissonController();

        InterfaceCommande interfaceCommande = new InterfaceCommande();
        InterfaceCocktail interfaceCoktail = new InterfaceCocktail();


        System.out.println("Bjr, bienvenue au bar");

        while (true) {
            System.out.println("Que voulez-vous faire ? (rentrer le numéro correspondant)");
            System.out.println("1. Commander un produit");
            System.out.println("2. Créer un cocktail");
            System.out.println("3. Quitter");

            Scanner scanner = new Scanner(System.in);
            try {
                 choix = scanner.nextInt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (choix == 1) {
                interfaceCommande.commandeCocktail(cocktailController, scanner);
            } else if (choix == 2) {
                interfaceCoktail.creerCocktail(cocktailController, ingredientController,boissonController, scanner);
            } else if (choix == 3) {
                System.out.println("Merci d'avoir utilisé notre service. Au revoir !");
                break;
            } else {
                System.out.println("Choix invalide, veuillez réessayer.");
            }
        }

    }
}