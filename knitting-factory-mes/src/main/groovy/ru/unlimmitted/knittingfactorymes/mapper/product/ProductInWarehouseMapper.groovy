package ru.unlimmitted.knittingfactorymes.mapper.product

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.product.ProductInWarehouse

import java.sql.ResultSet
import java.sql.SQLException

class ProductInWarehouseMapper  implements RowMapper<ProductInWarehouse> {
	@Override
	ProductInWarehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
		def product = new ProductInWarehouse()
		product.id = rs.getLong("id")
		product.quantity = rs.getInt("quantity")
		product.productId = rs.getLong("product_id")
		return product
	}
}
