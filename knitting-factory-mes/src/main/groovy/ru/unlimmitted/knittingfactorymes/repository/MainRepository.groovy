package ru.unlimmitted.knittingfactorymes.repository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.material.Material
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialInWarehouse
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialUnit
import ru.unlimmitted.knittingfactorymes.entity.product.Product
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialType
import ru.unlimmitted.knittingfactorymes.entity.product.ProductInWarehouse
import ru.unlimmitted.knittingfactorymes.mapper.material.MaterialInWarehouseMapper
import ru.unlimmitted.knittingfactorymes.mapper.material.MaterialJoinRecipeMapper
import ru.unlimmitted.knittingfactorymes.mapper.material.MaterialMapper
import ru.unlimmitted.knittingfactorymes.mapper.product.ProductInWarehouseMapper
import ru.unlimmitted.knittingfactorymes.mapper.product.ProductMapper

import java.math.RoundingMode

@Repository
class MainRepository {
	@Autowired
	JdbcTemplate template

	List<Material> getAllMaterials() {
		return template.query("SELECT * from material", new MaterialMapper())
	}

	List<MaterialInWarehouse> getMaterialInWarehouse() {
		List<MaterialInWarehouse> materials = template.query(
				"SELECT * FROM materials_in_warehouse", new MaterialInWarehouseMapper())
		for (material in materials) {
			material.name.append(template.queryForObject(
					"SELECT name FROM material WHERE id = ${material.id}", String.class)
			)
			material.typeName.append(
					(MaterialType.values().find {
						it.ordinal() == (template.queryForObject(
								"SELECT type FROM material WHERE id = ${material.id}", Integer.class))
					}).typeName
			)
			material.unitName.append(
					(MaterialUnit.values().find {
						it.ordinal() == (template.queryForObject(
								"SELECT unit FROM material WHERE id ${material.id}", Integer.class)
						)
					}).unitName
			)
			material.price = material.quantity * template.queryForObject(
					"SELECT price FROM material WHERE id = ${material.id}", BigDecimal.class)
		}
		return materials
	}

	List<ProductInWarehouse> getProductInWarehouse() {
		List<ProductInWarehouse> products = template.query(
				"SELECT * FROM products_in_warehouse WHERE quantity != 0",
				new ProductInWarehouseMapper())
		for (product in products) {
			product.name.append(
					template.queryForObject(
							"SELECT name FROM product WHERE id = ${product.productId}", String.class)
			)
			product.price = template.queryForObject(
					"SELECT price FROM product WHERE id = ${product.productId}",
					BigDecimal.class)
		}
		return products
	}

	List<Product> getAllRecipes() {
		List<Product> products = template.query("SELECT * FROM product", new ProductMapper())
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

	void insertRecipe(Product product) {

		template.update("INSERT INTO product (name) values ('$product.name')")
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
