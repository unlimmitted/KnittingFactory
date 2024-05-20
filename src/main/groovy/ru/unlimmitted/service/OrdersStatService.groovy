package ru.unlimmitted.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.unlimmitted.entity.order.Order
import ru.unlimmitted.repository.OrderRepository

@Service
class OrdersStatService {

	@Autowired
	OrderRepository orderRepository

	List<List> getOrdersStat() {
		return [getOrdersSumPrice(), getCountOrders()]
	}

	List getOrdersSumPrice() {
		List<Order> completedOrders = orderRepository.findByIsCompletedTrueOrderById()
		List monthlyTotals = []

		def monthlyTotalsMap = [:]

		completedOrders.each { order ->
			Integer month = order.dateOfOrder.month
			BigDecimal total = monthlyTotalsMap.getOrDefault(month, BigDecimal.ZERO) as BigDecimal
			total += (order.quantity * order.product.price)
			monthlyTotalsMap[month] = total
		}

		for (int i = 0; i < 12; i++) {
			BigDecimal total = monthlyTotalsMap.getOrDefault(i, BigDecimal.ZERO) as BigDecimal
			monthlyTotals << total
		}

		return monthlyTotals
	}

	List getCountOrders() {
		List<Order> completedOrders = orderRepository.findByIsCompletedTrueOrderById()
		List monthlyCounts = []

		def monthlyCountsMap = [:]

		completedOrders.each { order ->
			Integer month = order.dateOfOrder.month
			Integer count = monthlyCountsMap.getOrDefault(month, 0) as Integer
			count++
			monthlyCountsMap[month] = count
		}

		for (int i = 0; i < 12; i++) {
			Integer count = monthlyCountsMap.getOrDefault(i, 0) as Integer
			monthlyCounts << count
		}

		return monthlyCounts
	}
}
