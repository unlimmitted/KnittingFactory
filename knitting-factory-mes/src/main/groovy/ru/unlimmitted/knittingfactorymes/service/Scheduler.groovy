package ru.unlimmitted.knittingfactorymes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.unlimmitted.knittingfactorymes.entity.order.Order
import ru.unlimmitted.knittingfactorymes.entity.order.OrderCollection
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork
import ru.unlimmitted.knittingfactorymes.repository.OrderInWorkRepository
import ru.unlimmitted.knittingfactorymes.repository.OrderRepository

import java.math.RoundingMode

@Service
class Scheduler {

	@Autowired
	OrderInWorkRepository orderInWorkRepository

	@Autowired
	OrderRepository orderRepository

	@Autowired
	SocketService socketService

	@Autowired
	OrdersStatService ordersStatService

	@Scheduled(cron = "0 * * * * *")
	void calculateWorkingProgress() {
		List<OrderInWork> ordersInWork = orderInWorkRepository.getPriorityOrders()
		if (ordersInWork.size() !== 0) {
			for (orderInWork in ordersInWork) {
				if (orderInWork.done < orderInWork.needToDo) {
					(orderInWork.done += 1 / ordersInWork.size()).setScale(2, RoundingMode.CEILING)
					orderInWorkRepository.save(orderInWork)
				} else if (orderInWork.done >= orderInWork.needToDo) {
					orderInWorkRepository.delete(orderInWork)
					Optional<Order> order = orderRepository.findById(orderInWork.order.id)
					order.ifPresent {
						order.get().isCompleted = true
						order.get().inWork = false
						orderRepository.save(order.get())
					}
				}
			}
		}
	}

	@Scheduled(cron = "* * * * * *")
	void sendScheduleMessageToWebSocket() {
		OrderCollection orderCollection = new OrderCollection()
		orderCollection.newOrders = orderRepository.findByIsAcceptedFalseOrderById()
		orderCollection.accepted = orderRepository.findByIsAcceptedTrueAndIsCompletedFalseAndInWorkFalseOrderById()
		orderCollection.completed = orderRepository.findByIsCompletedTrueOrderById()
		orderCollection.inWork = orderRepository.findByInWorkTrueAndIsCompletedFalseOrderById()
		orderCollection.stats = ordersStatService.getOrdersStat()

		socketService.sendOrdersCollection(orderCollection)
		socketService.sendOrdersProgress(orderInWorkRepository.getPriorityOrders())
	}

}
