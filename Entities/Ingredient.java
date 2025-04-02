package Entities;




/**
 *
 */
public class Ingredient {
    private String nom;
    private double prix;
    private double degreSucre;
    private double contenance;

    /**
     * @param nom
     * @param prix
     * @param degreSucre
     */
    public Ingredient(String nom, double prix, double degreSucre, double contenance) {
        this.nom = nom;
        this.prix = prix;
        this.degreSucre = degreSucre;
        this.contenance = contenance; // en cl
    }

    /**
     * @return nom
     */
    public String getNom() {
       return nom;
    }

    /**
     * @return prix
     */
    public double getPrix() {
       return prix;
    }

    /**
     * @return degreSucre
     */
    public double getDegreSucre() {
       return degreSucre;
    }

    /**
     *
     * @return contenance
     */
    public double getContenance() {
        return contenance;
    }

}
