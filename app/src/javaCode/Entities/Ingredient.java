package javaCode.Entities;

/**
 * La classe Ingredient représente un ingrédient additionnel utilisé dans la préparation de cocktails.
 * Elle contient les informations de base telles que le nom, la contenance standard (exprimée en cl ou g),
 * le prix pour cette contenance, le degré de sucre et la quantité disponible en stock.
 */
public class Ingredient {
    private int id;
    private String nom;
    private double contenance;           // Quantité standard (ex. 50 cl ou 100 g)
    private double prix;                 // Prix pour la contenance complète
    private double degreSucre;           // Impact sur le taux de sucre (en % ou une autre unité)
    private double quantiteDisponible;   // Quantité disponible en stock (exprimée dans la même unité que la contenance)

    /**
     * Constructeur par défaut.
     */
    public Ingredient() {
    }

    /**
     * Constructeur paramétré.
     *
     * @param nom                le nom de l'ingrédient
     * @param contenance         la contenance standard
     * @param prix               le prix pour la contenance complète
     * @param degreSucre         le taux de sucre ou impact sur le sucre
     * @param quantiteDisponible la quantité disponible en stock
     */
    public Ingredient(String nom, double contenance, double prix, double degreSucre, double quantiteDisponible) {
        this.nom = nom;
        this.contenance = contenance;
        this.prix = prix;
        this.degreSucre = degreSucre;
        this.quantiteDisponible = quantiteDisponible;
    }

    // Getters et Setters

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
    public double getContenance() {
        return contenance;
    }
    public void setContenance(double contenance) {
        this.contenance = contenance;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public double getDegreSucre() {
        return degreSucre;
    }
    public void setDegreSucre(double degreSucre) {
        this.degreSucre = degreSucre;
    }
    public double getQuantiteDisponible() {
        return quantiteDisponible;
    }
    public void setQuantiteDisponible(double quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    @Override
    public String toString() {
        return "Ingredient [id=" + id + ", nom=" + nom + ", contenance=" + contenance +
                ", prix=" + prix + ", degreSucre=" + degreSucre + "]";
    }
}
