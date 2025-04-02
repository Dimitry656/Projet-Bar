package java.Entities;

import java.util.Date;

/**
 * La classe IngredientStock représente une entrée de stock pour un ingrédient.
 * Elle gère le pourcentage restant (pourcentageRestant) du volume standard de l'ingrédient
 * ainsi que la date de péremption de cette entrée de stock.
 */
public class IngredientStock {
    private int id;
    private Ingredient ingredient;   // Référence à l'ingrédient concerné
    private Date datePeremption;     // Date de péremption (peut être null si non applicable)
    private double pourcentageRestant; // Pourcentage du volume standard restant (100 = complet)

    // Constructeur par défaut
    public IngredientStock() {
    }

    // Constructeur paramétré
    public IngredientStock(Ingredient ingredient, Date datePeremption, double pourcentageRestant) {
        this.ingredient = ingredient;
        this.datePeremption = datePeremption;
        this.pourcentageRestant = pourcentageRestant;
    }

    // Getters et Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public Date getDatePeremption() {
        return datePeremption;
    }
    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }
    public double getPourcentageRestant() {
        return pourcentageRestant;
    }
    public void setPourcentageRestant(double pourcentageRestant) {
        this.pourcentageRestant = pourcentageRestant;
    }

    @Override
    public String toString() {
        return "IngredientStock [id=" + id + ", ingredient=" + ingredient
                + ", datePeremption=" + datePeremption
                + ", pourcentageRestant=" + pourcentageRestant + "]";
    }
}
