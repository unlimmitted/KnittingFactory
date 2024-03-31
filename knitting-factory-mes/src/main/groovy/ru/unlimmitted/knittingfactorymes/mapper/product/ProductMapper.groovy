package ru.unlimmitted.knittingfactorymes.mapper.product

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.product.Product

import java.sql.ResultSet
import java.sql.SQLException

class ProductMapper implements RowMapper<Product> {


	@Override
	Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		def recipe = new Product()
		recipe.id = rs.getLong("id")
		recipe.name = rs.getString("name")

		return recipe
	}
}
