package javaCode.view;

import javaCode.Entities.*;
import javaCode.controller.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import javaCode.Main;

import java.util.HashMap;

public class AjouterCocktailScene {
    private VBox layout;

    public AjouterCocktailScene(Main app, HashMap<Boisson, Double> boissonsAjoutees, HashMap<Ingredient, Double> ingredientAjoutes) {
        Label label = new Label("Gérer les cocktails");

        BoissonController boissonController = new BoissonController();
        IngredientController ingredientController = new IngredientController();
        CocktailController cocktailController = new CocktailController();
        StockController stockController = new StockController();
        IngredientStockController ingredientStockController = new IngredientStockController();

        Label label1 = new Label("Gérer les cocktails existants :");

        VBox listeCocktails = new VBox(5);
        for (Cocktail cocktail : cocktailController.getAllCocktails()) {
            Button btn = new Button(cocktail.getNom());
            btn.setOnAction(e -> {
                String[] nom = new String[1];
                HashMap<Boisson, Double> boissons = new HashMap<>();
                HashMap<Ingredient, Double> ingredients = new HashMap<>();

                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix du cocktail " + cocktail.getNom());
                dialog.setHeaderText("Gérer le cocktail " + cocktail.getNom() + " :");
                dialog.setContentText("Nouveau nom (0 pour conserver le nom actuel) :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        if (!input.equalsIgnoreCase("0")) {
                            nom[0] = input;
                            cocktail.setNom(nom[0]);
                            System.out.println("Vous avez changé le nom du cocktail en " + nom[0] + ".");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });

                for (Boisson boisson : cocktail.getBoissonsUtilisees().keySet()) {
                    TextInputDialog dialog2 = new TextInputDialog("1");
                    dialog2.setTitle("Choix du cocktail " + cocktail.getNom());
                    dialog2.setHeaderText("Gérer les boissons du cocktail " + cocktail.getNom() + " :");
                    dialog2.setContentText("Nouvelle quantité (0 pour supprimer la boisson) :");
                    dialog2.showAndWait().ifPresent(input -> {
                        try {
                            double quantite = Double.parseDouble(input);
                            if (quantite == 0) {
                                cocktail.enleverBoisson(boisson);
                                System.out.println("Vous avez supprimé " + boisson.getNom() + " du cocktail.");
                            } else {
                                System.out.println("Vous avez changé la quantité de " + boisson.getNom() + " à " + quantite + " cl.");
                                boissons.put(boisson, quantite);
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("Entrée invalide : " + input);
                        }
                    });
                }

                if (boissons.isEmpty()) {
                    cocktailController.deleteCocktail(cocktail.getId());
                    app.showAjouterCocktailScene(boissonsAjoutees, ingredientAjoutes);
                    return;
                }

                for (Ingredient ingredient : cocktail.getIngredientsUtilises().keySet()) {
                    TextInputDialog dialog2 = new TextInputDialog("1");
                    dialog2.setTitle("Choix du cocktail " + cocktail.getNom());
                    dialog2.setHeaderText("Gérer les ingrédients du cocktail " + cocktail.getNom() + " :");
                    dialog2.setContentText("Nouvelle quantité (0 pour supprimer) :");
                    dialog2.showAndWait().ifPresent(input -> {
                        try {
                            double quantite = Double.parseDouble(input);
                            if (quantite == 0) {
                                cocktail.enleverIngredient(ingredient);
                                System.out.println("Vous avez supprimé " + ingredient.getNom() + " du cocktail.");
                            } else {
                                System.out.println("Vous avez changé la quantité de " + ingredient.getNom() + " à " + quantite + " g.");
                                ingredients.put(ingredient, quantite);
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("Entrée invalide : " + input);
                        }
                    });
                }

                cocktail.setNom(nom[0]);
                cocktail.setBoissonsUtilisees(boissons);
                cocktail.setIngredientsUtilises(ingredients);

                cocktailController.updateCocktail(cocktail);

                app.showAjouterCocktailScene(boissonsAjoutees, ingredientAjoutes);
            });
            listeCocktails.getChildren().add(btn);
        }

        Label label4 = new Label("Boissons disponibles :");

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
                        System.out.println("Vous avez ajouté " + quantite + " cl de " + boisson.getNom() + ".");
                        boissonsAjoutees.put(boisson, quantite);
                        app.showAjouterCocktailScene(boissonsAjoutees, ingredientAjoutes);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            boutonsBoissons.getChildren().add(btn);
        };

        Label label5 = new Label("Ingrédient disponibles :");

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
                        System.out.println("Vous avez ajouté " + quantite + " g de " + ingredient.getNom() + ".");
                        ingredientAjoutes.put(ingredient, quantite);
                        app.showAjouterCocktailScene(boissonsAjoutees, ingredientAjoutes);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            ingredientBoutons.getChildren().add(btn);
        };

        Button retourBtn = new Button("Retour");
        retourBtn.setOnAction(e -> app.showBarmanScene());

        if (!boissonsAjoutees.isEmpty() || !ingredientAjoutes.isEmpty()) {
            VBox elementsCocktail = new VBox(5);
            Label label2 = new Label("Cocktail en cours de création :");
            if (!boissonsAjoutees.isEmpty()) {
                for (Boisson boisson : boissonsAjoutees.keySet()) {
                    Label label3 = new Label(boisson.getNom() + " : " + boissonsAjoutees.get(boisson) + " cl");
                    //label3.setStyle("-fx-alignment: left;");
                    elementsCocktail.getChildren().add(label3);
                }
            }
            if (!ingredientAjoutes.isEmpty()) {
                for (Ingredient ingredient : ingredientAjoutes.keySet()) {
                    Label label3 = new Label(ingredient.getNom() + " : " + ingredientAjoutes.get(ingredient) + " g");
                    //label3.setStyle("-fx-alignment: left;");
                    elementsCocktail.getChildren().add(label3);
                }
            }

            if (!boissonsAjoutees.isEmpty()) {
                Button suivantBtn = new Button("Suivant");
                suivantBtn.setOnAction(e -> {
                    final String[] nom = new String[1];

                    TextInputDialog dialog = new TextInputDialog("Nom du cocktail");
                    dialog.setTitle("Nom du cocktail");
                    dialog.setHeaderText("Entrez le nom du cocktail :");
                    dialog.setContentText("Nom :");
                    dialog.showAndWait().ifPresent(input -> {
                        try {
                            nom[0] = input;
                        } catch (NumberFormatException ex) {
                            System.out.println("Entrée invalide : " + input);
                            return;
                        }
                    });

                    Cocktail cocktail = new Cocktail(nom[0], boissonsAjoutees, ingredientAjoutes, true);
                    cocktailController.addCocktail(cocktail);
                    System.out.println("Cocktail " + nom[0] + " créé avec succès !");
                    app.showAjouterCocktailScene(new HashMap<>(), new HashMap<>());
                });

                layout = new VBox(10, label,
                        label1, listeCocktails,
                        label4, boutonsBoissons,
                        label5, ingredientBoutons,
                        label2, elementsCocktail,
                        suivantBtn,
                        retourBtn);
            } else {
                layout = new VBox(5, label,
                        label1, listeCocktails,
                        label4, boutonsBoissons,
                        label5, ingredientBoutons,
                        label2, elementsCocktail,
                        retourBtn);
            }
        } else {
            layout = new VBox(10, label,
                    label1, listeCocktails,
                    label4, boutonsBoissons,
                    label5, ingredientBoutons,
                    retourBtn);
        }
    }

    public VBox getLayout() {
        return layout;
    }
}
