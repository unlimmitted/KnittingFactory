package ru.unlimmitted.knittingfactorymes.mapper.material

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialJoinRecipe
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialType
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialUnit

import java.sql.ResultSet
import java.sql.SQLException

class MaterialJoinRecipeMapper implements RowMapper<MaterialJoinRecipe> {

	@Override
	MaterialJoinRecipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		def materialJoinRecipe = new MaterialJoinRecipe()
		materialJoinRecipe.name = rs.getString("name")
		materialJoinRecipe.material_id = rs.getLong("material_id")
		materialJoinRecipe.quantity = rs.getBigDecimal("quantity")
		materialJoinRecipe.type = MaterialType.values().find{it.ordinal() == rs.getLong("type")}
		materialJoinRecipe.typeName = materialJoinRecipe.type.typeName
		materialJoinRecipe.unit = MaterialUnit.values().find{it.ordinal() == rs.getLong("unit")}
		materialJoinRecipe.unitName = materialJoinRecipe.unit.unitName
		return materialJoinRecipe
	}
}
