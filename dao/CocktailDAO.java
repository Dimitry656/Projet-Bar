package dao;

import Entities.Cocktail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// pour info dao= Data Access Object (centralisation de la gestion de l'acces a la database et des requetes sur l'enttité en question
// actuellement généré automatiquement a retravailler completement

public class CocktailDAO implements IDao<Cocktail> {

    private static final String SELECT_BY_ID = "SELECT id, nom, prix FROM cocktails WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, nom, prix FROM cocktails";
    private static final String INSERT = "INSERT INTO cocktails (nom, prix) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE cocktails SET nom = ?, prix = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM cocktails WHERE id = ?";

    @Override
    public Cocktail findById(int id) {
        Cocktail cocktail = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cocktail = new Cocktail();
                cocktail.setId(rs.getInt("id"));
                cocktail.setNom(rs.getString("nom"));
                cocktail.setPrix(rs.getDouble("prix"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cocktail;
    }

    @Override
    public List<Cocktail> findAll() {
        List<Cocktail> cocktails = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cocktail cocktail = new Cocktail();
                cocktail.setId(rs.getInt("id"));
                cocktail.setNom(rs.getString("nom"));
                cocktail.setPrix(rs.getDouble("prix"));
                cocktails.add(cocktail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cocktails;
    }

    @Override
    public boolean insert(Cocktail cocktail) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, cocktail.getNom());
            stmt.setDouble(2, cocktail.getPrix());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Cocktail cocktail) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, cocktail.getNom());
            stmt.setDouble(2, cocktail.getPrix());
            stmt.setInt(3, cocktail.getId());
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
