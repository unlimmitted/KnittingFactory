package ru.unlimmitted.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import ru.unlimmitted.entity.order.Order
import ru.unlimmitted.entity.order.OrderCollection
import ru.unlimmitted.entity.order.OrderInWork
import ru.unlimmitted.repository.OrderRepository

@Service
@CrossOrigin
class SocketService {

	@Autowired
	private SimpMessagingTemplate simpMessaging

	void sendOrdersProgress(List<OrderInWork> message) {
		simpMessaging.convertAndSend("/topic/ordersInWork/", message)
	}

	void sendOrdersCollection(OrderCollection message) {
		simpMessaging.convertAndSend("/topic/orders/", message)
	}


}
