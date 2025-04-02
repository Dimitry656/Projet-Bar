package java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe DBConnection est une classe utilitaire chargée de centraliser et de gérer la connexion
 * à la base de données PostgreSQL pour notre projet. Elle encapsule les informations de connexion
 * (URL, utilisateur, mot de passe) et fournit une méthode statique pour obtenir une instance de
 * Connection via JDBC.
 *
 * Les avantages de cette approche sont multiples :
 *
 *   - **Centralisation de la configuration :** Les paramètres de connexion sont définis dans une
 *     seule classe, ce qui facilite leur modification et assure une cohérence dans l'ensemble de l'application.
 *
 *   - **Réutilisabilité :** La méthode getConnection() peut être utilisée partout dans le projet
 *     pour établir une connexion à la base, évitant ainsi la duplication du code.
 *
 *   - **Gestion simplifiée des ressources :** En utilisant cette classe, nous pouvons intégrer
 *     facilement des mécanismes de gestion des exceptions et de fermeture automatique des ressources
 *     (par exemple via try-with-resources).
 *
 * Pour utiliser cette classe, assurez-vous d'avoir ajouté le driver PostgreSQL dans le classpath
 * de votre projet. Par exemple, si vous utilisez Maven, ajoutez la dépendance suivante dans votre
 * pom.xml :
 *
 *   <dependency>
 *       <groupId>org.postgresql</groupId>
 *       <artifactId>postgresql</artifactId>
 *       <version>42.5.0</version>
 *   </dependency>
 *
 * L'URL de connexion utilise l'IP "147.93.95.108", le port par défaut PostgreSQL (5432) et la
 * base de données "bar_db". L'utilisateur est "bar_user" et le mot de passe est "projetBar".
 * Ainsi, en appelant DBConnection.getConnection(), vous obtenez une connexion configurée pour accéder
 * à cette base de données.
 */
public class DBConnection {

    // Paramètres de connexion
    private static final String URL = "jdbc:postgresql://147.93.95.108:5432/bar_db";
    private static final String USER = "bar_user";
    private static final String PASSWORD = "projetBar";

    /**
     * Obtient et retourne une connexion JDBC à la base de données.
     *
     * @return une instance de java.sql.Connection permettant d'interagir avec la base de données.
     * @throws SQLException si la connexion échoue, par exemple en cas d'informations de connexion incorrectes.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
