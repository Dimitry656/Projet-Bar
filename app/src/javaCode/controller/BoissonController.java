package javaCode.controller;

import javaCode.Entities.Boisson;
import javaCode.dao.BoissonDAO;
import java.util.List;

/**
 * BoissonController centralise la logique métier liée aux boissons.
 * Il fournit des méthodes pour rechercher par ID, par nom, récupérer toutes les boissons,
 * ainsi qu'ajouter, mettre à jour et supprimer des enregistrements.
 *
 * Ce contrôleur est utilisé par l'interface pour manipuler les données de Boisson sans se soucier
 * des détails de persistance.
 */
public class BoissonController {

    private BoissonDAO boissonDAO;

    public BoissonController() {
        this.boissonDAO = new BoissonDAO();
    }

    /**
     * Recherche une boisson par son identifiant.
     *
     * @param id l'identifiant de la boisson.
     * @return la boisson correspondante, ou null si elle n'existe pas.
     */
    public Boisson getBoissonById(int id) {
        return boissonDAO.findById(id);
    }

    /**
     * Recherche une boisson par son nom.
     *
     * @param nom le nom de la boisson.
     * @return la boisson correspondante, ou null si elle n'existe pas.
     */
    public Boisson getBoissonByNom(String nom) {
        return boissonDAO.findByNom(nom);
    }

    /**
     * Récupère la liste de toutes les boissons.
     *
     * @return une liste de boissons.
     */
    public List<Boisson> getAllBoissons() {
        return boissonDAO.findAll();
    }

    /**
     * Ajoute une nouvelle boisson.
     *
     * @param boisson la boisson à ajouter.
     * @return true si l'insertion réussit, false sinon (par exemple, si le nom est déjà utilisé).
     */
    public boolean addBoisson(Boisson boisson) {
        return boissonDAO.insert(boisson);
    }

    /**
     * Met à jour une boisson existante.
     *
     * @param boisson la boisson à mettre à jour.
     * @return true si la mise à jour réussit, false sinon.
     */
    public boolean updateBoisson(Boisson boisson) {
        return boissonDAO.update(boisson);
    }

    /**
     * Supprime une boisson par son identifiant.
     *
     * @param id l'identifiant de la boisson à supprimer.
     * @return true si la suppression réussit, false sinon.
     */
    public boolean deleteBoisson(int id) {
        return boissonDAO.delete(id);
    }
}
