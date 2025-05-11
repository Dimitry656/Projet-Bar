package javaCode.view;

import javaCode.Entities.BoissonAlcolisee;
import javaCode.Entities.BoissonNonAlcoolisee;
import javaCode.Entities.Boisson;
import javaCode.Entities.Stock;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import javaCode.Main;
import javaCode.controller.BoissonController;
import javaCode.controller.StockController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjouterBoissonScene {
    private VBox layout;

    public AjouterBoissonScene(Main app) {
        Label label = new Label("Gérer les boissons");

        BoissonController boissonController = new BoissonController();
        StockController stockController = new StockController();

        Label label1 = new Label("Gérer les stocks existants : ");

        VBox boutonsBoissons = new VBox(5);
        for (Stock stock : stockController.getAllStocks()) {
            Button btn = new Button(stock.getBoisson().getNom() + " : " + stock.getPourcentageRestant());
            btn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Choix de la boisson " + stock.getBoisson().getNom());
                dialog.setHeaderText("Choisissez le nouveau pourcentage restant de " + stock.getBoisson().getNom() + " (en cl) :");
                dialog.setContentText("Pourcentage (0 pour supprimer) :");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        int quantite = Integer.parseInt(input);
                        if (quantite == 0) {
                            stockController.deleteStock(stock.getId());
                            boissonController.deleteBoisson(stock.getBoisson().getId());
                            System.out.println("Stock de " + stock.getBoisson().getNom() + " supprimé.");
                            app.showAjouterBoissonScene();
                        } else if (quantite < 0 || quantite > 100) {
                            System.out.println("Pourcentage invalide : " + quantite);
                            return;
                        } else {
                            System.out.println("Vous avez " + quantite + "% de " + stock.getBoisson().getNom() + " en stock.");
                            stock.setPourcentageRestant(quantite);
                            stockController.updateStock(stock);
                            app.showAjouterBoissonScene();
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrée invalide : " + input);
                    }
                });
            });
            boutonsBoissons.getChildren().add(btn);
        }

        Label label2 = new Label("Ajouter une nouvelle boisson en stock :");

        VBox boutonNouvelleBoisson = new VBox(5);
        Button btnNouvelleBoissonAlcoolisee = new Button("Boisson alcoolisée");
        btnNouvelleBoissonAlcoolisee.setOnAction(e -> {
            final String[] nom = new String[1];
            final Double[] contenance = new Double[1];
            final Double[] prix = new Double[1];
            final Double[] degreAlcool = new Double[1];
            final Double[] degreSucre = new Double[1];

            TextInputDialog dialog = new TextInputDialog("Nouvelle Boisson");
            dialog.setTitle("Ajouter une nouvelle boisson");
            dialog.setHeaderText("Entrez le nom de la nouvelle boisson :");
            dialog.setContentText("Nom :");
            dialog.showAndWait().ifPresent(input -> {
                try {
                    nom[0] = input;
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog2 = new TextInputDialog("Nouvelle Boisson");
            dialog2.setTitle("Ajouter une nouvelle boisson");
            dialog2.setHeaderText("Entrez la quantité de la nouvelle boisson (en cl) :");
            dialog2.setContentText("Contenance :");
            dialog2.showAndWait().ifPresent(input -> {
                try {
                    contenance[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog3 = new TextInputDialog("Nouvelle Boisson");
            dialog3.setTitle("Ajouter une nouvelle boisson");
            dialog3.setHeaderText("Entrez le prix de la nouvelle boisson :");
            dialog3.setContentText("Prix :");
            dialog3.showAndWait().ifPresent(input -> {
                try {
                    prix[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog4 = new TextInputDialog("Nouvelle Boisson");
            dialog4.setTitle("Ajouter une nouvelle boisson");
            dialog4.setHeaderText("Entrez le degré d'alcool de la nouvelle boisson :");
            dialog4.setContentText("Degré d'alcool :");
            dialog4.showAndWait().ifPresent(input -> {
                try {
                    degreAlcool[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog5 = new TextInputDialog("Nouvelle Boisson");
            dialog5.setTitle("Ajouter une nouvelle boisson");
            dialog5.setHeaderText("Entrez le degré de sucre de la nouvelle boisson :");
            dialog5.setContentText("Degré de sucre :");
            dialog5.showAndWait().ifPresent(input -> {
                try {
                    degreSucre[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });
            BoissonAlcolisee boissonAlcoolisee = new BoissonAlcolisee(nom[0], contenance[0], prix[0], degreAlcool[0], degreSucre[0]);
            boissonController.addBoisson(boissonAlcoolisee);
            stockController.addStock(new Stock(boissonAlcoolisee, null, 100));
            System.out.println("Nouvelle boisson alcoolisée ajoutée : " + nom[0]);
            app.showAjouterBoissonScene();
        });

        Button btnNouvelleBoissonNonAlcoolisee = new Button("Boisson non alcoolisée");
        btnNouvelleBoissonNonAlcoolisee.setOnAction(e -> {
            final String[] nom = new String[1];
            final Double[] contenance = new Double[1];
            final Double[] prix = new Double[1];
            final Double[] degreSucre = new Double[1];

            TextInputDialog dialog = new TextInputDialog("Nouvelle Boisson");
            dialog.setTitle("Ajouter une nouvelle boisson");
            dialog.setHeaderText("Entrez le nom de la nouvelle boisson :");
            dialog.setContentText("Nom :");
            dialog.showAndWait().ifPresent(input -> {
                try {
                    nom[0] = input;
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog2 = new TextInputDialog("Nouvelle Boisson");
            dialog2.setTitle("Ajouter une nouvelle boisson");
            dialog2.setHeaderText("Entrez la quantité de la nouvelle boisson (en cl) :");
            dialog2.setContentText("Contenance :");
            dialog2.showAndWait().ifPresent(input -> {
                try {
                    contenance[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog3 = new TextInputDialog("Nouvelle Boisson");
            dialog3.setTitle("Ajouter une nouvelle boisson");
            dialog3.setHeaderText("Entrez le prix de la nouvelle boisson :");
            dialog3.setContentText("Prix :");
            dialog3.showAndWait().ifPresent(input -> {
                try {
                    prix[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });

            TextInputDialog dialog5 = new TextInputDialog("Nouvelle Boisson");
            dialog5.setTitle("Ajouter une nouvelle boisson");
            dialog5.setHeaderText("Entrez le degré de sucre de la nouvelle boisson :");
            dialog5.setContentText("Degré de sucre :");
            dialog5.showAndWait().ifPresent(input -> {
                try {
                    degreSucre[0] = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Entrée invalide : " + input);
                    return;
                }
            });
            BoissonNonAlcoolisee boissonNonAlcoolisee = new BoissonNonAlcoolisee(nom[0], contenance[0], prix[0], degreSucre[0]);
            boissonController.addBoisson(boissonNonAlcoolisee);
            stockController.addStock(new Stock(boissonNonAlcoolisee, null, 100));
            System.out.println("Nouvelle boisson alcoolisée ajoutée : " + nom[0]);
            app.showAjouterBoissonScene();
        });
        boutonNouvelleBoisson.getChildren().add(btnNouvelleBoissonAlcoolisee);
        boutonNouvelleBoisson.getChildren().add(btnNouvelleBoissonNonAlcoolisee);

        Button retourBtn = new Button("Retour");
        retourBtn.setOnAction(e -> app.showBarmanScene());

        layout = new VBox(15, label,
                label1, boutonsBoissons,
                label2, boutonNouvelleBoisson,
                retourBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}
