package abhinay.firstapp.project;

public class Recipe {
    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    String recipe;
    String ingredients;
    String instructions;


    public Recipe(){

    }
    public Recipe(String recipe, String ingredients, String instructions){
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}
