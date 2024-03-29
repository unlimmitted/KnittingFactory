package ru.unlimmitted.knittingfactorymes.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id


class Material {
	@Id
	Long id

	String name

	MaterialType type

}
