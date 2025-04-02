package java.dao;

import java.Entities.Cocktail;
import java.Entities.Boisson;
import java.Entities.Ingredient;
import java.sql.*;
import java.util.*;

/**
 * Le CocktailDAO gère les opérations CRUD sur la table "cocktails"
 * ainsi que la gestion des associations dans les tables "cocktail_boissons" et "cocktail_ingredients".
 */
public class CocktailDAO implements IDao<Cocktail> {

    private static final String SELECT_BY_ID = "SELECT id, nom, prix, degre_alcool, contenance, degre_sucre, sauvegarde FROM cocktails WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id FROM cocktails";
    private static final String INSERT_COCKTAIL = "INSERT INTO cocktails (nom, prix, degre_alcool, contenance, degre_sucre, sauvegarde) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COCKTAIL = "UPDATE cocktails SET nom = ?, prix = ?, degre_alcool = ?, contenance = ?, degre_sucre = ?, sauvegarde = ? WHERE id = ?";
    private static final String DELETE_COCKTAIL = "DELETE FROM cocktails WHERE id = ?";

    private static final String INSERT_COCKTAIL_BOISSON = "INSERT INTO cocktail_boissons (cocktail_id, boisson_id, volume_utilise) VALUES (?, ?, ?)";
    private static final String INSERT_COCKTAIL_INGREDIENT = "INSERT INTO cocktail_ingredients (cocktail_id, ingredient_id, volume_utilise) VALUES (?, ?, ?)";

    private static final String SELECT_COCKTAIL_BOISSONS = "SELECT boisson_id, volume_utilise FROM cocktail_boissons WHERE cocktail_id = ?";
    private static final String SELECT_COCKTAIL_INGREDIENTS = "SELECT ingredient_id, volume_utilise FROM cocktail_ingredients WHERE cocktail_id = ?";

    private static final String DELETE_COCKTAIL_BOISSONS = "DELETE FROM cocktail_boissons WHERE cocktail_id = ?";
    private static final String DELETE_COCKTAIL_INGREDIENTS = "DELETE FROM cocktail_ingredients WHERE cocktail_id = ?";

