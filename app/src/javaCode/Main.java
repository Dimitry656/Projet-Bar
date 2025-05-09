package javaCode;

import javaCode.Entities.Cocktail;
import javaCode.Entities.Boisson;
import javaCode.controller.BoissonController;
import javaCode.controller.CocktailController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javaCode.view.*;

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

    public void showCommandeScene() {
        CommandeScene commandeScene = new CommandeScene(this);
        primaryStage.setScene(new Scene(commandeScene.getLayout(), 500, 400));
    }

    public void showCreerCocktailScene() {
        CreerCocktailScene creerScene = new CreerCocktailScene(this);
        primaryStage.setScene(new Scene(creerScene.getLayout(), 400, 300));
    }

    public static void main(String[] args) {
        launch(args);
    }
}