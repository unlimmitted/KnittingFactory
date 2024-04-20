package ru.unlimmitted.knittingfactorymes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.unlimmitted.knittingfactorymes.repository.MainRepository

@Service
class SocketScheduler {

	@Autowired
	MainRepository repository

	@Autowired
	SocketService socketService

	@Scheduled(cron = "* * * * * *")
	void sendScheduleMessageToWebSocket() {
		socketService.sendOrdersProgress(repository.getOrdersInWorkJoin())
		socketService.sendOrdersCollection(repository.getCollectionOrders())
	}
}
