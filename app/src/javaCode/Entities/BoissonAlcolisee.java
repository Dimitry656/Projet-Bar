package javaCode.Entities;



/**
 *
 */
public class BoissonAlcolisee extends Boisson {

    /**
     * Default constructor
     */
    public BoissonAlcolisee() {
    }

    /**
     * @param nom
     * @param contenance
     * @param prix
     * @param degreAlcool
     * @param degreSucre
     */
    public BoissonAlcolisee(String nom, double contenance, double prix, double degreAlcool, double degreSucre) {
        super.setNom(nom);
        super.setContenance(contenance);
        super.setPrix(prix);
        super.setDegreAlcool(degreAlcool);
        super.setDegreSucre(degreSucre);

    }

}