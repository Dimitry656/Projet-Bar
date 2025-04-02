package java.Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Représente une instance de stock pour une boisson.
 * Chaque entrée correspond à un contenant physique (par exemple, une bouteille)
 * et contient des informations spécifiques telles que la date de péremption et
 * le pourcentage restant (indiquant le volume non utilisé par rapport à la contenance totale).
 */
public class Stock {
    private int id;               // Identifiant unique de l'entrée de stock
    private Boisson boisson;      // La boisson associée (référence vers l'entité Boisson)
    private Date datePeremption;  // Date de péremption (NULL si jamais périmée)
    private double pourcentageRestant; // Pourcentage restant dans le contenant (100 = complet)

    // Constructeur par défaut
    public Stock() {
    }

    // Constructeur paramétré
    public Stock(Boisson boisson, Date datePeremption, double pourcentageRestant) {
        this.boisson = boisson;
        this.datePeremption = datePeremption;
        this.pourcentageRestant = pourcentageRestant;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boisson getBoisson() {
        return boisson;
    }

    public void setBoisson(Boisson boisson) {
        this.boisson = boisson;
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
        return "Stock [id=" + id + ", boisson=" + boisson + ", datePeremption=" + datePeremption
                + ", pourcentageRestant=" + pourcentageRestant + "]";
    }
}