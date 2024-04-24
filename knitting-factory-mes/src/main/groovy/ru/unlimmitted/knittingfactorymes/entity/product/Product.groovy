package ru.unlimmitted.knittingfactorymes.entity.product

import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	String name

	BigDecimal price = 0.01d

	Integer productionTime

	Product() {

	}
}
