package ru.unlimmitted.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.unlimmitted.entity.provider.Provider

interface ProviderRepository extends JpaRepository<Provider, Long> {

}