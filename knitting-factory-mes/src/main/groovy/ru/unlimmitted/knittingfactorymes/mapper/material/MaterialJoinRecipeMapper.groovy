package ru.unlimmitted.knittingfactorymes.mapper.material

import org.springframework.jdbc.core.RowMapper
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialJoinRecipe
import ru.unlimmitted.knittingfactorymes.entity.material.MaterialType

import java.sql.ResultSet
import java.sql.SQLException

class MaterialJoinRecipeMapper implements RowMapper<MaterialJoinRecipe> {

	@Override
	MaterialJoinRecipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		def materialJoinRecipe = new MaterialJoinRecipe()
		materialJoinRecipe.name = rs.getString("name")
		materialJoinRecipe.quantity = rs.getInt("quantity")
		materialJoinRecipe.type = MaterialType.values().find{it.ordinal() == rs.getLong("type")}
		materialJoinRecipe.typeName = materialJoinRecipe.type.typeName
		return materialJoinRecipe
	}
}
