package ru.unlimmitted.knittingfactorymes.repository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.Material
import ru.unlimmitted.knittingfactorymes.entity.Recipe
import ru.unlimmitted.knittingfactorymes.mapper.MaterialMapper
import ru.unlimmitted.knittingfactorymes.mapper.RecipeMapper

@Repository
class MainRepository {
	@Autowired
	JdbcTemplate template

	List<Material> getAllMaterials(){
		return template.query("SELECT * from material", new MaterialMapper())
	}

//	List<Material> getMaterial() {
//		return template.query("SELECT * FROM ", new MaterialMapper())
//	}

	void insertRecipe(Recipe recipe){
		template.update("INSERT INTO recipe (name) values ($recipe.name)")
		Long recipe_id = template.queryForObject("SELECT id FROM recipe WHERE name = ${recipe.name}", Long.class)
		template.update("""
						|INSERT INTO material_for_recipe (material_id, quantity, recipe_id)
						|values ($recipe.recipe) 
						""".stripMargin())
	}
}
