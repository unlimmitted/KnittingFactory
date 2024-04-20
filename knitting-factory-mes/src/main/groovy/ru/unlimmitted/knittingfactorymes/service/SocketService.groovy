package ru.unlimmitted.knittingfactorymes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import ru.unlimmitted.knittingfactorymes.entity.OrdersCollection
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWorkJoinOrder

@Service
@CrossOrigin
class SocketService {

	@Autowired
	private SimpMessagingTemplate simpMessaging;

	void sendOrdersCollection(OrdersCollection message) {
		simpMessaging.convertAndSend("/topic/orders/", message)
	}

	void sendOrdersProgress(List<OrderInWorkJoinOrder> message) {
		simpMessaging.convertAndSend("/topic/ordersInWork/", message)
	}
}
