package ru.unlimmitted.knittingfactorymes.entity.recipe

import jakarta.persistence.*
import ru.unlimmitted.knittingfactorymes.entity.product.Product

@Entity
@Table(name = "recipe")
class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	@OneToMany(
			cascade=CascadeType.ALL,
			fetch = FetchType.EAGER,
			targetEntity = RecipePart.class)
	List<RecipePart> material

	@ManyToOne(
			cascade=CascadeType.ALL,
			fetch = FetchType.EAGER,
			targetEntity = Product.class)
	Product products

	Recipe() {

	}
}
