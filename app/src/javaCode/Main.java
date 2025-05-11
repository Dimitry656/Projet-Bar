package javaCode;

import javaCode.Entities.Cocktail;
import javaCode.Entities.Boisson;
import javaCode.Entities.Ingredient;
import javaCode.controller.BoissonController;
import javaCode.controller.CocktailController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javaCode.view.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showMainScene();
    }

    public void showMainScene() {
        MainScene mainScene = new MainScene(this);
        primaryStage.setScene(new Scene(mainScene.getLayout(), 400, 250));
        primaryStage.setTitle("Bar à Cocktails");
        primaryStage.show();
    }

    public void showBarmanScene() {
        BarmanScene barmanScene = new BarmanScene(this);
        primaryStage.setScene(new Scene(barmanScene.getLayout(), 400, 300));
    }

    public void showAjouterBoissonScene() {
        AjouterBoissonScene ajouterBoissonScene = new AjouterBoissonScene(this);
        primaryStage.setScene(new Scene(ajouterBoissonScene.getLayout(), 400, 300));
    }

    public void showAjouterIngredientScene() {
        AjouterIngredientScene ajouterIngredientScene = new AjouterIngredientScene(this);
        primaryStage.setScene(new Scene(ajouterIngredientScene.getLayout(), 400, 300));
    }

    public void showAjouterCocktailScene(HashMap<Boisson, Double> boissonNouveau, HashMap<Ingredient, Double> ingredientNouveau) {
        AjouterCocktailScene ajouterCocktailScene = new AjouterCocktailScene(this, boissonNouveau, ingredientNouveau);
        primaryStage.setScene(new Scene(ajouterCocktailScene.getLayout(), 400, 300));
    }

    public void showCommandeScene() {
        CommandeScene commandeScene = new CommandeScene(this);
        primaryStage.setScene(new Scene(commandeScene.getLayout(), 500, 400));
    }

    public void showCreerCocktailScene(HashMap<Boisson, Double> boissonsAjoutees, HashMap<Ingredient, Double> ingredientAjoutes) {
        CreerCocktailScene creerScene = new CreerCocktailScene(this, boissonsAjoutees, ingredientAjoutes);
        primaryStage.setScene(new Scene(creerScene.getLayout(), 400, 300));
    }

    public static void main(String[] args) {
        launch(args);
    }
}