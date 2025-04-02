package java.dao;


import java.util.List;


/**
 * L'interface IDao est une interface générique qui définit les opérations de base (CRUD) pour
 * l'accès aux données dans notre application. Son but est de centraliser et d'abstraire la logique
 * de persistance (connexion à la base, exécution de requêtes SQL, etc.) afin que le reste de l'application,
 * notamment la couche métier, n'ait pas à se soucier des détails d'implémentation de la base de données.
 *
 * Concrètement, chaque Data Access Object (DAO) qui gère une entité spécifique (comme Cocktail, Boisson, etc.)
 * doit implémenter cette interface. Cela garantit que tous les DAO possèdent des méthodes uniformes pour :
 *
 *   - Récupérer une entité par son identifiant (findById)
 *   - Récupérer toutes les entités d'un type donné (findAll)
 *   - Insérer une nouvelle entité dans la base de données (insert)
 *   - Mettre à jour une entité existante (update)
 *   - Supprimer une entité par son identifiant (delete)
 *
 * Cette approche présente plusieurs avantages :
 *
 *   - **Séparation des préoccupations :** Le code qui interagit avec la base de données est isolé
 *     dans des classes dédiées, ce qui simplifie la maintenance et l'évolution du code.
 *   - **Réutilisabilité :** Les DAO peuvent être réutilisés dans différents contrôleurs ou services,
 *     facilitant ainsi le développement et les tests.
 *   - **Modularité :** En cas de changement dans la technologie de persistance, il suffit de modifier
 *     l'implémentation des DAO sans impacter le reste de l'application.
 *
 * @param <T> Le type d'entité sur lequel les opérations CRUD seront effectuées.
 */
public interface IDao<T> {
    T findById(int id);
    List<T> findAll();
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(int id);
}