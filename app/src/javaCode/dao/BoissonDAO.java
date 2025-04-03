package javaCode.dao;

import javaCode.Entities.Boisson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO pour la gestion des opérations sur les boissons.
 * Elle implémente les opérations CRUD pour la table 'boissons' en base de données.
 */
public class BoissonDAO implements IDao<Boisson> {

    private static final String SELECT_BY_ID = "SELECT id, nom, contenance, prix, degrealcool, degresucre FROM boissons WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, nom, contenance, prix, degrealcool, degresucre FROM boissons";
    private static final String INSERT = "INSERT INTO boissons (nom, contenance, prix, degrealcool, degresucre) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE boissons SET nom = ?, contenance = ?, prix = ?, degrealcool = ?, degresucre = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM boissons WHERE id = ?";

    @Override
    public Boisson findById(int id) {
        Boisson boisson = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boisson = createBoissonFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boisson;
    }

    public Boisson findByNom(String nom) {
        Boisson boisson = null;
        String sql = "SELECT id, nom, contenance, prix, degre_alcool, degre_sucre FROM boissons WHERE nom = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boisson = new Boisson() { };  // ou utilisez une classe concrète si vous en avez une
                boisson.setId(rs.getInt("id"));
                boisson.setNom(rs.getString("nom"));
                boisson.setContenance(rs.getDouble("contenance"));
                boisson.setPrix(rs.getDouble("prix"));
                boisson.setDegreAlcool(rs.getDouble("degre_alcool"));
                boisson.setDegreSucre(rs.getDouble("degre_sucre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boisson;
    }


    @Override
    public List<Boisson> findAll() {
        List<Boisson> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Boisson boisson = createBoissonFromResultSet(rs);
                list.add(boisson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Boisson boisson) {
        try {
            // Vérifier si une boisson avec ce nom existe déjà
            if (findByNom(boisson.getNom()) != null) {
                System.out.println("Doublon détecté pour le nom de boisson: " + boisson.getNom());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, boisson.getNom());
            stmt.setDouble(2, boisson.getContenance());
            stmt.setDouble(3, boisson.getPrix());
            stmt.setDouble(4, boisson.getDegreAlcool());
            stmt.setDouble(5, boisson.getDegreSucre());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    boisson.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Boisson boisson) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, boisson.getNom());
            stmt.setDouble(2, boisson.getContenance());
            stmt.setDouble(3, boisson.getPrix());
            stmt.setDouble(4, boisson.getDegreAlcool());
            stmt.setDouble(5, boisson.getDegreSucre());
            stmt.setInt(6, boisson.getId());
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
     * Méthode utilitaire pour créer une instance de Boisson à partir d'un ResultSet.
     * Comme Boisson est abstraite, nous utilisons ici une classe anonyme.
     *
     * @param rs Le ResultSet issu d'une requête SQL.
     * @return une instance de Boisson contenant les données récupérées.
     * @throws SQLException si une erreur SQL survient.
     */
    private Boisson createBoissonFromResultSet(ResultSet rs) throws SQLException {
        Boisson boisson = new Boisson() {
            // Implémentation anonyme de Boisson (vous pouvez créer une classe concrète à la place)
        };
        boisson.setId(rs.getInt("id"));
        boisson.setNom(rs.getString("nom"));
        boisson.setContenance(rs.getDouble("contenance"));
        boisson.setPrix(rs.getDouble("prix"));
        boisson.setDegreAlcool(rs.getDouble("degrealcool"));
        boisson.setDegreSucre(rs.getDouble("degresucre"));
        return boisson;
    }
}