Documentation d’Utilisation de l’Architecture du Projet
1. Structure Globale du Projet
   Le projet est organisé selon une architecture en couches qui sépare :

Les Entités (Entities) :
Ce sont des classes Java (POJO) qui représentent nos objets métier. Par exemple :

Boisson, Ingredient, Cocktail, Stock, IngredientStock, etc.
Ces classes contiennent uniquement des données et de la logique de calcul (comme le calcul du prix, de la contenance, du degré d’alcool et de sucre).

La Couche DAO (Data Access Object) :
Ces classes (BoissonDAO, IngredientDAO, CocktailDAO, StockDAO, IngredientStockDAO, etc.) s’occupent de la persistance des entités dans la base de données. Elles effectuent les opérations CRUD (Create, Read, Update, Delete) en interagissant directement avec la base via JDBC.

Les Contrôleurs :
Ces classes (CocktailController, StockController, IngredientController, IngredientStockController, etc.) centralisent la logique métier et orchestrent les appels vers les DAO.
Point clé : Toute interaction concernant la création, la modification ou la suppression d’objets doit se faire via ces contrôleurs.
Par exemple, lorsqu’un cocktail est modifié (changement de nom, d’ingrédients ou de boissons), l’interface ne modifie pas directement l’objet Cocktail. Elle appelle les méthodes du CocktailController pour mettre à jour l’objet en mémoire et déclencher la persistance (via la méthode updateCocktail).

La Couche d’Interface (View) :
C’est ici que votre collègue intervient. Il s’agit de la partie qui affichera l’interface utilisateur (JSP, Swing, JavaFX, etc.) et qui appellera les contrôleurs pour récupérer ou modifier les données.

2. Rôle et Utilisation des Contrôleurs
   A. CocktailController
   Le CocktailController offre les fonctionnalités suivantes :

Création et Enregistrement :

addCocktail(Cocktail cocktail) :

Si le cocktail est préenregistré (la propriété sauvegarde est vraie), il est inséré dans la base de données via le DAO.

Pour un cocktail « sur mesure » (cocktail non sauvegardé), il n'est pas enregistré en base, mais peut être utilisé pour servir immédiatement.

Mise à jour :

updateCocktail(Cocktail cocktail) :

Important : Toute modification sur un cocktail (changement de nom, ajout ou suppression d’ingrédients/boissons) doit être suivie d’un appel explicite à updateCocktail(cocktail) dans le CocktailController pour persister ces modifications dans la base.

Suppression et Récupération :

deleteCocktail(int id), getCocktailById(int id), getAllCocktails().

Service d’un Cocktail :

serveCocktail(Cocktail cocktail) :

Cette méthode vérifie que le stock de chaque composant (boissons et ingrédients) est suffisant.

Si c’est le cas, elle déduit le volume requis du stock en utilisant les méthodes des contrôleurs de stock (StockController et IngredientStockController).

Le résultat de l'opération indique si le cocktail a pu être servi correctement.

Disponibilité des Cocktails :

getAvailableCocktails() :

Retourne une map associant chaque cocktail enregistré à un nombre de portions réalisables, calculé en fonction du stock disponible.

B. Autres Contrôleurs
StockController et IngredientStockController :
Ces contrôleurs gèrent la mise à jour des stocks.
Ils offrent des méthodes comme :

isVolumeAvailable(Composant, volumeNeeded) : Vérifie la disponibilité.

updateAfterService(Composant, volumeUsed) : Déduit le volume utilisé du stock.

getTotalAvailableVolume(Composant) : Calcule le volume total disponible.

Votre collègue utilisera ces contrôleurs indirectement via le CocktailController, sans avoir à gérer les détails de la persistance des stocks.

3. Directives pour l’Implémentation des Interfaces
   Votre collègue doit se concentrer sur la partie « View », c’est-à-dire sur l’interface utilisateur. Voici quelques points importants :

Interaction avec les Contrôleurs :

Pour afficher les données :

Utiliser getAllCocktails(), getCocktailById(int id), getAvailableCocktails() pour afficher les cocktails disponibles, leur détail, et le nombre de portions réalisables.

Utiliser les méthodes analogues des autres contrôleurs pour afficher la liste des boissons, ingrédients et leur stock.

Pour modifier un cocktail :

Lorsqu’un utilisateur modifie un cocktail (par exemple, change le nom, ajoute un ingrédient ou une boisson, ou modifie une quantité), l’interface doit mettre à jour l’objet Cocktail en appelant les méthodes correspondantes (ex. cocktail.ajouterBoisson(boisson, volume)).

Important : Après toute modification, il faut appeler explicitement updateCocktail(cocktail) du CocktailController pour persister les changements.

Exemple d’utilisation :

java
Copy
// Dans votre code d'interface, lorsqu'un utilisateur modifie un cocktail :
Cocktail cocktail = cocktailController.getCocktailById(id);
cocktail.setNom("Nouveau Nom");
cocktail.ajouterIngredient(citron, 15.0);
// Mise à jour en base :
cocktailController.updateCocktail(cocktail);
Pour servir un cocktail :

Lorsque l'utilisateur souhaite servir un cocktail, appeler la méthode serveCocktail(cocktail). Celle-ci vérifiera la disponibilité des stocks et mettra à jour les quantités si le cocktail peut être servi.

Gestion des Erreurs et Feedback :

Chaque méthode des contrôleurs renvoie un booléen (true/false) indiquant le succès ou l’échec de l’opération.

L’interface doit gérer ces retours pour afficher des messages d’erreur ou de confirmation à l’utilisateur.

Par exemple, si serveCocktail(cocktail) renvoie false, afficher un message "Stock insuffisant pour servir ce cocktail".

4. Récapitulatif de la Procédure d'Utilisation
   Création et Modification d'un Cocktail :

Créer un objet Cocktail en mémoire (soit sauvegardé, soit sur mesure).

Modifier l’objet en utilisant les méthodes internes (ajouter/retirer des boissons ou ingrédients, changer le nom, etc.).

IMPORTANT : Appeler updateCocktail(cocktail) via le CocktailController pour enregistrer les modifications dans la base de données.

Affichage et Consultation :

Utiliser getAllCocktails() et getCocktailById(int id) pour afficher les données.

Utiliser getAvailableCocktails() pour connaître le nombre de portions servables pour chaque cocktail.

Service d'un Cocktail :

Lorsqu’un cocktail doit être servi, appeler serveCocktail(cocktail).

Le CocktailController vérifiera et mettra à jour les stocks via les StockControllers associés.

Conclusion
Votre collègue n’a pas à se soucier de la logique de calcul ou de persistance. Il devra :

Appeler les méthodes des contrôleurs pour récupérer, modifier et servir les cocktails.

Veiller à déclencher explicitement une mise à jour en base via updateCocktail après toute modification sur un cocktail.

Utiliser les retours des méthodes (booléens ou objets) pour afficher des messages appropriés dans l’interface utilisateur.

Cette documentation doit fournir une vue d’ensemble claire du fonctionnement de notre architecture, facilitant l’intégration des interfaces utilisateur et garantissant une utilisation cohérente des contrôleurs et des entités.