package ru.unlimmitted.knittingfactorymes.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.unlimmitted.knittingfactorymes.entity.Product
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

	@GetMapping("/get-material")
	ResponseEntity<Object> getMaterial(){
		return ResponseEntity.ok().body(repository.getMaterialInWarehouse())
	}

	@PostMapping("/create-recipe")
	ResponseEntity<Object> createRecipe(@RequestBody Product newRecipe){
		repository.insertRecipe(newRecipe)
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
	}

	@GetMapping("/get-all-recipe")
	ResponseEntity<Object> getAllRecipe(){
		repository.getAllRecipes()
		return ResponseEntity.ok().body(repository.getAllRecipes())
	}
}
