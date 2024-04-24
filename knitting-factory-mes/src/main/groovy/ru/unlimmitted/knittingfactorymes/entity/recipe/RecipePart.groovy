package ru.unlimmitted.knittingfactorymes.entity.recipe

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import ru.unlimmitted.knittingfactorymes.entity.material.Material

@Entity
class RecipePart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Material.class)
	Material material

	BigDecimal quantity

}