    @Override
    public Cocktail findById(int id) {
        Cocktail cocktail = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                double degreAlcool = rs.getDouble("degre_alcool");
                double contenance = rs.getDouble("contenance");
                double degreSucre = rs.getDouble("degre_sucre");
                boolean sauvegarde = rs.getBoolean("sauvegarde");
                // Initialisation des maps vides
                Map<Boisson, Double> boissonsUtilisees = new HashMap<>();
                Map<Ingredient, Double> ingredientsUtilises = new HashMap<>();
                cocktail = new Cocktail(nom, boissonsUtilisees, ingredientsUtilises, sauvegarde);
                cocktail.setId(id);

                // Récupération des associations boissons
                try (PreparedStatement stmtBoissons = conn.prepareStatement(SELECT_COCKTAIL_BOISSONS)) {
                    stmtBoissons.setInt(1, id);
                    ResultSet rsBoissons = stmtBoissons.executeQuery();
                    BoissonDAO boissonDAO = new BoissonDAO();
                    while(rsBoissons.next()){
                        int boissonId = rsBoissons.getInt("boisson_id");
                        double volumeUtilise = rsBoissons.getDouble("volume_utilise");
                        Boisson boisson = boissonDAO.findById(boissonId);
                        if(boisson != null){
                            boissonsUtilisees.put(boisson, volumeUtilise);
                        }
                    }
                }
                // Récupération des associations ingrédients
                try (PreparedStatement stmtIngredients = conn.prepareStatement(SELECT_COCKTAIL_INGREDIENTS)) {
                    stmtIngredients.setInt(1, id);
                    ResultSet rsIngredients = stmtIngredients.executeQuery();
                    IngredientDAO ingredientDAO = new IngredientDAO();
                    while(rsIngredients.next()){
                        int ingredientId = rsIngredients.getInt("ingredient_id");
                        double volumeUtilise = rsIngredients.getDouble("volume_utilise");
                        Ingredient ingredient = ingredientDAO.findById(ingredientId);
                        if(ingredient != null){
                            ingredientsUtilises.put(ingredient, volumeUtilise);
                        }
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return cocktail;
    }

    @Override
    public List<Cocktail> findAll() {
        List<Cocktail> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                int id = rs.getInt("id");
                Cocktail cocktail = findById(id);
                if(cocktail != null){
                    list.add(cocktail);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Cocktail cocktail) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_COCKTAIL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cocktail.getNom());
            stmt.setDouble(2, cocktail.calculerPrix());
            stmt.setDouble(3, cocktail.getDegreAlcool());
            stmt.setDouble(4, cocktail.getContenance());
            stmt.setDouble(5, cocktail.getDegreSucre());
            stmt.setBoolean(6, cocktail.isSauvegarde());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0){
                ResultSet keys = stmt.getGeneratedKeys();
                if(keys.next()){
                    cocktail.setId(keys.getInt(1));
                }
                insertAssociations(conn, cocktail);
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Insère les associations du cocktail dans les tables "cocktail_boissons" et "cocktail_ingredients".
     * @param conn la connexion active
     * @param cocktail l'objet cocktail contenant les associations
     * @throws SQLException en cas d'erreur SQL
     */
    private void insertAssociations(Connection conn, Cocktail cocktail) throws SQLException {
        // Insertion dans cocktail_boissons
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_COCKTAIL_BOISSON)) {
            for(Map.Entry<Boisson, Double> entry : cocktail.getBoissonsUtilisees().entrySet()){
                stmt.setInt(1, cocktail.getId());
                stmt.setInt(2, entry.getKey().getId());
                stmt.setDouble(3, entry.getValue());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        // Insertion dans cocktail_ingredients
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_COCKTAIL_INGREDIENT)) {
            for(Map.Entry<Ingredient, Double> entry : cocktail.getIngredientsUtilises().entrySet()){
                stmt.setInt(1, cocktail.getId());
                stmt.setInt(2, entry.getKey().getId());
                stmt.setDouble(3, entry.getValue());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public boolean update(Cocktail cocktail) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_COCKTAIL)) {
            stmt.setString(1, cocktail.getNom());
            stmt.setDouble(2, cocktail.calculerPrix());
            stmt.setDouble(3, cocktail.getDegreAlcool());
            stmt.setDouble(4, cocktail.getContenance());
            stmt.setDouble(5, cocktail.getDegreSucre());
            stmt.setBoolean(6, cocktail.isSauvegarde());
            stmt.setInt(7, cocktail.getId());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0){
                // Supprime les associations existantes puis insère les nouvelles
                try (PreparedStatement delStmt1 = conn.prepareStatement(DELETE_COCKTAIL_BOISSONS);
                     PreparedStatement delStmt2 = conn.prepareStatement(DELETE_COCKTAIL_INGREDIENTS)) {
                    delStmt1.setInt(1, cocktail.getId());
                    delStmt2.setInt(1, cocktail.getId());
                    delStmt1.executeUpdate();
                    delStmt2.executeUpdate();
                }
                insertAssociations(conn, cocktail);
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            // Supprime d'abord les associations
            try (PreparedStatement delStmt1 = conn.prepareStatement(DELETE_COCKTAIL_BOISSONS);
                 PreparedStatement delStmt2 = conn.prepareStatement(DELETE_COCKTAIL_INGREDIENTS)) {
                delStmt1.setInt(1, id);
                delStmt2.setInt(1, id);
                delStmt1.executeUpdate();
                delStmt2.executeUpdate();
            }
            // Supprime le cocktail
            try (PreparedStatement stmt = conn.prepareStatement(DELETE_COCKTAIL)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
