package ru.unlimmitted.knittingfactorymes.mapper.product

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.product.Product

import java.sql.ResultSet
import java.sql.SQLException

class ProductMapper implements RowMapper<Product> {


	@Override
	Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		def product = new Product()
		product.id = rs.getLong("id")
		product.name = rs.getString("name")
		product.price = rs.getBigDecimal("price")
		product.productionTime = rs.getInt("production_time")

		return product
	}
}
