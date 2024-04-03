package ru.unlimmitted.knittingfactorymes.mapper.order

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.order.OrderInWork

import java.sql.ResultSet
import java.sql.SQLException

class OrderInWorkMapper implements RowMapper<OrderInWork> {
	@Override
	OrderInWork mapRow(ResultSet rs, int rowNum) throws SQLException {
		def orderInWork = new OrderInWork()
		orderInWork.id = rs.getLong("id")
		orderInWork.orderId = rs.getLong("order_id")
		orderInWork.done = rs.getBigDecimal("done")
		orderInWork.needToDo = rs.getLong("need_to_do")
		return orderInWork
	}
}
