package ru.unlimmitted.knittingfactorymes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.knittingfactorymes.entity.product.Product

@Repository
interface ProductRepository extends JpaRepository<Product, Long>{

}