package ru.unlimmitted.knittingfactorymes.mapper.order


import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.order.Order

import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.time.ZoneId

class OrderMapper implements RowMapper<Order> {

	@Override
	Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		def order = new Order()
		order.id = rs.getLong("id")
		order.productId = rs.getLong("product_id")
		order.quantity = rs.getInt("quantity")
		order.deadline = rs.getDate("deadline")
		order.dateOfOrder = rs.getDate("date_of_order")
		return order
	}
}
