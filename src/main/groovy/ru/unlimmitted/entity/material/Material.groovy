package ru.unlimmitted.entity.material

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient

@Entity
@Table(name = "material")
class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	String name

	MaterialType type

	BigDecimal price

	MaterialUnit unit

	@Transient
	String typeName

	@Transient
	String unitName

	Material() {

	}

}
