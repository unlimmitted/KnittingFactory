package ru.unlimmitted.knittingfactorymes.mapper.order

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.order.CompletedOrders
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWorkJoinOrder

import java.sql.ResultSet
import java.sql.SQLException


class CompletedOrdersMapper implements RowMapper<CompletedOrders> {
	@Override
	CompletedOrders mapRow(ResultSet rs, int rowNum) throws SQLException {
		def order = new CompletedOrders()
		order.orderId = rs.getLong("id")
		order.quantity = rs.getInt("quantity")
		order.productPrice = rs.getBigDecimal("price")
		order.productName = rs.getString("name")
		order.dateOfOrder = rs.getDate("date_of_order")
		return order
	}
}
