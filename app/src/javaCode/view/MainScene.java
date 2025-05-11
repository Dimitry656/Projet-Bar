package javaCode.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javaCode.Main;

import java.util.HashMap;

public class MainScene {

    private VBox layout;

    public MainScene(Main app) {
        Button barmanBtn = new Button("Barman mode");
        Button commanderBtn = new Button("Commander un produit");
        Button creerBtn = new Button("Créer un cocktail");
        Button quitterBtn = new Button("Quitter");

        barmanBtn.setOnAction(e -> app.showBarmanScene());
        commanderBtn.setOnAction(e -> app.showCommandeScene());
        creerBtn.setOnAction(e -> app.showCreerCocktailScene(new HashMap<>(), new HashMap<>()));
        quitterBtn.setOnAction(e -> System.exit(0));

        layout = new VBox(10, barmanBtn, commanderBtn, creerBtn, quitterBtn);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}