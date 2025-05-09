package javaCode.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javaCode.Main;

public class MainScene {

    private VBox layout;

    public MainScene(Main app) {
        Button commanderBtn = new Button("Commander un produit");
        Button creerBtn = new Button("Créer un cocktail");
        Button quitterBtn = new Button("Quitter");

        commanderBtn.setOnAction(e -> app.showCommandeScene());
        creerBtn.setOnAction(e -> app.showCreerCocktailScene());
        quitterBtn.setOnAction(e -> System.exit(0));

        layout = new VBox(10, commanderBtn, creerBtn, quitterBtn);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");
    }

    public VBox getLayout() {
        return layout;
    }
}