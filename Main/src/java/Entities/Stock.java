package java.Entities;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe Stock gère l'ensemble des stocks de boissons.
 * Elle utilise une Map pour associer chaque Boisson à sa quantité disponible.
 */
public class Stock {

    // Map associant chaque Boisson à la quantité disponible
    private Map<Boisson, Integer> stockMap;

    /**
     * Constructeur par défaut.
     */
    public Stock() {
        this.stockMap = new HashMap<>();
    }

    /**
     * Ajoute une quantité pour une boisson donnée.
     * Si la boisson est déjà présente, sa quantité est incrémentée.
     *
     * @param b         La boisson à ajouter.
     * @param quantite  La quantité à ajouter.
     */
    public void ajouterBoisson(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return;
        }
        int quantiteExistante = stockMap.getOrDefault(b, 0);
        stockMap.put(b, quantiteExistante + quantite);
    }

    /**
     * Retire une quantité pour une boisson donnée.
     * Si la quantité à retirer est égale ou supérieure à la quantité existante,
     * la boisson est retirée du stock.
     *
     * @param b         La boisson à retirer.
     * @param quantite  La quantité à retirer.
     */
    public void retirerBoisson(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return;
        }
        int quantiteExistante = stockMap.getOrDefault(b, 0);
        if (quantiteExistante <= quantite) {
            stockMap.remove(b);
        } else {
            stockMap.put(b, quantiteExistante - quantite);
        }
    }

    /**
     * Vérifie si la quantité demandée pour une boisson est disponible.
     *
     * @param b         La boisson à vérifier.
     * @param quantite  La quantité demandée.
     * @return true si la quantité disponible est suffisaSnte, false sinon.
     */
    public boolean verifierDisponibilite(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return false;
        }
        int quantiteExistante = stockMap.getOrDefault(b, 0);
        return quantiteExistante >= quantite;
    }

    /**
     * Retourne la quantité disponible pour une boisson donnée.
     *
     * @param b La boisson à vérifier.
     * @return La quantité disponible, ou 0 si la boisson n'est pas présente dans le stock.
     */
    public int getQuantite(Boisson b) {
        if (b == null) {
            return 0;
        }
        return stockMap.getOrDefault(b, 0);
    }

    /**
     * Retourne l'ensemble de la Map de stock.
     *
     * @return La Map associant les boissons à leur quantité.
     */
    public Map<Boisson, Integer> getStockMap() {
        return stockMap;
    }
}
