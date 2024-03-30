package ru.unlimmitted.knittingfactorymes.entity.material


import jakarta.persistence.Id


class Material {
	@Id
	Long id

	String name

	MaterialType type

	String typeName

}
