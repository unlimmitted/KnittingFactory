package ru.unlimmitted.entity.customer

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import ru.unlimmitted.entity.order.Order

@Entity
class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	String name

	String phoneNumber

	@OneToOne(fetch = FetchType.EAGER)
	Order orders
}
