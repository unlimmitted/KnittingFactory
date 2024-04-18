package ru.unlimmitted.knittingfactorymes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.unlimmitted.knittingfactorymes.entity.order.AcceptedOrder
import ru.unlimmitted.knittingfactorymes.entity.order.Order
import ru.unlimmitted.knittingfactorymes.entity.order.OrderToWork
import ru.unlimmitted.knittingfactorymes.entity.product.Product
import ru.unlimmitted.knittingfactorymes.repository.MainRepository

@RestController
@CrossOrigin
class MainController {
	@Autowired
	MainRepository repository

	@GetMapping("/")
	ResponseEntity<Object> main() {
		return ResponseEntity.ok().body(repository.getAllMaterials())
	}
	@GetMapping("/orders")
	ResponseEntity<Object> getOrdersCollection () {
		return ResponseEntity.ok().body(repository.getCollectionOrders())
	}

	@GetMapping("/get-orders-stat")
	ResponseEntity<Object> getOrdersStat() {
		return ResponseEntity.ok().body(repository.getOrdersStat())
	}

	@GetMapping("/get-product-in-warehouse")
	ResponseEntity<Object> getProductInWarehouse() {
		return ResponseEntity.ok().body(repository.getProductsInWarehouse())
	}

	@GetMapping("/get-material")
	ResponseEntity<Object> getMaterial() {
		return ResponseEntity.ok().body(repository.getMaterialInWarehouse())
	}

	@GetMapping("/get-all-recipe")
	ResponseEntity<Object> getAllRecipe() {
		return ResponseEntity.ok().body(repository.getAllRecipes())
	}

	@GetMapping("/get-all-products")
	ResponseEntity<Object> getAllProducts() {
		return ResponseEntity.ok().body(repository.getAllProducts())
	}

	@PostMapping("/create-recipe")
	ResponseEntity<Object> createRecipe(@RequestBody Product newRecipe) {
		repository.insertRecipe(newRecipe)
		return ResponseEntity.ok().body(repository.getAllRecipes())
	}

	@PostMapping("/put-order-to-work")
	ResponseEntity<Object> putOrderToWork(@RequestBody OrderToWork orderToWork) {
		repository.insertOrderToWork(orderToWork)
		return ResponseEntity.ok().body([repository.getAcceptedOrders(), repository.getOrdersInWorkJoin()])
	}

	@PostMapping("/create-new-order")
	ResponseEntity<Object> createNewOrder(@RequestBody Order newOrder) {
		repository.insertNewOrder(newOrder)
		return ResponseEntity.ok().body(repository.getAllOrders())
	}

	@PostMapping("/make-order-accepted")
	ResponseEntity<Object> makeOrderAccepted(@RequestBody AcceptedOrder order) {
		repository.makeOrderAccepted(order)
		return ResponseEntity.ok().body([repository.getAllOrders(), repository.getAcceptedOrders()])
	}
}
