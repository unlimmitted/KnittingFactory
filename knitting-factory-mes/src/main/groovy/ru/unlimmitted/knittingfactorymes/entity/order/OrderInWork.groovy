package ru.unlimmitted.knittingfactorymes.entity.order

import jakarta.persistence.*

@Entity
@Table(name = "order_in_work")
class OrderInWork {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	@OneToOne(
			fetch = FetchType.EAGER,
			targetEntity = Order.class)
	Order order

	BigDecimal done

	Long needToDo

	OrderInWork() {

	}

}
