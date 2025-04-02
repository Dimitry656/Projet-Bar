package java.dao;

import java.Entities.Stock;
import java.Entities.Boisson;
import java.sql.*;

/**
 * DAO pour la gestion du stock global.
 * Cette classe permet de charger et de mettre à jour les quantités des boissons en base de données.
 */
public class StockDAO {

    private static final String SELECT_ALL = "SELECT boisson_id, quantite FROM stock";
    private static final String SELECT_BY_BOISSON = "SELECT quantite FROM stock WHERE boisson_id = ?";
    private static final String INSERT = "INSERT INTO stock (boisson_id, quantite) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE stock SET quantite = ? WHERE boisson_id = ?";
    private static final String DELETE = "DELETE FROM stock WHERE boisson_id = ?";

    /**
     * Charge l'ensemble du stock depuis la base de données et le retourne sous forme d'un objet Stock.
     *
     * @return un objet Stock contenant toutes les entrées de stock.
     */
    public Stock loadStock() {
        Stock stock = new Stock();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            BoissonDAO boissonDAO = new BoissonDAO();
            while (rs.next()) {
                int boissonId = rs.getInt("boisson_id");
                int quantite = rs.getInt("quantite");
                Boisson boisson = boissonDAO.findById(boissonId);
                if (boisson != null) {
                    stock.ajouterBoisson(boisson, quantite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    /**
     * Ajoute une quantité pour une boisson donnée dans la base de données.
     * Si la boisson existe déjà dans le stock, sa quantité est incrémentée.
     * Sinon, une nouvelle entrée est insérée.
     *
     * @param b         La boisson.
     * @param quantite  La quantité à ajouter.
     * @return true si l'opération est réussie, false sinon.
     */
    public boolean ajouterBoisson(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return false;
        }
        int quantiteExistante = getQuantite(b);
        try (Connection conn = DBConnection.getConnection()) {
            if (quantiteExistante > 0) {
                // Mise à jour de la quantité existante
                try (PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
                    stmt.setInt(1, quantiteExistante + quantite);
                    stmt.setInt(2, b.getId());
                    return stmt.executeUpdate() > 0;
                }
            } else {
                // Insertion d'une nouvelle entrée
                try (PreparedStatement stmt = conn.prepareStatement(INSERT)) {
                    stmt.setInt(1, b.getId());
                    stmt.setInt(2, quantite);
                    return stmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retire une quantité pour une boisson donnée dans la base de données.
     * Si la quantité restante devient nulle ou négative, l'entrée est supprimée.
     *
     * @param b         La boisson.
     * @param quantite  La quantité à retirer.
     * @return true si l'opération est réussie, false sinon.
     */
    public boolean retirerBoisson(Boisson b, int quantite) {
        if (b == null || quantite <= 0) {
            return false;
        }
        int quantiteExistante = getQuantite(b);
        if (quantiteExistante < quantite) {
            // Stock insuffisant
            return false;
        }
        try (Connection conn = DBConnection.getConnection()) {
            if (quantiteExistante == quantite) {
                // Supprimer l'enregistrement si la quantité tombe à zéro
                try (PreparedStatement stmt = conn.prepareStatement(DELETE)) {
                    stmt.setInt(1, b.getId());
                    return stmt.executeUpdate() > 0;
                }
            } else {
                // Mettre à jour avec la nouvelle quantité
                try (PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
                    stmt.setInt(1, quantiteExistante - quantite);
                    stmt.setInt(2, b.getId());
                    return stmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Vérifie si la quantité demandée pour une boisson est disponible dans la base de données.
     *
     * @param b         La boisson.
     * @param quantite  La quantité demandée.
     * @return true si la quantité disponible est suffisante, false sinon.
     */
    public boolean verifierDisponibilite(Boisson b, int quantite) {
        return getQuantite(b) >= quantite;
    }

    /**
     * Retourne la quantité disponible pour une boisson donnée dans la base de données.
     *
     * @param b La boisson.
     * @return la quantité disponible, ou 0 si aucune entrée n'existe.
     */
    public int getQuantite(Boisson b) {
        if (b == null) {
            return 0;
        }
        int quantite = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_BOISSON)) {
            stmt.setInt(1, b.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    quantite = rs.getInt("quantite");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantite;
    }
}
