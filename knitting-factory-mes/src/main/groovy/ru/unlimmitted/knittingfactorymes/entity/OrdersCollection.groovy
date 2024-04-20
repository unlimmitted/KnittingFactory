package ru.unlimmitted.knittingfactorymes.entity

import ru.unlimmitted.knittingfactorymes.entity.material.MaterialJoinRecipe
import ru.unlimmitted.knittingfactorymes.entity.order.AcceptedOrder
import ru.unlimmitted.knittingfactorymes.entity.order.CompletedOrders
import ru.unlimmitted.knittingfactorymes.entity.order.Order
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWorkJoinOrder
import ru.unlimmitted.knittingfactorymes.entity.order.OrderToWork

class OrdersCollection {
	List<Order> acceptedOrder = new ArrayList<Order>()
	List<CompletedOrders> completedOrders = new ArrayList<CompletedOrders>()
	List<Order> orders = new ArrayList<Order>()
	List<OrderInWork> orderInWork = new ArrayList<OrderInWork>()
	List<OrderToWork> orderToWork = new ArrayList<OrderToWork>()
	List<List> ordersStats = new ArrayList<>()
}
