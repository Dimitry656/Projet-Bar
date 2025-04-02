package java.dao;

import java.Entities.Stock;
import java.Entities.Boisson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des entrées de stock.
 * Cette classe gère les opérations CRUD sur la table "stock" et offre
 * des méthodes pour récupérer les stocks associés à une boisson.
 */
public class StockDAO implements IDao<Stock> {

    private static final String SELECT_BY_ID = "SELECT id, boisson_id, date_peremption, pourcentage_restant FROM stock WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, boisson_id, date_peremption, pourcentage_restant FROM stock";
    private static final String INSERT = "INSERT INTO stock (boisson_id, date_peremption, pourcentage_restant) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE stock SET boisson_id = ?, date_peremption = ?, pourcentage_restant = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM stock WHERE id = ?";

    // Requête pour récupérer toutes les entrées de stock pour une boisson donnée
    private static final String SELECT_BY_BOISSON = "SELECT id, boisson_id, date_peremption, pourcentage_restant FROM stock WHERE boisson_id = ?";

    @Override
    public Stock findById(int id) {
        Stock stock = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stock = createStockFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Stock stock = createStockFromResultSet(rs);
                list.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Stock stock) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, stock.getBoisson().getId());
            if (stock.getDatePeremption() != null) {
                stmt.setDate(2, new java.sql.Date(stock.getDatePeremption().getTime()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }
            stmt.setDouble(3, stock.getPourcentageRestant());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    stock.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Stock stock) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setInt(1, stock.getBoisson().getId());
            if (stock.getDatePeremption() != null) {
                stmt.setDate(2, new java.sql.Date(stock.getDatePeremption().getTime()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }
            stmt.setDouble(3, stock.getPourcentageRestant());
            stmt.setInt(4, stock.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupère toutes les entrées de stock associées à une boisson donnée.
     *
     * @param b La boisson dont on souhaite récupérer le stock.
     * @return Une liste d'objets Stock correspondants.
     */
    public List<Stock> findByBoisson(Boisson b) {
        List<Stock> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_BOISSON)) {
            stmt.setInt(1, b.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Stock stock = createStockFromResultSet(rs);
                    list.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Méthode utilitaire pour construire un objet Stock à partir d'un ResultSet.
     *
     * @param rs Le ResultSet issu d'une requête SQL.
     * @return Un objet Stock avec ses attributs initialisés.
     * @throws SQLException En cas d'erreur d'accès aux données.
     */
    private Stock createStockFromResultSet(ResultSet rs) throws SQLException {
        Stock stock = new Stock();
        stock.setId(rs.getInt("id"));
        int boissonId = rs.getInt("boisson_id");
        // Récupération de l'objet Boisson correspondant via le BoissonDAO
        BoissonDAO boissonDAO = new BoissonDAO();
        Boisson boisson = boissonDAO.findById(boissonId);
        stock.setBoisson(boisson);
        Date datePeremption = rs.getDate("date_peremption");
        stock.setDatePeremption(datePeremption);
        stock.setPourcentageRestant(rs.getDouble("pourcentage_restant"));
        return stock;
    }
}