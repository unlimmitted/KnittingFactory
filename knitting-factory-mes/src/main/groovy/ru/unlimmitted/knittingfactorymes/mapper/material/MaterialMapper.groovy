package ru.unlimmitted.knittingfactorymes.mapper.material

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.material.Material
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialType
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialUnit

import java.sql.ResultSet
import java.sql.SQLException

class MaterialMapper  implements RowMapper<Material> {

	@Override
	Material mapRow(ResultSet rs, int rowNum) throws SQLException {
		def material = new Material()
		material.id = rs.getLong("id")
		material.name = rs.getString("name")
		material.type = MaterialType.values().find{it.ordinal() == rs.getLong("type")}
		material.price = rs.getBigDecimal("price")
		material.typeName = material.type.typeName
		material.unit = MaterialUnit.values().find({it.ordinal() == rs.getInt("unit")})
		material.unitName = material.unit.unitName

		return material
	}
}
