package ru.unlimmitted.knittingfactorymes.entity.material

class MaterialJoinRecipe {

	String name

	Long material_id

	MaterialType type

	BigDecimal quantity = 0.01d

	String typeName

	MaterialUnit unit

	String unitName
}
