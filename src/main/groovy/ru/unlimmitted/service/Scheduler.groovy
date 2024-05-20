package ru.unlimmitted.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.unlimmitted.entity.order.Order
import ru.unlimmitted.entity.order.OrderCollection
import ru.unlimmitted.entity.order.OrderInWork
import ru.unlimmitted.entity.product.ProductInWarehouse
import ru.unlimmitted.repository.OrderInWorkRepository
import ru.unlimmitted.repository.OrderRepository
import ru.unlimmitted.repository.ProductInWarehouseRepository

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

	@Autowired
	ProductInWarehouseRepository productInWarehouseRepository

	@Scheduled(cron = "0 * * * * *")
//	@Scheduled(cron = "* * * * * *")
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
						ProductInWarehouse product = new ProductInWarehouse()
						product.quantity = order.get().quantity
						product.order = order.get()
						product.product = order.get().product
						productInWarehouseRepository.save(product)
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
