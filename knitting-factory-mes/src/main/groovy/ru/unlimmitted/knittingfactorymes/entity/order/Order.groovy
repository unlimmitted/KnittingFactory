package ru.unlimmitted.knittingfactorymes.entity.order


import java.time.LocalDate

class Order {
	Long id
	StringBuilder name = new StringBuilder()
	Long productId
	Integer quantity
	Date deadline
	Date dateOfOrder
}
