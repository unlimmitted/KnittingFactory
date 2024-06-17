package ru.unlimmitted.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.unlimmitted.entity.customer.Customer

interface CustomerRepository extends JpaRepository<Customer, Long> {

}