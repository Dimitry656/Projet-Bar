package javaCode.view;

import javaCode.Entities.*;
import javaCode.controller.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import javaCode.Main;

import java.util.HashMap;

public class CreerCocktailScene {
    private VBox layout;

    public CreerCocktailScene(Main app, HashMap<Boisson, Double> boissonsAjoutees, HashMap<Ingredient, Double> ingredientAjoutes) {
        Label label = new Label("Gérer les cocktails");

        BoissonController boissonController = new BoissonController();
        IngredientController ingredientController = new IngredientController();
        CocktailController cocktailController = new CocktailController();
        StockController stockController = new StockController();
        IngredientStockController ingredientStockController = new IngredientStockController();

        Label label1 = new Label("Boissons disponibles :");

        VBox boutonsBoissons = new VBox(5);
        for (Stock stock : stockController.getAllStocks()) {
            Boisson boisson = stock.getBoisson();
            Button btn = new Button(boisson.getNom());
            btn.setOnAction(e -> {
                if (boissonsAjoutees.containsKey(boisson)) {
                    System.out.println("Vous avez déjà ajouté " + boisson.getNom() + " dans le cocktail.");
                    return;
                }
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix de la boisson " + boisson.getNom());
                dialog.setHeaderText("Choisissez la quantité de " + boisson.getNom() + " dans le cocktail (en cl) :");
                dialog.setContentText("Quantité :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        double quantite = Double.parseDouble(input);
                        if (quantite <= 0) {
                            System.out.println("Quantité invalide : " + quantite);
                            return;
                        } else if (quantite > stockController.getTotalAvailableVolume(boisson)) {
                            System.out.println("Quantité supérieure au stock disponible : " + quantite);
                            return;
                        }
                        System.out.println("Vous avez ajouté " + quantite + " cl de " + boisson.getNom() + ".");
                        boissonsAjoutees.put(boisson, quantite);
                        app.showCreerCocktailScene(boissonsAjoutees, ingredientAjoutes);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            boutonsBoissons.getChildren().add(btn);
        };

        Label label2 = new Label("Ingrédient disponibles :");

        VBox ingredientBoutons = new VBox(5);
        for (IngredientStock stock : ingredientStockController.getAllIngredientStocks()) {
            Ingredient ingredient = stock.getIngredient();
            Button btn = new Button(ingredient.getNom());
            btn.setOnAction(e -> {
                if (ingredientAjoutes.containsKey(ingredient)) {
                    System.out.println("Vous avez déjà ajouté " + ingredient.getNom() + " dans le cocktail.");
                    return;
                }
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix de l'ingrédient " + ingredient.getNom());
                dialog.setHeaderText("Choisissez la quantité de " + ingredient.getNom() + " dans le cocktail (en g) :");
                dialog.setContentText("Quantité :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        double quantite = Double.parseDouble(input);
                        if (quantite <= 0) {
                            System.out.println("Quantité invalide : " + quantite);
                            return;
                        } else if (quantite > ingredientStockController.getTotalAvailableVolume(ingredient)) {
                            System.out.println("Quantité supérieure au stock disponible : " + quantite);
                            return;
                        }
                        System.out.println("Vous avez ajouté " + quantite + " g de " + ingredient.getNom() + ".");
                        ingredientAjoutes.put(ingredient, quantite);
                        app.showCreerCocktailScene(boissonsAjoutees, ingredientAjoutes);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            ingredientBoutons.getChildren().add(btn);
        };

        Button retourBtn = new Button("Retour");
        retourBtn.setOnAction(e -> app.showMainScene());

        if (!boissonsAjoutees.isEmpty() || !ingredientAjoutes.isEmpty()) {
            VBox elementsCocktail = new VBox(5);
            Label label3 = new Label("Cocktail en cours de création :");
            if (!boissonsAjoutees.isEmpty()) {
                for (Boisson boisson : boissonsAjoutees.keySet()) {
                    Label label4 = new Label(boisson.getNom() + " : " + boissonsAjoutees.get(boisson) + " cl");
                    //label3.setStyle("-fx-alignment: left;");
                    elementsCocktail.getChildren().add(label4);
                }
            }
            if (!ingredientAjoutes.isEmpty()) {
                for (Ingredient ingredient : ingredientAjoutes.keySet()) {
                    Label label4 = new Label(ingredient.getNom() + " : " + ingredientAjoutes.get(ingredient) + " g");
                    //label3.setStyle("-fx-alignment: left;");
                    elementsCocktail.getChildren().add(label4);
                }
            }

            if (!boissonsAjoutees.isEmpty()) {
                Button suivantBtn = new Button("Suivant");
                suivantBtn.setOnAction(e -> {
                    Cocktail cocktail = new Cocktail("", boissonsAjoutees, ingredientAjoutes, false);
                    cocktailController.serveCocktail(cocktail);
                    System.out.println("Cocktail sur mesure servis");
                    app.showCreerCocktailScene(new HashMap<>(), new HashMap<>());
                });

                layout = new VBox(10, label,
                        label1, boutonsBoissons,
                        label2, ingredientBoutons,
                        label3, elementsCocktail,
                        suivantBtn,
                        retourBtn);
            } else {
                layout = new VBox(5, label,
                        label1, boutonsBoissons,
                        label2, ingredientBoutons,
                        label3, elementsCocktail,
                        retourBtn);
            }
        } else {
            layout = new VBox(10, label,
                    label1, boutonsBoissons,
                    label2, ingredientBoutons,
                    retourBtn);
        }
    }

    public VBox getLayout() {
        return layout;
    }
}
