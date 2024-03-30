package ru.unlimmitted.knittingfactorymes.entity


import jakarta.persistence.Id
import lombok.Builder

@Builder
class Product {
	@Id
	Long id

	String name

	List<Recipe> recipes = new ArrayList<>()

	@Override
	public String toString() {
		return "Product{id=$id, name='$name', recipes=$recipes}'"
	}
}
