package ru.unlimmitted.entity.provider

import jakarta.persistence.*

@Entity
class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	String companyName

	@OneToMany(
			cascade=CascadeType.ALL,
			fetch = FetchType.EAGER,
			targetEntity = ProviderMaterialsPart.class)
	List<ProviderMaterialsPart> material
}
