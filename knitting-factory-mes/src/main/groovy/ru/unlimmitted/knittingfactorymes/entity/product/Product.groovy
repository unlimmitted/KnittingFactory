package ru.unlimmitted.knittingfactorymes.entity.product


import jakarta.persistence.Id
import lombok.Builder
import ru.unlimmitted.knittingfactorymes.entity.recipe.Recipe

@Builder
class Product {
	@Id
	Long id

	String name

	List<Recipe> recipes = new ArrayList<>()

	Double price = 0.01d

	@Override
	public String toString() {
		return "Product{id=$id, name='$name', recipes=$recipes}'"
	}
}
