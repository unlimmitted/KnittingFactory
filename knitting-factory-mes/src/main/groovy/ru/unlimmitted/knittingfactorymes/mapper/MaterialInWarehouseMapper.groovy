package ru.unlimmitted.knittingfactorymes.mapper

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialInWarehouse
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialJoinRecipe

import java.sql.ResultSet
import java.sql.SQLException

class MaterialInWarehouseMapper  implements RowMapper<MaterialInWarehouse> {
	@Override
	MaterialInWarehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
		def materials = new MaterialInWarehouse()
		materials.id = rs.getLong("id")
		materials.quantity = rs.getInt("quantity")
		materials.material_id = rs.getLong("material_id")

		return materials
	}
}
