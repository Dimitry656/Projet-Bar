package javaCode.dao;

import javaCode.Entities.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IngredientDAO gère les opérations CRUD sur la table "ingredients".
 * Il permet de récupérer, insérer, mettre à jour et supprimer des enregistrements pour les ingrédients.
 */
public class IngredientDAO implements IDao<Ingredient> {

    private static final String SELECT_BY_ID = "SELECT id, nom, contenance, prix, degre_sucre, quantite_disponible FROM ingredients WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT id, nom, contenance, prix, degre_sucre, quantite_disponible FROM ingredients";
    private static final String INSERT =
            "INSERT INTO ingredients (nom, contenance, prix, degre_sucre, quantite_disponible) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE ingredients SET nom = ?, contenance = ?, prix = ?, degre_sucre = ?, quantite_disponible = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM ingredients WHERE id = ?";

    @Override
    public Ingredient findById(int id) {
        Ingredient ingredient = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setNom(rs.getString("nom"));
                ingredient.setContenance(rs.getDouble("contenance"));
                ingredient.setPrix(rs.getDouble("prix"));
                ingredient.setDegreSucre(rs.getDouble("degre_sucre"));
                ingredient.setQuantiteDisponible(rs.getDouble("quantite_disponible"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setNom(rs.getString("nom"));
                ingredient.setContenance(rs.getDouble("contenance"));
                ingredient.setPrix(rs.getDouble("prix"));
                ingredient.setDegreSucre(rs.getDouble("degre_sucre"));
                ingredient.setQuantiteDisponible(rs.getDouble("quantite_disponible"));
                list.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Ingredient ingredient) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ingredient.getNom());
            stmt.setDouble(2, ingredient.getContenance());
            stmt.setDouble(3, ingredient.getPrix());
            stmt.setDouble(4, ingredient.getDegreSucre());
            stmt.setDouble(5, ingredient.getQuantiteDisponible());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    ingredient.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Ingredient ingredient) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, ingredient.getNom());
            stmt.setDouble(2, ingredient.getContenance());
            stmt.setDouble(3, ingredient.getPrix());
            stmt.setDouble(4, ingredient.getDegreSucre());
            stmt.setDouble(5, ingredient.getQuantiteDisponible());
            stmt.setInt(6, ingredient.getId());
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
}
