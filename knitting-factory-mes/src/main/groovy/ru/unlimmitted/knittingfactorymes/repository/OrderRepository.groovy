package ru.unlimmitted.knittingfactorymes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.order.Order

@Repository
interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByIsAcceptedFalseOrderById()

	List<Order> findByIsAcceptedTrueAndIsCompletedFalseAndInWorkFalseOrderById()

	List<Order> findByIsCompletedTrueOrderById()

	List<Order> findByInWorkTrueAndIsCompletedFalseOrderById()
}