package ru.unlimmitted.knittingfactorymes.mapper.recipe

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.recipe.Recipe

import java.sql.ResultSet
import java.sql.SQLException

class RecipeMapper implements RowMapper<Recipe> {

	@Override
	Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		def recipe = new Recipe()
		recipe.id = rs.getLong("id")
		recipe.material_id = rs.getLong("material_id")
		recipe.quantity = rs.getInt("quantity")
		recipe.recipe_id = rs.getLong("product_id")
		return recipe
	}
}
