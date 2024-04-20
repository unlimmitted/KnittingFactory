package ru.unlimmitted.knittingfactorymes.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.OrdersCollection
import ru.unlimmitted.knittingfactorymes.entity.material.Material
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialInWarehouse
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialType
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialUnit
import ru.unlimmitted.knittingfactorymes.entity.order.*
import ru.unlimmitted.knittingfactorymes.entity.product.Product
import ru.unlimmitted.knittingfactorymes.entity.product.ProductInWarehouse
import ru.unlimmitted.knittingfactorymes.mapper.material.MaterialInWarehouseMapper
import ru.unlimmitted.knittingfactorymes.mapper.material.MaterialJoinRecipeMapper
import ru.unlimmitted.knittingfactorymes.mapper.material.MaterialMapper
import ru.unlimmitted.knittingfactorymes.mapper.order.CompletedOrdersMapper
import ru.unlimmitted.knittingfactorymes.mapper.order.OrderInWorkJoinOrderMapper
import ru.unlimmitted.knittingfactorymes.mapper.order.OrderInWorkMapper
import ru.unlimmitted.knittingfactorymes.mapper.order.OrderMapper
import ru.unlimmitted.knittingfactorymes.mapper.product.ProductInWarehouseMapper
import ru.unlimmitted.knittingfactorymes.mapper.product.ProductMapper

import java.math.RoundingMode
import java.sql.Date

@Repository
class MainRepository {
	@Autowired
	JdbcTemplate template

	List<Material> getAllMaterials() {
		return template.query("SELECT * from material", new MaterialMapper())
	}

	OrdersCollection getCollectionOrders() {
		OrdersCollection orders = new OrdersCollection()
		orders.acceptedOrder.addAll(getAcceptedOrders())
		orders.completedOrders.addAll(getCompletedOrders())
		orders.orders.addAll(getAllOrders())
		orders.orderInWork.addAll(getAllOrdersInWork())
		orders.orderToWork.addAll()
		orders.ordersStats.addAll(getOrdersStat())
		return orders
	}

	List<MaterialInWarehouse> getMaterialInWarehouse() {
		List<MaterialInWarehouse> materials = template.query(
				"SELECT * FROM materials_in_warehouse", new MaterialInWarehouseMapper())
		for (material in materials) {
			material.name.append(template.queryForObject(
					"SELECT name FROM material WHERE id = ${material.material_id}", String.class))
			material.typeName.append(
					(MaterialType.values().find {
						it.ordinal() == (template.queryForObject(
								"SELECT type FROM material WHERE id = ${material.material_id}", Integer.class))
					}).typeName
			)
			material.unitName.append(
					(MaterialUnit.values().find {
						it.ordinal() == (template.queryForObject(
								"SELECT unit FROM material WHERE id = ${material.material_id}", Integer.class)
						)
					}).unitName
			)
			material.price = material.quantity * template.queryForObject(
					"SELECT price FROM material WHERE id = ${material.material_id}", BigDecimal.class)
		}
		return materials
	}

	List<Product> getAllRecipes() {
		List<Product> products = getAllProducts()
		for (product in products) {
			product.recipes.addAll(template.query("""
							|SELECT mat.name, mat.id as "material_id", rec.quantity, mat.type, mat.unit
							|FROM recipe rec
							|JOIN material mat on mat.id = rec.material_id
							|WHERE product_id = $product.id
							""".stripMargin(), new MaterialJoinRecipeMapper()))
			product.price = template.queryForObject(
					"SELECT price FROM product WHERE id = ${product.id}",
					BigDecimal.class).setScale(2, RoundingMode.CEILING)
		}
		return products
	}

	List<Order> getAllOrders() {
		List<Order> orders = template.query("""
							SELECT * FROM orders
							WHERE id not in (SELECT order_id FROM accepted_orders)
							""".stripMargin(), new OrderMapper())
		for (order in orders) {
			order.name.append(template.queryForObject(
					"SELECT name from product WHERE id = ${order.productId}", String.class))
		}
		return orders
	}

