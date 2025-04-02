package Entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Cocktail   {
    private String nom;
    private Map<Boisson, Double> boissonsUtilisees; // contenance en cl
    private Map<Ingredient, Double> ingredientsUtilises; // quantités (contenance) en cl
    private boolean sauvegarde;
    private double degreAlcool;
    private double contenance;
    private double degreSucre;

    /**
     * @param nom
     * @param boissonsUtilisees
     * @param ingredientsUtilises
     * @param sauvegarde
     */
    public Cocktail(String nom, Map<Boisson, Double> boissonsUtilisees, Map<Ingredient, Double> ingredientsUtilises, boolean sauvegarde) {
        this.nom = nom;
        this.boissonsUtilisees = boissonsUtilisees;
        this.ingredientsUtilises = ingredientsUtilises;
        this.sauvegarde = sauvegarde;

        this.contenance = calculerContenance();
        this.degreAlcool = calculerDegreAlcool();
        this.degreSucre = calculerDegreSucre();

    }

    /**
     * Calcule le prix du cocktail.
     * Pour les boissons, le prix est proportionnel à la quantité utilisée par rapport à leur contenance totale.
     * Pour les ingrédients, on suppose que le prix est donné pour une unité de mesure.
     * Une majoration de 10% est ajoutée au total.
     *
     * @return Le prix total calculé du cocktail. (de ses ingredients majoré de 10%)
     */
    public double calculerPrix() {
        double prixTotal = 0.0;

        // Calcul pour les boissons
        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            // On suppose que boisson.getPrix() correspond au prix pour sa contenance totale
            // Le coût est proportionnel à la quantité utilisée par rapport à la contenance totale.
            prixTotal += boisson.getPrix() * (quantiteNecessaire / boisson.getContenance());
        }

        // Calcul pour les ingrédients
        for (Map.Entry<Ingredient, Double> entry : ingredientsUtilises.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            // Ici, on suppose que ingredient.getPrix() est le prix par unité.
            prixTotal += ingredient.getPrix() * (quantiteNecessaire/ingredient.getContenance());
        }

        // Majoration de 10% sur le prix total des boissons
        prixTotal += prixTotal * 0.10;

        return prixTotal;
    }

    /**
     * @return degreAlcool
     */
    private double calculerDegreAlcool() {
        double degreAlcool = 0;
        double quantiteAlcool = 0;
        double contenanceTotale = 0;

        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            quantiteAlcool += boisson.getAlcoolQuantity() * (quantiteNecessaire/boisson.getContenance());
            contenanceTotale += quantiteNecessaire;
        }

        for (Map.Entry<Ingredient, Double> entry : ingredientsUtilises.entrySet()) {
            double quantiteNecessaire = entry.getValue();
            contenanceTotale += quantiteNecessaire;
        }
        return quantiteAlcool / contenanceTotale;
    }

    private double calculerContenance() {
        double contenance = 0;
        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            contenance +=  quantiteNecessaire;
        }
        for (Map.Entry<Ingredient, Double> entry : ingredientsUtilises.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            contenance +=  quantiteNecessaire;
        }
        return contenance;
    }



    /**
     * @return degreSucre
     */
    private double calculerDegreSucre() {
        double quantiteSucre = 0;
        double contenance = calculerContenance();
        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            quantiteSucre += boisson.getSucreQuantity() * (quantiteNecessaire/boisson.getContenance());
        }
        for (Map.Entry<Ingredient, Double> entry : ingredientsUtilises.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double quantiteNecessaire = entry.getValue();
            quantiteSucre += ingredient.getDegreSucre() * (quantiteNecessaire/ingredient.getContenance());
        }
        return quantiteSucre / contenance;


    }

    private void updateAttributes() {
        this.contenance = calculerContenance();
        this.degreAlcool = calculerDegreAlcool();
        this.degreSucre = calculerDegreSucre();
    }

    /**
     * @param b (la boisson a rajouter)
     * @return
     */
    public void ajouterBoisson(Boisson b) {
        this.boissonsUtilisees.put(b, b.getContenance());
        updateAttributes();
    }

    /**
     * @param i
     * @return
     */
    public void ajouterIngredient(Ingredient i) {
        this.ingredientsUtilises.put(i, i.getContenance());
        updateAttributes();
    }

    public void enleverBoisson(Boisson b) {
        this.boissonsUtilisees.remove(b);
        updateAttributes();
    }
    public void enleverIngredient(Ingredient i) {
        this.ingredientsUtilises.remove(i);
        updateAttributes();
    }

}