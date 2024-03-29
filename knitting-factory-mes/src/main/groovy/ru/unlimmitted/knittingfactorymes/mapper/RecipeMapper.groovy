package ru.unlimmitted.knittingfactorymes.mapper

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.Recipe

import java.sql.ResultSet
import java.sql.SQLException

class RecipeMapper implements RowMapper<Recipe> {


	@Override
	Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		def recipe = new Recipe()
		recipe.id = rs.getLong("id")
		recipe.name = rs.getString("name")
		return recipe
	}
}
