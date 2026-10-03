package com.rabreu.pokebattlearena.model;
/**
 * Representa un movimiento o ataque que un Pokémon puede ejecutar.
 * Contiene sus estadísticas base como potencia, precisión y tipo elemental.
 */
public class Move {
	private String name;
	private String nameES;
	private Type type;
	private int power;
	private int accuracy;
	private boolean isPhysical;
	
	public Move(String name, String nameES, Type type, int power, int accuracy, boolean isPhysical) {
		this.name = name;
		this.nameES = nameES;
		this.type = type;
		this.power = power;
		this.accuracy = accuracy;
		this.isPhysical = isPhysical;
	}

	public String getName() {
		return name;
	}

	public String getNameES() {
		return nameES;
	}
	
	public Type getType() {
		return type;
	}

	public int getPower() {
		return power;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public boolean isPhysical() {
		return isPhysical;
	}
}
