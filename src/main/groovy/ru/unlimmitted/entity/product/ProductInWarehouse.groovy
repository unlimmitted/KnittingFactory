package ru.unlimmitted.entity.product

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.Transient
import ru.unlimmitted.entity.order.Order


@Entity
@Table(name = "products_in_warehouse")
class ProductInWarehouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	Integer quantity

	@OneToOne(
			fetch = FetchType.EAGER,
			targetEntity = Order.class
	)
	Order order

	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Product.class)
	Product product

	@Transient
	String name

	@Transient
	BigDecimal price
}