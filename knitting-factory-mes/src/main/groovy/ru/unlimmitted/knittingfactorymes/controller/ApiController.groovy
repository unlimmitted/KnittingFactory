package ru.unlimmitted.knittingfactorymes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.unlimmitted.knittingfactorymes.entity.material.Material
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialInWarehouse
import ru.unlimmitted.knittingfactorymes.entity.order.Order
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork
import ru.unlimmitted.knittingfactorymes.entity.recipe.Recipe
import ru.unlimmitted.knittingfactorymes.repository.*

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class ApiController {

	@Autowired
	MaterialRepository materialRepository

	@Autowired
	ProductRepository productRepository

	@Autowired
	OrderRepository orderRepository

	@Autowired
	RecipeRepository recipeRepository

	@Autowired
	MaterialInWarehouseRepository materialInWarehouseRepository

	@Autowired
	ProductInWarehouseRepository productInWarehouseRepository

	@Autowired
	OrderInWorkRepository orderInWorkRepository

	@GetMapping("/get-all-material")
	ResponseEntity<Object> main() {
		return ResponseEntity.ok().body(materialRepository.findAll()
				.each { it.unitName = it.unit.unitName }
				.each { it.typeName = it.type.typeName })
	}

	@GetMapping("/get-all-products")
	ResponseEntity<Object> getAllProducts() {
		return ResponseEntity.ok().body(productRepository.findAll())
	}

	@GetMapping("/get-all-recipe")
	ResponseEntity<Object> getAllRecipe() {
		return ResponseEntity.ok().body(recipeRepository.findAll()
				.each { it.material.each { it.material.unitName = it.material.unit.unitName } }
				.each { it.material.each { it.material.typeName = it.material.type.typeName } })
	}

	@GetMapping("/get-all-orders")
	ResponseEntity<Object> getOrdersCollection() {
		return ResponseEntity.ok().body(orderRepository.findAll())
	}

	@GetMapping("/get-material-in-warehouse")
	ResponseEntity<Object> getMaterial() {
		return ResponseEntity.ok().body(materialInWarehouseRepository.findAll()
				.each { it.material.typeName = it.material.type.typeName }
				.each { it.material.unitName = it.material.unit.unitName })
	}

	@GetMapping("/get-product-in-warehouse")
	ResponseEntity<Object> getProductInWarehouse() {
		return ResponseEntity.ok().body(productInWarehouseRepository.findAll())
	}

	@PostMapping("/create-recipe")
	ResponseEntity<Object> createRecipe(@RequestBody Recipe newRecipe) {
		recipeRepository.save(newRecipe)
		return ResponseEntity.ok().body(recipeRepository.findAll()
				.each { it.material.each { it.material.unitName = it.material.unit.unitName } }
				.each { it.material.each { it.material.typeName = it.material.type.typeName } })
	}

	@PostMapping("/delete-recipe")
	ResponseEntity<Object> deleteRecipe(@RequestBody Recipe recipe) {
		recipeRepository.delete(recipe)
		return ResponseEntity.ok().body(recipeRepository.findAll()
				.each { it.material.each { it.material.unitName = it.material.unit.unitName } }
				.each { it.material.each { it.material.typeName = it.material.type.typeName } })
	}

	@PostMapping("/create-new-order")
	void createNewOrder(@RequestBody Order newOrder) {
		orderRepository.save(newOrder)
	}

	@PostMapping("/make-order-accepted")
	void makeOrderAccepted(@RequestBody Order order) {
		order.isAccepted = true
		orderRepository.save(order)
	}

	@PostMapping("/put-order-to-work")
	void putOrderToWork(@RequestBody Order order) {
		OrderInWork orderToWork = new OrderInWork()
		order.inWork = true
		orderToWork.order = order
		orderToWork.done = 0
		orderToWork.needToDo = order.product.productionTime * order.quantity
		orderRepository.save(order)
		orderInWorkRepository.save(orderToWork)
	}

	@PostMapping("/ordering-material")
	void orderingMaterial(@RequestBody MaterialInWarehouse material) {
		materialInWarehouseRepository.save(material)
	}
}
