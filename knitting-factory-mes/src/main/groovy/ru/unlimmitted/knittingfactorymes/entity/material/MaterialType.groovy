package ru.unlimmitted.knittingfactorymes.entity.material

enum MaterialType {

	NATURAL_FABRIC("Натуральная ткань"),
	SYNTHETIC_FABRIC("Синтетическая ткань"),
	PAINT("Краска"),
	MEASURED_QUANTITIES("Мерный материал")

	String typeName

	MaterialType(String typeName) {
		this.typeName = typeName
	}

	static getByType(String typeName) {
		for (MaterialType v : values()) {
			if (v.typeName == typeName) {
				return v
			}
		}
	}
}