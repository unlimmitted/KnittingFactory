package ru.unlimmitted.knittingfactorymes.repository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.material.Material
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialInWarehouse
import ru.unlimmitted.knittingfactorymes.entity.Product
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialType
import ru.unlimmitted.knittingfactorymes.mapper.MaterialInWarehouseMapper
import ru.unlimmitted.knittingfactorymes.mapper.MaterialJoinRecipeMapper
import ru.unlimmitted.knittingfactorymes.mapper.MaterialMapper
import ru.unlimmitted.knittingfactorymes.mapper.ProductMapper

@Repository
class MainRepository {
	@Autowired
	JdbcTemplate template

	List<Material> getAllMaterials() {
		return template.query("SELECT * from material", new MaterialMapper())
	}

	List<MaterialInWarehouse> getMaterialInWarehouse() {
		List<MaterialInWarehouse> materials = template.query(
				"SELECT * FROM quantity_materials", new MaterialInWarehouseMapper())
		for (material in materials) {
			material.name.append(template.queryForObject(
					"SELECT name FROM material WHERE id = ${material.id}", String.class)
			)
			material.typeName.append(
					(MaterialType.values().find{
						it.ordinal() == (template.queryForObject(
								"SELECT type FROM material WHERE id = ${material.id}", String.class)).toInteger()
					}).typeName
			)
		}
		return materials
	}

	List<Product> getAllRecipes() {
		List<Product> products = template.query("SELECT * FROM product", new ProductMapper())
		for (product in products) {
			product.recipes.addAll(template.query("""
							|SELECT mat.name, rec.quantity , mat.type
							|FROM recipe rec
							|JOIN material mat on mat.id = rec.material_id
							|WHERE product_id = $product.id
							""".stripMargin(), new MaterialJoinRecipeMapper()))
		}
		return products
	}

	void insertRecipe(Product product) {

		template.update("INSERT INTO product (name) values ('$product.name')")
		Long product_id = template.queryForObject("SELECT id FROM product WHERE name = '${product.name}'", Long.class)

		for (material in product.recipes) {
			template.update("""
							|INSERT INTO recipe (material_id, quantity, product_id)
							|VALUES (${material.material_id}, ${material.quantity}, $product_id) 
							""".stripMargin())
		}
	}
}
