package ru.unlimmitted.knittingfactorymes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork
import ru.unlimmitted.knittingfactorymes.repository.MainRepository

import java.math.RoundingMode

@Service
class Scheduler {

	@Autowired
	MainRepository repository

	@Scheduled(cron = "0 * * * * *")
//	@Scheduled(cron = "* * * * * *")
	void calculateWorkingProgress() {
		List<OrderInWork> orders = repository.getOrdersInWork()
		if (orders.size() !== 0) {
			for (order in orders) {
				if (order.done < order.needToDo) {
					(order.done += 1 / orders.size()).setScale(2, RoundingMode.CEILING)
					repository.insertProgressOrderInWork(order)
				} else if (order.done >= order.needToDo) {
					repository.finishOrderWork(order)
				}
			}
		}

	}

}
