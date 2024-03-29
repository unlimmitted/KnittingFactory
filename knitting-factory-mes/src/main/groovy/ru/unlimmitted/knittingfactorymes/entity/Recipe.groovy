package ru.unlimmitted.knittingfactorymes.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import lombok.Builder

@Builder
class Recipe {
	@Id
	Long id

	String name

	List<Material> material
}
