package ru.unlimmitted.knittingfactorymes.mapper

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.Material
import ru.unlimmitted.knittingfactorymes.entity.MaterialType
import ru.unlimmitted.knittingfactorymes.entity.Recipe

import java.sql.ResultSet
import java.sql.SQLException

class MaterialMapper  implements RowMapper<Material> {

	@Override
	Material mapRow(ResultSet rs, int rowNum) throws SQLException {
		def material = new Material()
		material.id = rs.getLong("id")
		material.name = rs.getString("name")
		material.type = MaterialType.values().find{it.ordinal() == rs.getLong("type")}
		return material
	}
}