	List<Product> getAllProducts() {
		List<Product> product = template.query("SELECT * FROM product", new ProductMapper())
		return product
	}

	List<Order> getAcceptedOrders() {
		List<Order> orders = template.query("""
							SELECT * FROM orders
							WHERE id in (SELECT order_id FROM accepted_orders) and in_work = FALSE
							""".stripMargin(), new OrderMapper())
		for (order in orders) {
			order.name.append(template.queryForObject(
					"SELECT name from product WHERE id = ${order.productId}", String.class))
		}
		return orders
	}

	List<OrderInWork> getAllOrdersInWork() {
		List<OrderInWork> orderInWork = template.query(
				"SELECT id, order_id, done, need_to_do FROM orders_in_work",
				new OrderInWorkMapper())
		return orderInWork
	}

	List<OrderInWork> getOrdersInWork() {
		List<OrderInWork> orderInWork = template.query(
				"SELECT id, order_id, done, need_to_do FROM orders_in_work ORDER BY priority DESC LIMIT 10",
				new OrderInWorkMapper())
		return orderInWork
	}

	List<CompletedOrders> getCompletedOrders() {
		String query = """
					SELECT ord.id, prd.name, ord.quantity ,ord.deadline, ord.date_of_order, prd.price
					FROM orders ord
					JOIN product prd on ord.product_id = prd.id
					JOIN completed_orders on ord.id = completed_orders.order_id
					WHERE ord.id = completed_orders.order_id
					"""
		List<CompletedOrders> orders = template.query(query, new CompletedOrdersMapper())
		for (order in orders) {
			order.price = order.quantity * order.productPrice
		}
		return orders
	}

	List<List> getOrdersStat() {
		return [getOrdersSumPrice(), getCountOrders()]
	}

	List getOrdersSumPrice() {
		List<CompletedOrders> orders = getCompletedOrders()
		List monthlyTotals = []

		def monthlyTotalsMap = [:]

		orders.each { order ->
			Integer month = order.dateOfOrder.month
			BigDecimal total = monthlyTotalsMap.getOrDefault(month, BigDecimal.ZERO) as BigDecimal
			total += order.price
			monthlyTotalsMap[month] = total
		}

		for (int i = 0; i < 12; i++) {
			BigDecimal total = monthlyTotalsMap.getOrDefault(i, BigDecimal.ZERO) as BigDecimal
			monthlyTotals << total
		}

		return monthlyTotals
	}

