
package javaCode;
import javaCode.Entities.Boisson;
import javaCode.Entities.Ingredient;
import javaCode.Entities.Stock;
import javaCode.Entities.IngredientStock;
import javaCode.Entities.Cocktail;

import javaCode.dao.BoissonDAO;
import javaCode.dao.IngredientDAO;


import javaCode.controller.IngredientStockController;
import javaCode.controller.StockController;
import javaCode.controller.CocktailController;



import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * ne pas executer sa va foutre le bordel en base car il ny' a pas les controller de mis en place cetais juste pour tester
 */
public class Test {

    public static void main(String[] args) {
        CocktailController cocktailController = new CocktailController();

        System.out.println(cocktailController.getAllCocktails());
    }
}
