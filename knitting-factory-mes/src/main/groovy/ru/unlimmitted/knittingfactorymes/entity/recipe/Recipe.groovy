package ru.unlimmitted.knittingfactorymes.entity.recipe

import jakarta.persistence.Id

class Recipe {
	@Id
	Long id

	Long material_id

	BigDecimal quantity = new BigDecimal()

	Long recipe_id

	@Override
	public String toString() {
		return "Recipe{id=$id, material_id=$material_id, quantity=$quantity, recipe_id=$recipe_id'}'"
	}
}