	List getCountOrders() {
		List<CompletedOrders> orders = getCompletedOrders()
		List monthlyCounts = []

		def monthlyCountsMap = [:]

		orders.each { order ->
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

	List<ProductInWarehouse> getProductsInWarehouse() {
		List<ProductInWarehouse> productInWarehouse = template.query("""
							SELECT piw.product_id, prd.name, piw.quantity, piw.order_id
							FROM products_in_warehouse piw
							JOIN product prd on piw.product_id = prd.id
							""", new ProductInWarehouseMapper())
		for (product in productInWarehouse) {
			product.price = product.quantity * (template.queryForObject(
					"SELECT price from product WHERE id = ${product.productId}", BigDecimal.class))
					.setScale(2, RoundingMode.CEILING)
		}
		return productInWarehouse
	}

	void finishOrderWork(OrderInWork order) {
		template.update("DELETE FROM orders_in_work WHERE id = ${order.id}")
		ProductInWarehouse productInWarehouse = template.queryForObject("""
							|SELECT ord.id as "order_id", ord.product_id, ord.quantity as "quantity", prd.name
							|FROM orders ord
							|JOIN product prd on prd.id = ord.product_id
							|WHERE ord.id = ${order.orderId}
							""".stripMargin(), new ProductInWarehouseMapper())
		template.update(
				"INSERT INTO products_in_warehouse (quantity, product_id, order_id) VALUES (?, ?, ?)",
				productInWarehouse.quantity, productInWarehouse.productId, productInWarehouse.orderId
		)
		template.update("INSERT INTO completed_orders (order_id) VALUES (?)",
				(order.orderId))

	}

	void insertProgressOrderInWork(OrderInWork order) {
		template.update("UPDATE orders_in_work SET done = ${order.done} WHERE id = ${order.id}")
	}

	void insertOrderToWork(OrderToWork orderToWork) {
		Order order = template.queryForObject(
				"SELECT * FROM orders WHERE id = ${orderToWork.orderId}", new OrderMapper())

		def productionTime = template.queryForObject(
				"SELECT production_time FROM product WHERE id = ${order.productId}", Integer.class)

		Date deadlineDate = new Date(order.deadline.time)
		def priority = calculatePriority(deadlineDate, order.quantity, productionTime)
		template.update("UPDATE orders SET in_work = TRUE WHERE id = ${orderToWork.orderId}")
		template.update("""
						|INSERT INTO orders_in_work (order_id, priority, need_to_do) 
						|VALUES (${orderToWork.orderId}, ${priority}, ${productionTime * order.quantity})
						""".stripMargin())
	}

	List<OrderInWorkJoinOrder> getOrdersInWorkJoin() {
		List<OrderInWorkJoinOrder> orders = template.query("""
						SELECT ord.id,ord.quantity, ord.product_id, prd.name, oiw.done, ord.deadline, oiw.need_to_do
   						FROM  orders ord
    					JOIN orders_in_work oiw on ord.id = oiw.order_id
    					JOIN product prd on prd.id = ord.product_id
    					WHERE ord.in_work = TRUE
    					ORDER BY oiw.priority
						""".stripMargin(), new OrderInWorkJoinOrderMapper())
		for (order in orders) {
			order.done = (
					template.queryForObject(
							"SELECT done FROM orders_in_work WHERE order_id = ${order.id}",
							BigDecimal.class) * 100 / template.queryForObject(
							"SELECT need_to_do FROM orders_in_work WHERE order_id = ${order.id}",
							BigDecimal.class)
			).setScale(2, RoundingMode.CEILING)
		}
		return orders
	}


	static Integer calculatePriority(Date deadline, Integer quantity, Integer productionTime) {
		Long remainingTime = deadline.time - new java.util.Date().time
		Integer totalTimeNeeded = quantity * productionTime
		Double ratio = (double) remainingTime / totalTimeNeeded
		Integer priority = Math.max(1, Math.min(5, ((1 - ratio) * 5).toInteger()))
		return priority
	}

	void insertNewOrder(Order newOrder) {
		template.update("""
							|INSERT INTO orders (product_id, quantity, deadline, date_of_order)
							|VALUES ($newOrder.productId, $newOrder.quantity, ?, ?)
							""".stripMargin(),
				newOrder.deadline,
				newOrder.dateOfOrder
		)
	}

	void makeOrderAccepted(AcceptedOrder order) {
		template.update("INSERT INTO accepted_orders (order_id) values ($order.orderId)")
	}

	void insertRecipe(Product product) {

		template.update("INSERT INTO product (name, production_time) values ('$product.name', ${product.productionTime})")
		Long product_id = template.queryForObject("SELECT id FROM product WHERE name = '${product.name}'", Long.class)

		for (material in product.recipes) {
			template.update("""
							|INSERT INTO recipe (material_id, quantity, product_id)
							|VALUES (${material.material_id}, ${material.quantity}, $product_id) 
							""".stripMargin())
		}
		BigDecimal price = 0
		for (recipe in product.recipes) {
			price += (recipe.quantity * template.queryForObject(
					"SELECT price FROM material WHERE id = ${recipe.material_id}",
					BigDecimal.class))
		}
		template.update("""
							|UPDATE product 
							|SET price = ${price.setScale(2, RoundingMode.CEILING)}
							|WHERE id = ${product_id}
							""".stripMargin())
	}
}
