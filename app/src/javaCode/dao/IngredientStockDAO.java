package javaCode.dao;

import javaCode.Entities.IngredientStock;
import javaCode.Entities.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IngredientStockDAO gère les opérations CRUD sur la table "ingredient_stock".
 * Cette classe est structurée de manière analogue au StockDAO des boissons et utilise
 * le pourcentageRestant pour calculer le volume disponible.
 */
public class IngredientStockDAO implements IDao<IngredientStock> {

    private static final String SELECT_BY_ID =
            "SELECT id, ingredient_id, date_peremption, pourcentage_restant FROM ingredient_stock WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT id, ingredient_id, date_peremption, pourcentage_restant FROM ingredient_stock";
    private static final String SELECT_BY_INGREDIENT =
            "SELECT id, ingredient_id, date_peremption, pourcentage_restant FROM ingredient_stock WHERE ingredient_id = ?";
    private static final String INSERT =
            "INSERT INTO ingredient_stock (ingredient_id, date_peremption, pourcentage_restant) VALUES (?, ?, ?)";
    private static final String UPDATE =
            "UPDATE ingredient_stock SET ingredient_id = ?, date_peremption = ?, pourcentage_restant = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM ingredient_stock WHERE id = ?";

    @Override
    public IngredientStock findById(int id) {
        IngredientStock stock = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stock = createIngredientStockFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    @Override
    public List<IngredientStock> findAll() {
        List<IngredientStock> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                IngredientStock stock = createIngredientStockFromResultSet(rs);
                list.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Récupère toutes les entrées de stock pour un ingrédient donné.
     *
     * @param ingredient l'ingrédient concerné.
     * @return une liste d'IngredientStock.
     */
    public List<IngredientStock> findByIngredient(Ingredient ingredient) {
        List<IngredientStock> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_INGREDIENT)) {
            stmt.setInt(1, ingredient.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    IngredientStock stock = createIngredientStockFromResultSet(rs);
                    list.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(IngredientStock stock) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, stock.getIngredient().getId());
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
    public boolean update(IngredientStock stock) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setInt(1, stock.getIngredient().getId());
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
     * Construit un objet IngredientStock à partir d'un ResultSet.
     *
     * @param rs le ResultSet issu d'une requête SQL.
     * @return un objet IngredientStock initialisé.
     * @throws SQLException en cas d'erreur d'accès aux données.
     */
    private IngredientStock createIngredientStockFromResultSet(ResultSet rs) throws SQLException {
        IngredientStock stock = new IngredientStock();
        stock.setId(rs.getInt("id"));
        int ingredientId = rs.getInt("ingredient_id");
        // Récupération de l'ingrédient via IngredientDAO
        IngredientDAO ingredientDAO = new IngredientDAO();
        Ingredient ingredient = ingredientDAO.findById(ingredientId);
        stock.setIngredient(ingredient);
        stock.setPourcentageRestant(rs.getDouble("pourcentage_restant"));
        Date datePeremption = rs.getDate("date_peremption");
        stock.setDatePeremption(datePeremption);
        return stock;
    }
}
