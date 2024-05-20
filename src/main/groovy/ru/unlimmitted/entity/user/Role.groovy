package ru.unlimmitted.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "t_role")
class Role implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id

	private String name

	@Transient
	@ManyToMany(mappedBy = "roles")
	private Set<User> users

	Role() {
	}


	@Override
	String getAuthority() {
		return name
	}
}