package ru.unlimmitted.entity.order

import jakarta.persistence.*
import ru.unlimmitted.entity.product.Product

@Entity
@Table(name = "t_order")
class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	@PrimaryKeyJoinColumn
	@ManyToOne(
			cascade=CascadeType.MERGE,
			fetch = FetchType.EAGER,
			targetEntity = Product.class)
	Product product

	Integer quantity

	Date deadline

	Date dateOfOrder

	@Column(name = "in_work", nullable = false, columnDefinition = "boolean default false")
	Boolean inWork

	@Column(name = "is_accepted", nullable = false, columnDefinition = "boolean default false")
	Boolean isAccepted

	@Column(name = "is_completed", nullable = false, columnDefinition = "boolean default false")
	Boolean isCompleted

	Order() {

	}
}
