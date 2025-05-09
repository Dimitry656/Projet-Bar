package javaCode.view;

import javaCode.Entities.Cocktail;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javaCode.Main;
import javaCode.Entities.Boisson;
import javaCode.controller.CocktailController;
import javaCode.controller.BoissonController;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CommandeScene {

    private VBox layout;

    public CommandeScene(Main app) {
        Label label = new Label("Choisissez un produit à commander :");

        CocktailController cocktailController = new CocktailController();
        BoissonController boissonController = new BoissonController();


        Map<Cocktail, Integer> mapCocktailsID = cocktailController.getAvailableCocktails();
        Map<String, Integer> mapNomCoktailsID = new HashMap<String, Integer>();

        List<Boisson> listeBoissonsDispo;
        ArrayList<String> nomBoissons = new ArrayList<>();
        // Map<Boisson, Integer> mapBoissons = boissonController.getAvailableBoissons();
        listeBoissonsDispo = boissonController.getAllBoissons();

        for (Cocktail cocktail : mapCocktailsID.keySet()) {
            mapNomCoktailsID.put(cocktail.getNom(), cocktail.getId());
        }

        for (Boisson boisson : listeBoissonsDispo) {
            nomBoissons.add(boisson.getNom());
        }

        VBox boutonsCocktails = new VBox(5);
        for (String nom : mapNomCoktailsID.keySet()) {
            Button btn = new Button(nom);
            btn.setOnAction(e -> {
                cocktailController.serveCocktail(cocktailController.getCocktailById(mapNomCoktailsID.get(nom)));
                System.out.println("Cocktail commandé : " + nom);
            });
            boutonsCocktails.getChildren().add(btn);
        }

        VBox boutonsBoissons = new VBox(5);
        for (String nom : nomBoissons) {
            Button btn = new Button(nom);
            btn.setOnAction(e -> {
                // boissonController.serveBoisson(boissonController.getBoissonByNom(nom));
                System.out.println("Boisson commandée : " + nom);
                });
            boutonsBoissons.getChildren().add(btn);
        }

        Button retourBtn = new Button("Retour");
        retourBtn.setOnAction(e -> app.showMainScene());

        layout = new VBox(15, label,
                new Label("Cocktails :"), boutonsCocktails,
                new Label("Boissons :"), boutonsBoissons,
                retourBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}