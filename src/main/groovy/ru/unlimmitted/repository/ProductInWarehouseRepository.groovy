package ru.unlimmitted.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.entity.product.ProductInWarehouse

@Repository
interface ProductInWarehouseRepository extends JpaRepository<ProductInWarehouse, Long> {

}