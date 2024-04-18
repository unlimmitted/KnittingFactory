package ru.unlimmitted.knittingfactorymes.entity.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "t_user")
class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	String username

	String password

	@Transient
	String passwordConfirm

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles


	User() {
	}

	@Override
	String getUsername() {
		return username
	}

	@Override
	boolean isAccountNonExpired() {
		return true
	}

	@Override
	boolean isAccountNonLocked() {
		return true
	}

	@Override
	boolean isCredentialsNonExpired() {
		return true
	}

	@Override
	boolean isEnabled() {
		return true
	}

	@Override
	Collection<? extends GrantedAuthority> getAuthorities() {
		return roles
	}

	@Override
	String getPassword() {
		return password
	}
}
