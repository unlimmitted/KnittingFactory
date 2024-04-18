package ru.unlimmitted.knittingfactorymes.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.unlimmitted.knittingfactorymes.entity.user.User

interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
