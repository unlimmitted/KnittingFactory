package ru.unlimmitted.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.entity.material.MaterialInWarehouse

@Repository
interface MaterialInWarehouseRepository extends JpaRepository<MaterialInWarehouse, Long> {
	MaterialInWarehouse findByMaterialId(Long id)
}