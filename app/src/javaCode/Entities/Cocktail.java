package javaCode.Entities;

import java.util.Map;
import java.util.HashMap;

/**
 * La classe Cocktail représente une recette de cocktail composée de boissons et d'ingrédients.
 * Elle intègre des attributs calculés tels que la contenance totale, le degré d'alcool moyen
 * et le degré de sucre moyen. Le cocktail peut être préenregistré (sauvegardé) ou créé à la demande.
 */
public class Cocktail {
    private int id;
    private String nom;
    // Maps associant les composants à la quantité utilisée en cl
    private Map<Boisson, Double> boissonsUtilisees;
    private Map<Ingredient, Double> ingredientsUtilises;
    private boolean sauvegarde;

    // Attributs calculés
    private double degreAlcool;
    private double contenance; // en cl, somme des volumes utilisés
    private double degreSucre;

    /**
     * Constructeur par défaut.
     */
    public Cocktail() {
        this.boissonsUtilisees = new HashMap<>();
        this.ingredientsUtilises = new HashMap<>();
    }

    /**
     * Constructeur paramétré.
     *
     * @param nom le nom du cocktail
     * @param boissonsUtilisees Map associant chaque boisson à la quantité utilisée (en cl)
     * @param ingredientsUtilises Map associant chaque ingrédient à la quantité utilisée (en cl)
     * @param sauvegarde indique si le cocktail est préenregistré
     */
    public Cocktail(String nom, Map<Boisson, Double> boissonsUtilisees, Map<Ingredient, Double> ingredientsUtilises, boolean sauvegarde) {
        this.nom = nom;
        this.boissonsUtilisees = boissonsUtilisees;
        this.ingredientsUtilises = ingredientsUtilises;
        this.sauvegarde = sauvegarde;
        updateAttributes();
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Map<Boisson, Double> getBoissonsUtilisees() {
        return boissonsUtilisees;
    }
    public void setBoissonsUtilisees(Map<Boisson, Double> boissonsUtilisees) {
        this.boissonsUtilisees = boissonsUtilisees;
        updateAttributes();
    }
    public Map<Ingredient, Double> getIngredientsUtilises() {
        return ingredientsUtilises;
    }
    public void setIngredientsUtilises(Map<Ingredient, Double> ingredientsUtilises) {
        this.ingredientsUtilises = ingredientsUtilises;
        updateAttributes();
    }
    public boolean isSauvegarde() {
        return sauvegarde;
    }
    public void setSauvegarde(boolean sauvegarde) {
        this.sauvegarde = sauvegarde;
    }
    public double getDegreAlcool() {
        return degreAlcool;
    }
    public double getContenance() {
        return contenance;
    }
    public double getDegreSucre() {
        return degreSucre;
    }

    /**
     * Calcule le prix du cocktail.
     * Pour chaque boisson, le coût est proportionnel à la quantité utilisée par rapport à sa contenance.
     * Pour chaque ingrédient, le coût est calculé de façon similaire.
     * Une majoration de 10% est ajoutée au total.
     *
     * @return le prix total calculé du cocktail.
     */
    public double calculerPrix() {
        double prixTotal = 0.0;
        // Coût des boissons
        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double quantiteUtilisee = entry.getValue();
            prixTotal += boisson.getPrix() * (quantiteUtilisee / boisson.getContenance());
        }
        // Coût des ingrédients
        for (Map.Entry<Ingredient, Double> entry : ingredientsUtilises.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double quantiteUtilisee = entry.getValue();
            prixTotal += ingredient.getPrix() * (quantiteUtilisee / ingredient.getContenance());
        }
        return prixTotal * 1.10;
    }

    /**
     * Calcule la contenance totale du cocktail (la somme des volumes utilisés en cl).
     * @return la contenance totale en cl.
     */
    private double calculerContenance() {
        double total = 0;
        for (double volume : boissonsUtilisees.values()) {
            total += volume;
        }
        for (double volume : ingredientsUtilises.values()) {
            total += volume;
        }
        return total;
    }

    /**
     * Calcule le degré d'alcool moyen du cocktail en pondérant par le volume utilisé.
     * @return le degré d'alcool moyen.
     */
    private double calculerDegreAlcool() {
        double totalAlcool = 0;
        double totalVolume = calculerContenance();
        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double volume = entry.getValue();
            totalAlcool += boisson.getDegreAlcool() * (volume / boisson.getContenance());
        }
        return (totalVolume == 0) ? 0 : totalAlcool;
    }

    /**
     * Calcule le degré de sucre moyen du cocktail.
     * @return le degré de sucre moyen.
     */
    private double calculerDegreSucre() {
        double totalSucre = 0;
        double totalVolume = calculerContenance();
        for (Map.Entry<Boisson, Double> entry : boissonsUtilisees.entrySet()) {
            Boisson boisson = entry.getKey();
            double volume = entry.getValue();
            totalSucre += boisson.getDegreSucre() * (volume / boisson.getContenance());
        }
        for (Map.Entry<Ingredient, Double> entry : ingredientsUtilises.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double volume = entry.getValue();
            totalSucre += ingredient.getDegreSucre() * (volume / ingredient.getContenance());
        }
        return (totalVolume == 0) ? 0 : totalSucre;
    }

    /**
     * Met à jour les attributs calculés (contenance, degré d'alcool et de sucre).
     */
    private void updateAttributes() {
        this.contenance = calculerContenance();
        this.degreAlcool = calculerDegreAlcool();
        this.degreSucre = calculerDegreSucre();
    }

    /**
     * Ajoute une boisson à la recette avec la quantité utilisée égale par défaut à sa contenance.
     * @param b la boisson à ajouter.
     */
    public void ajouterBoisson(Boisson b) {
        this.boissonsUtilisees.put(b, b.getContenance());
        updateAttributes();
    }

    /**
     * Ajoute un ingrédient à la recette avec la quantité utilisée égale par défaut à sa contenance.
     * @param i l'ingrédient à ajouter.
     */
    public void ajouterIngredient(Ingredient i) {
        this.ingredientsUtilises.put(i, i.getContenance());
        updateAttributes();
    }

    /**
     * Supprime une boisson de la recette.
     * @param b la boisson à enlever.
     */
    public void enleverBoisson(Boisson b) {
        this.boissonsUtilisees.remove(b);
        updateAttributes();
    }

    /**
     * Supprime un ingrédient de la recette.
     * @param i l'ingrédient à enlever.
     */
    public void enleverIngredient(Ingredient i) {
        this.ingredientsUtilises.remove(i);
        updateAttributes();
    }

    @Override
    public String toString() {
        return "Cocktail [id=" + id + ", nom=" + nom + ", contenance=" + contenance
                + " cl, degreAlcool=" + degreAlcool + ", degreSucre=" + degreSucre
                + ", prix=" + calculerPrix() + "]";
    }
}
