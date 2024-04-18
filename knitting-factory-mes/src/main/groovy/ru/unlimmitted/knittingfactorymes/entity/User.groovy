package ru.unlimmitted.knittingfactorymes.entity

import lombok.Builder

@Builder
class User {
	Integer id
	String username
	String password
	String role
}
