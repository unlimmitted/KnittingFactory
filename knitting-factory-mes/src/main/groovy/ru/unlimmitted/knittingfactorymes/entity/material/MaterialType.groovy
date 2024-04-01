package ru.unlimmitted.knittingfactorymes.entity.material

enum MaterialType {

	NATURAL_FABRIC("Полотна"),
	PAINT("Краски"),
	FINISHING("Отделочные"),
	THREAD("Нитки"),
	ELASTIC_BAND("Резинки")

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