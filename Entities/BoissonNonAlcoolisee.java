package Entities;

public class BoissonNonAlcoolisee extends Boisson {


    /**
     * @param  nom
     * @param contenance
     * @param prix
     * @param degreSucre
     */
    public  BoissonNonAlcoolisee(String nom, double contenance, double prix, double degreSucre) {
        super.setNom(nom);
        super.setContenance(contenance);
        super.setPrix(prix);
        super.setDegreSucre(degreSucre);
        super.setDegreAlcool(0.0);

    }


    @Override
    public Double setDegreAlcool(Double degre) {
        return 0.0;
    }

}