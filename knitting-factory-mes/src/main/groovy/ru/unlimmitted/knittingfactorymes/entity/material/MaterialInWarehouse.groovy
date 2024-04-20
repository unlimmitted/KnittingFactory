package ru.unlimmitted.knittingfactorymes.entity.material

class MaterialInWarehouse {
	Long id

	BigDecimal quantity

	Long material_id

	StringBuilder name = new StringBuilder()

	StringBuilder typeName = new StringBuilder()

	BigDecimal price

	StringBuilder unitName = new StringBuilder()

}
