package javaCode.controller;

import javaCode.Entities.Stock;
import javaCode.Entities.Boisson;
import javaCode.dao.StockDAO;

import java.util.List;

/**
 * Le StockController gère la logique métier relative aux stocks de boissons.
 * Il permet d'ajouter, mettre à jour, supprimer et récupérer des entrées de stock,
 * ainsi que de mettre à jour le stock après le service d'un cocktail en fonction du volume utilisé.
 */
public class StockController {

    private final StockDAO stockDAO;

    public StockController() {
        this.stockDAO = new StockDAO();
    }

    /**
     * Ajoute une nouvelle entrée de stock dans la base de données.
     *
     * @param stock L'entrée de stock à ajouter.
     * @return true si l'insertion réussit, false sinon.
     */
    public boolean addStock(Stock stock) {
        return stockDAO.insert(stock);
    }

    /**
     * Met à jour une entrée de stock existante.
     *
     * @param stock L'entrée de stock à mettre à jour.
     * @return true si la mise à jour réussit, false sinon.
     */
    public boolean updateStock(Stock stock) {
        return stockDAO.update(stock);
    }

    /**
     * Supprime une entrée de stock par son identifiant.
     *
     * @param id L'identifiant de l'entrée à supprimer.
     * @return true si la suppression réussit, false sinon.
     */
    public boolean deleteStock(int id) {
        return stockDAO.delete(id);
    }

    /**
     * Récupère l'ensemble des entrées de stock.
     *
     * @return Une liste d'objets Stock.
     */
    public List<Stock> getAllStocks() {
        return stockDAO.findAll();
    }

    /**
     * Récupère les entrées de stock associées à une boisson donnée.
     *
     * @param boisson La boisson dont on souhaite récupérer le stock.
     * @return Une liste d'objets Stock correspondant.
     */
    public List<Stock> getStocksByBoisson(Boisson boisson) {
        return stockDAO.findByBoisson(boisson);
    }




    /**
     * Vérifie si le volume total disponible pour une boisson est suffisant.
     * Le calcul se fait en sommant pour chaque entrée du stock le volume disponible (pourcentageRestant converti en cl).
     *
     * @param boisson      La boisson concernée.
     * @param volumeNeeded Le volume nécessaire en cl.
     * @return true si le volume total disponible est supérieur ou égal au volume nécessaire, false sinon.
     */
    public boolean isVolumeAvailable(Boisson boisson, double volumeNeeded) {
        List<Stock> stocks = getStocksByBoisson(boisson);
        double totalAvailable = 0;
        for (Stock stock : stocks) {
            // Calcul du volume disponible en cl pour chaque entrée
            double availableVolume = (stock.getPourcentageRestant() / 100.0) * boisson.getContenance();
            totalAvailable += availableVolume;
        }
        return totalAvailable >= volumeNeeded;
    }

    public double getTotalAvailableVolume (Boisson boisson) {
        List<Stock> stocks = getStocksByBoisson(boisson);
        double totalAvailable = 0;
        for (Stock stock : stocks) {
            totalAvailable += stock.getPourcentageRestant()/100.0 * boisson.getContenance();
        }
        return totalAvailable;
    }



    /**
     * Met à jour le stock après le service d'un cocktail.
     * Avant de déduire le volume, la méthode vérifie que le volume total disponible est suffisant.
     * Les entrées de stock sont triées par date de péremption croissante (les plus proches en premier),
     * et le volume consommé est déduit de manière itérative.
     *
     * Exemple : Si une bouteille de 1L a 23% restant (soit 23 cl) et que 25 cl sont consommés,
     * la méthode mettra à jour cette bouteille à 0% et déduira les 2 cl manquants de la bouteille suivante.
     *
     * @param boisson    La boisson concernée.
     * @param volumeUsed Le volume utilisé en cl.
     * @return true si l'opération est réalisée avec succès, false si le volume total disponible est insuffisant ou en cas d'erreur de mise à jour.
     */
    public boolean updateAfterService(Boisson boisson, double volumeUsed) {
        // Vérifier d'abord que le volume total disponible est suffisant
        if (!isVolumeAvailable(boisson, volumeUsed)) {
            return false;
        }

        // Récupère toutes les entrées de stock pour la boisson
        List<Stock> stocks = getStocksByBoisson(boisson);

        // Trie les stocks par date de péremption croissante (les dates les plus proches en premier)
        // Les stocks sans date de péremption (NULL) seront placés en dernier.
        stocks.sort((s1, s2) -> {
            if (s1.getDatePeremption() == null && s2.getDatePeremption() == null) return 0;
            if (s1.getDatePeremption() == null) return 1;
            if (s2.getDatePeremption() == null) return -1;
            return s1.getDatePeremption().compareTo(s2.getDatePeremption());
        });

        double remainingVolume = volumeUsed;
        for (Stock stock : stocks) {
            // Calcul du volume disponible dans cette entrée (en cl)
            double availableVolume = (stock.getPourcentageRestant() / 100.0) * boisson.getContenance();
            if (availableVolume <= 0) {
                continue;
            }
            if (availableVolume >= remainingVolume) {
                // Cette entrée peut couvrir entièrement le volume restant
                double percentageConsumed = (remainingVolume / boisson.getContenance()) * 100;
                double newPercentage = stock.getPourcentageRestant() - percentageConsumed;
                if (newPercentage < 0) {
                    newPercentage = 0;
                }
                stock.setPourcentageRestant(newPercentage);
                if (!updateStock(stock)) {
                    return false;
                }
                remainingVolume = 0;
                break;
            } else {
                // Consomme entièrement cette entrée et continue avec la suivante
                stock.setPourcentageRestant(0);
                if (!updateStock(stock)) {
                    return false;
                }
                remainingVolume -= availableVolume;
            }
        }
        return remainingVolume == 0;
    }
}
