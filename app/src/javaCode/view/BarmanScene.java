package javaCode.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextInputDialog;

import javaCode.Main;

import java.util.HashMap;

public class BarmanScene {
    private VBox layout;

    public BarmanScene(Main app) {
        Label label = new Label("Choisissez un produit à ajouter :");

        Button btnBoisson = new Button("Gérer les boissons");
        btnBoisson.setOnAction(e -> {
            app.showAjouterBoissonScene();
        });

        Button btnIngredient = new Button("Gérer les ingrédients");
        btnIngredient.setOnAction(e -> {
            app.showAjouterIngredientScene();
        });


        Button btnCocktail = new Button("Gérer les cocktails");
        btnCocktail.setOnAction(e -> {
            app.showAjouterCocktailScene(new HashMap<>(), new HashMap<>());
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> {
            app.showMainScene();
        });

        layout = new VBox(10, label, btnBoisson, btnIngredient, btnCocktail, btnRetour);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}
