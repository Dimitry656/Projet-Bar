package java.controller;


import java.Entities.Stock;
import java.Entities.Boisson;
import java.dao.StockDAO;

/**
 * Le StockController est responsable de la gestion complète du stock :
 * - Charger le stock existant depuis la base de données.
 * - Ajouter ou retirer des quantités pour une boisson donnée,
 *   en mettant à jour à la fois la structure en mémoire et la base de données.
 *
 * Il centralise la logique métier liée au stock, facilitant ainsi la maintenance
 * et l'évolution de la gestion du stock dans l'application.
 */
public class StockController {

    // Instance du DAO pour persister et récupérer les données de stock
    private StockDAO stockDAO;

    // Objet Stock représentant le stock global en mémoire
    private Stock stock;

    /**
     * Constructeur du StockController.
     * Charge le stock initial depuis la base de données lors de l'initialisation.
     */
    public StockController() {
        this.stockDAO = new StockDAO();
        // Charger le stock existant en base dans l'objet stock en mémoire
        this.stock = stockDAO.loadStock();
    }

    /**
     * Ajoute une quantité donnée pour une boisson.
     * Met à jour le stock en mémoire et en base de données.
     *
     * @param b         La boisson à ajouter.
     * @param quantite  La quantité à ajouter.
     * @return true si l'opération réussit, false sinon.
     */
    public boolean ajouterBoisson(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return false;
        }
        // Mise à jour de l'objet en mémoire
        stock.ajouterBoisson(b, quantite);
        // Persistance dans la base de données
        return stockDAO.ajouterBoisson(b, quantite);
    }

    /**
     * Retire une quantité donnée pour une boisson.
     * Met à jour le stock en mémoire et en base de données.
     *
     * @param b         La boisson à retirer.
     * @param quantite  La quantité à retirer.
     * @return true si l'opération réussit, false sinon (par exemple, stock insuffisant).
     */
    public boolean retirerBoisson(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return false;
        }
        // Vérifie d'abord en mémoire si la quantité est disponible
        if (!stock.verifierDisponibilite(b, quantite)) {
            return false;
        }
        // Mise à jour de l'objet en mémoire
        stock.retirerBoisson(b, quantite);
        // Persistance dans la base de données
        return stockDAO.retirerBoisson(b, quantite);
    }

    /**
     * Vérifie la disponibilité d'une boisson pour une quantité donnée.
     * Cette méthode consulte directement l'objet Stock en mémoire.
     *
     * @param b         La boisson à vérifier.
     * @param quantite  La quantité demandée.
     * @return true si la quantité disponible est suffisante, false sinon.
     */
    public boolean verifierDisponibilite(Boisson b, int quantite) {
        return stock.verifierDisponibilite(b, quantite);
    }

    /**
     * Retourne la quantité disponible pour une boisson donnée.
     *
     * @param b La boisson.
     * @return La quantité disponible, ou 0 si la boisson n'est pas présente.
     */
    public int getQuantite(Boisson b) {
        return stock.getQuantite(b);
    }

    /**
     * Recharge le stock depuis la base de données.
     * Utile en cas de mise à jour externe ou de synchronisation.
     */
    public void reloadStock() {
        this.stock = stockDAO.loadStock();
    }

    /**
     * Retourne l'objet Stock en mémoire.
     *
     * @return l'objet Stock contenant la Map des boissons et leurs quantités.
     */
    public Stock getStock() {
        return stock;
    }
}
