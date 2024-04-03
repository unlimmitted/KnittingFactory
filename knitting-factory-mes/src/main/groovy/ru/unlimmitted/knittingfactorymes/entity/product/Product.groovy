package ru.unlimmitted.knittingfactorymes.entity.product

import jakarta.persistence.Id
import lombok.Builder
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialJoinRecipe

@Builder
class Product {
	@Id
	Long id

	String name

	List<MaterialJoinRecipe> recipes = new ArrayList<MaterialJoinRecipe>()

	BigDecimal price = 0.01d

	Integer productionTime

	@Override
	public String toString() {
		return "Product{id=$id, name='$name', recipes=$recipes}'"
	}
}
