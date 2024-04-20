package ru.unlimmitted.knittingfactorymes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork
import ru.unlimmitted.knittingfactorymes.repository.MainRepository

@Controller
@CrossOrigin
class GreetingController {

	@Autowired
	MainRepository repository

	@MessageMapping("/app/greeting")
	@SendTo("/topic/orders")
	List<OrderInWork> greeting() throws Exception {
		List<OrderInWork> orders = repository.getAllOrdersInWork()
		return orders
	}

}