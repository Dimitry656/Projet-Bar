import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;
import javaCode.controller.*;
import javaCode.view.*;

public class Main extends Application {

    public static void main(String[] args) {
        // Démarrer JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CocktailController cocktailController = new CocktailController();
        IngredientController ingredientController = new IngredientController();
        IngredientStockController ingredientStockController = new IngredientStockController();
        BoissonController boissonController = new BoissonController();

        InterfaceCommande interfaceCommande = new InterfaceCommande();
        InterfaceCocktail interfaceCoktail = new InterfaceCocktail();

        // Interface JavaFX simple
        Button commanderBtn = new Button("Commander un produit");
        Button creerCocktailBtn = new Button("Créer un cocktail");
        Button quitterBtn = new Button("Quitter");

        commanderBtn.setOnAction(e -> {
            Scanner scanner = new Scanner(System.in);
            interfaceCommande.commandeCocktail(cocktailController, scanner);
        });

        creerCocktailBtn.setOnAction(e -> {
            Scanner scanner = new Scanner(System.in);
            interfaceCoktail.creerCocktail(cocktailController, ingredientController, boissonController, scanner);
        });

        quitterBtn.setOnAction(e -> {
            primaryStage.close();
        });

        VBox root = new VBox(10, commanderBtn, creerCocktailBtn, quitterBtn);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Bar à Cocktails");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}