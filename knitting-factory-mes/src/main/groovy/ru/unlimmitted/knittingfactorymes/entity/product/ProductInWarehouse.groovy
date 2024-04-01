package ru.unlimmitted.knittingfactorymes.entity.product


class ProductInWarehouse {
	Long id
	Integer quantity
	Long productId
	StringBuilder name = new StringBuilder()
	BigDecimal price = new BigDecimal()
}