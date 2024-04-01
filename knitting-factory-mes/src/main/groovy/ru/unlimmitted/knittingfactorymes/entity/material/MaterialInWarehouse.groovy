package ru.unlimmitted.knittingfactorymes.entity.material

class MaterialInWarehouse {
	Long id

	BigDecimal quantity = new BigDecimal()

	Long material_id

	StringBuilder name = new StringBuilder()

	StringBuilder typeName = new StringBuilder()

	BigDecimal price = new BigDecimal()

	StringBuilder unitName = new StringBuilder()

}
