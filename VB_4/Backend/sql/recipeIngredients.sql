SELECT  ingredients.iname,
        amounts.amount,
        ingredients.measuretype,
        ingredients.itype
        
FROM recipes, ingredients, amounts
WHERE recipes.recipe_name = "Grilled Cheese" AND amounts.ingredient_id = ingredients.ingredient_id