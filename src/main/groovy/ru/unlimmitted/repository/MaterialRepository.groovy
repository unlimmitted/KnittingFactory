package ru.unlimmitted.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.entity.material.Material

@Repository
interface MaterialRepository extends JpaRepository<Material, Long> {

}