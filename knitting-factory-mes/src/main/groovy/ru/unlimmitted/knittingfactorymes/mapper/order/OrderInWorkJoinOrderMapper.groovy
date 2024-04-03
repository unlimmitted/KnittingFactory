package ru.unlimmitted.knittingfactorymes.mapper.order

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWorkJoinOrder

import java.sql.ResultSet
import java.sql.SQLException

class OrderInWorkJoinOrderMapper implements RowMapper<OrderInWorkJoinOrder> {

	@Override
	OrderInWorkJoinOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		def order = new OrderInWorkJoinOrder()
		order.id = rs.getLong("id")
		order.quantity = rs.getInt("quantity")
		order.productName = rs.getString("name")
		order.deadline = rs.getDate("deadline")
		order.needToDo = rs.getInt("need_to_do")
		return order
	}
}
