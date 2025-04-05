package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import main.najah.code.RecipeBook;
import main.najah.code.Recipe;

@DisplayName("Recipe Book Tests")
@Execution(ExecutionMode.CONCURRENT)
public class RecipeBookTest {

    private RecipeBook recipeBook;

    @BeforeEach
    void setup() {
        recipeBook = new RecipeBook();
    }

    @Test
    @DisplayName("Test adding a new recipe successfully")
    void testAddRecipeSuccess() {
        Recipe recipe = new Recipe();
        recipe.setName("Coffee");
        boolean added = recipeBook.addRecipe(recipe);
        assertTrue(added, "Recipe should be added successfully");
    }

    @Test
    @DisplayName("Test adding duplicate recipe fails")
    void testAddDuplicateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Tea");
        boolean firstAdd = recipeBook.addRecipe(recipe);
        boolean secondAdd = recipeBook.addRecipe(recipe);
        assertTrue(firstAdd, "First add should succeed");
        assertFalse(secondAdd, "Duplicate recipe should not be added");
    }

    @Test
    @DisplayName("Test deleting an existing recipe")
    void testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Espresso");
        recipeBook.addRecipe(recipe);
        String deletedName = recipeBook.deleteRecipe(0);
        assertEquals("Espresso", deletedName, "Deleted recipe name should match");
        assertNotEquals("Espresso", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Test deleting a recipe from an empty slot")
    void testDeleteFromEmptySlot() {
        String result = recipeBook.deleteRecipe(0);
        assertNull(result, "Deleting empty slot should return null");
    }

    @Test
    @DisplayName("Test editing an existing recipe")
    void testEditRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Latte");
        recipeBook.addRecipe(recipe);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("Cappuccino");

        String oldName = recipeBook.editRecipe(0, newRecipe);
        assertEquals("Latte", oldName);
        assertEquals("", recipeBook.getRecipes()[0].getName(), "New recipe name should be reset to empty");
    }

    @Test
    @DisplayName("Test editing a recipe from an empty slot")
    void testEditFromEmptySlot() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Flat White");
        String result = recipeBook.editRecipe(0, newRecipe);
        assertNull(result, "Editing a null recipe should return null");
    }

    @Test
    @DisplayName("Test addRecipe beyond capacity (should fail on 5th)")
    void testAddMoreThanMaxRecipes() {
        for (int i = 0; i < 4; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Recipe" + i);
            assertTrue(recipeBook.addRecipe(recipe), "Should add Recipe" + i);
        }

        Recipe overflowRecipe = new Recipe();
        overflowRecipe.setName("Overflow");
        assertFalse(recipeBook.addRecipe(overflowRecipe), "Should not add more than 4 recipes");
    }

    @Test
    @DisplayName("Test index out of bounds for delete")
    void testDeleteOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            recipeBook.deleteRecipe(5);
        });
    }

    @Test
    @DisplayName("Test index out of bounds for edit")
    void testEditOutOfBounds() {
        Recipe recipe = new Recipe();
        recipe.setName("OverEdit");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            recipeBook.editRecipe(10, recipe);
        });
    }

    @Test
    @DisplayName("Test recipe operations within timeout")
    void testRecipeTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Recipe recipe = new Recipe();
            recipe.setName("Mocha");
            recipeBook.addRecipe(recipe);
            recipeBook.deleteRecipe(0);
        });
    }

    @ParameterizedTest(name = "Add recipe with name: {0}")
    @ValueSource(strings = {"Americano", "Macchiato", "Mocha"})
    @DisplayName("Parameterized Test for adding recipes")
    void testAddRecipeParameterized(String recipeName) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        boolean added = recipeBook.addRecipe(recipe);
        assertTrue(added, "Recipe " + recipeName + " should be added successfully");
    }

    @Test
    @DisplayName("Test multiple assertions for recipe book")
    void testMultipleAssertions() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Espresso");
        Recipe recipe2 = new Recipe();
        recipe2.setName("Latte");
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        assertAll("RecipeBook operations",
            () -> assertNotNull(recipeBook.getRecipes()[0]),
            () -> assertEquals("Espresso", recipeBook.getRecipes()[0].getName()),
            () -> assertNotNull(recipeBook.getRecipes()[1]),
            () -> assertEquals("Latte", recipeBook.getRecipes()[1].getName())
        );
    }
}
