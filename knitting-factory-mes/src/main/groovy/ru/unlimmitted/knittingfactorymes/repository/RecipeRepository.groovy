package ru.unlimmitted.knittingfactorymes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.recipe.Recipe

@Repository
interface RecipeRepository extends JpaRepository<Recipe, Long>{

	Recipe findByProductsId(Long id)

}