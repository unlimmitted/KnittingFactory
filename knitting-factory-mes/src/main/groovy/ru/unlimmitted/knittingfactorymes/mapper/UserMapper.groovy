package ru.unlimmitted.knittingfactorymes.mapper

import ru.unlimmitted.knittingfactorymes.entity.user.User

import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class UserMapper implements RowMapper<User> {

	@Override
	User mapRow(ResultSet rs, int rowNum) throws SQLException {
		def user = new User()
		user.id = rs.getInt("id")
		user.username = rs.getString("username")
		user.password = rs.getString("password")
		user.role = rs.getString("role")
		return user
	}
}
