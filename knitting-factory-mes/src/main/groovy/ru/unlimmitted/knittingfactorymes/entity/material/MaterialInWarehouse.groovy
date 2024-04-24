package ru.unlimmitted.knittingfactorymes.entity.material

import jakarta.persistence.*

@Entity
@Table(name = "material_in_warehouse")
class MaterialInWarehouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	BigDecimal quantity

	@OneToOne(
			fetch = FetchType.EAGER,
			targetEntity = Material.class
	)
	Material material

//	@Transient
//	String name
//
//	@Transient
//	String typeName
//
//	@Transient
//	BigDecimal price
//
//	@Transient
//	String unitName

	MaterialInWarehouse() {

	}

}
