package ru.unlimmitted.entity.provider

import jakarta.persistence.*
import ru.unlimmitted.entity.material.Material

@Entity
class ProviderMaterialsPart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Material.class)
	Material material
}
