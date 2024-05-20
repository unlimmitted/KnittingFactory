package ru.unlimmitted.entity.material

enum MaterialUnit {

	KILOGRAM("Килограммы"),
	METER("Метры"),
	LITER("Литры")

	String unitName

	MaterialUnit(String unitName) {
		this.unitName = unitName
	}

}