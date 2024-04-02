package ru.unlimmitted.knittingfactorymes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork
import ru.unlimmitted.knittingfactorymes.repository.MainRepository

@Service
class Scheduler {

	@Autowired
	MainRepository repository

	@Scheduled(cron = "* * * * *")
	void calculateWorkingProgress() {
		List<OrderInWork> orders = repository.getAllOrdersInWork()
		for (order in orders){
			if (order.done > order.needToDo){
				order.done ++
				repository.insertProgressOrderInWork(order)
			} else {
				repository.finishOrderWork(order)
			}
		}

	}

}
