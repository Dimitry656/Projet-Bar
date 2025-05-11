package javaCode.view;

import javaCode.Entities.Ingredient;
import javaCode.Entities.IngredientStock;
import javaCode.Entities.Stock;
import javaCode.controller.IngredientController;
import javaCode.controller.IngredientStockController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import javaCode.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjouterIngredientScene {
    private VBox layout;

    public AjouterIngredientScene(Main app) {
        Label label = new Label("Gérer les ingrédients");

        IngredientController ingredientController = new IngredientController();
        IngredientStockController ingredientStockController = new IngredientStockController();

        Label label1 = new Label("Gérer les stocks existants : ");

        VBox boutonsIngredients = new VBox(5);
        for (IngredientStock stock : ingredientStockController.getAllIngredientStocks()) {
            Button btn = new Button(stock.getIngredient().getNom() + " : " + stock.getPourcentageRestant());
            btn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix de l'ingrédient " + stock.getIngredient().getNom());
                dialog.setHeaderText("Choisissez le nouveau pourcentage restant de " + stock.getIngredient().getNom());
                dialog.setContentText("Pourcentage (0 pour supprimer) :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        int quantite = Integer.parseInt(input);
                        if (quantite == 0) {
                            ingredientStockController.deleteIngredientStock(stock.getId());
                            System.out.println("Stock de " + stock.getIngredient().getNom() + " supprimé.");
                            app.showAjouterIngredientScene();
                        } else if (quantite < 0 || quantite > 100) {
                            System.out.println("Pourcentage invalide : " + quantite);
                            return;
                        } else {
                            System.out.println("Vous avez " + quantite + "% de " + stock.getIngredient().getNom() + " en stock.");
                            stock.setPourcentageRestant(quantite);
                            ingredientStockController.updateIngredientStock(stock);
                            app.showAjouterIngredientScene();
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            boutonsIngredients.getChildren().add(btn);
        }


        Label label2 = new Label("Ajouter un nouvel ingrédient en stock :");

        Button boutonNouveauIngredient = new Button("Nouvel ingredient");
        boutonNouveauIngredient.setOnAction(e -> {
            final String[] nom = new String[1];
            final Double[] contenance = new Double[1];
            final Double[] prix = new Double[1];
            final Double[] degreSucre = new Double[1];

            TextInputDialog dialog = new TextInputDialog("Nouvelle ingrédient");
            dialog.setTitle("Ajouter un nouvel ingrédient");
            dialog.setHeaderText("Entrez le nom du nouvel ingrédient :");
            dialog.setContentText("Nom :");
            dialog.showAndWait().ifPresent(input -> {
                try {
                    nom[0] = input;
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog2 = new TextInputDialog("Nouvel ingrédient");
            dialog2.setTitle("Ajouter une nouvel ingrédient");
            dialog2.setHeaderText("Entrez la quantité du nouvel ingrédient (en g) :");
            dialog2.setContentText("Contenance :");
            dialog2.showAndWait().ifPresent(input -> {
                try {
                    contenance[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog3 = new TextInputDialog("Nouvel ingrédient");
            dialog3.setTitle("Ajouter une nouvelle boisson");
            dialog3.setHeaderText("Entrez le prix de la nouvel ingrédient :");
            dialog3.setContentText("Prix :");
            dialog3.showAndWait().ifPresent(input -> {
                try {
                    prix[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog5 = new TextInputDialog("Nouvel ingrédient");
            dialog5.setTitle("Ajouter un nouvel ingrédient");
            dialog5.setHeaderText("Entrez le degré de sucre du nouvel ingrédient :");
            dialog5.setContentText("Degré de sucre :");
            dialog5.showAndWait().ifPresent(input -> {
                try {
                    degreSucre[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });
            Ingredient ingredient = new Ingredient(nom[0], contenance[0], prix[0], degreSucre[0]);
            ingredientController.addIngredient(ingredient);
            ingredientStockController.addIngredientStock(new IngredientStock(ingredient, null, 100));
            System.out.println("Nouvelle boisson alcoolisée ajoutée : " + nom[0]);
            app.showAjouterIngredientScene();
        });

        Button retourBtn = new Button("Retour");
        retourBtn.setOnAction(e -> app.showBarmanScene());

        layout = new VBox(15, label,
                label1, boutonsIngredients,
                label2, boutonNouveauIngredient,
                retourBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}
