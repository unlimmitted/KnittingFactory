package ru.unlimmitted.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.unlimmitted.entity.user.User

@Repository
interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username)
}
