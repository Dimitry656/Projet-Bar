package javaCode.view;

import javaCode.Entities.Boisson;
import javaCode.Entities.Ingredient;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javaCode.Main;

import javaCode.controller.CocktailController;
import javaCode.controller.BoissonController;
import javaCode.controller.IngredientController;
import javaCode.Entities.Cocktail;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class CreerCocktailScene {

    private VBox layout;

    public CreerCocktailScene(Main app) {
        Label label = new Label("Créer un cocktail :");

        CocktailController cocktailController = new CocktailController();
        BoissonController boissonController = new BoissonController();
        IngredientController ingredientController = new IngredientController();

        List<Boisson> listeBoissonsDispo = boissonController.getAllBoissons();
        ArrayList<String> nomBoissons = new ArrayList<>();

        for (Boisson boisson : listeBoissonsDispo) {
            nomBoissons.add(boisson.getNom());
        }

        Map<Boisson, Double> boissonNouveauCocktail = new HashMap<>();
        Map<Ingredient, Double> ingredientNouveauCocktail = new HashMap<>();

        VBox boutonsBoissons = new VBox(5);
        for (String nom : nomBoissons) {
            Button btn = new Button(nom);
            btn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix de la boisson " + nom);
                dialog.setHeaderText("Choisissez la quantité de " + nom + " (en cl) :");
                dialog.setContentText("Quantité :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        int quantite = Integer.parseInt(input);
                        System.out.println("Vous avez ajouté " + quantite + " de " + nom + "cl.");
                        boissonNouveauCocktail.put(boissonController.getBoissonByNom(nom), (double) quantite);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            boutonsBoissons.getChildren().add(btn);
        }

        VBox boutonsIngredients = new VBox(5);
        for (String nom : nomBoissons) {
            Button btn = new Button(nom);
            btn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix de l'ingrédient " + nom);
                dialog.setHeaderText("Choisissez la quantité de " + nom + " (en g) :");
                dialog.setContentText("Quantité :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        int quantite = Integer.parseInt(input);
                        System.out.println("Ingrédient ajouté : " + nom + " - Quantité : " + quantite + " g.");
                        ingredientNouveauCocktail.put(ingredientController.getIngredientByNom(nom), (double) quantite);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            boutonsBoissons.getChildren().add(btn);
        }

        Button validerBtn = new Button("Valider et commander le cocktail");
        validerBtn.setOnAction(e -> {
            Cocktail nouveauCocktail = new Cocktail("", boissonNouveauCocktail, ingredientNouveauCocktail, false);
            cocktailController.serveCocktail(nouveauCocktail);
            System.out.println("Cocktail personnalisé servi.");
            app.showMainScene();
        });

        Button retourBtn = new Button("Retour");
        retourBtn.setOnAction(e -> app.showMainScene());

        layout = new VBox(15, label,
                new Label("Boissons :"), boutonsBoissons,
                new Label("Ingrédients :"), boutonsIngredients,
                validerBtn,
                retourBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}