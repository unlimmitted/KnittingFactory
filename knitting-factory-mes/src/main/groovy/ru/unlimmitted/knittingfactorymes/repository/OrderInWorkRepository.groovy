package ru.unlimmitted.knittingfactorymes.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork

@Repository
interface OrderInWorkRepository extends JpaRepository<OrderInWork, Long> {

	@Query(value = "SELECT * FROM order_in_work ORDER BY id LIMIT 10", nativeQuery = true)
	List<OrderInWork> getPriorityOrders()
}