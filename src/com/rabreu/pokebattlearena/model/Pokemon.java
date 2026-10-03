package com.rabreu.pokebattlearena.model;

import java.util.ArrayList;

/**
 * Entidad principal del juego. Representa a un Pokémon individual.
 * Gestiona su propio estado de salud (HP), nivel, tipo y su arsenal 
 * de movimientos (almacenados en un ArrayList). 
 * Encapsula la lógica de recibir daño y verificar si ha sido debilitado.
 */
public class Pokemon {
	private String name;
	private Type primaryType;
	private Type secondaryType;
	private int level;
	
	// Estadisticas de combate
	private int currentHP;
	private int maxHP;
	private int attack;
    private int defense;
    private int spAttack;
    private int spDefense;
    private int speed;
	
	private ArrayList<Move> moves;
	
	public Pokemon(String name, Type primaryType, Type secondaryType, int level, 
			int maxHP, int attack, int defense, int spAttack, int spDefense, int speed) {
		this.name = name;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
        this.moves = new ArrayList<>();
	}
	
	public void learnMove(Move move) {
		if (getMoveCount() < 4) {
			moves.add(move);
		} else {
			System.out.println(this.name + " no puede aprender mas movimientos (Máximo 4).");
		}
	}
	
	public Move getMove(int index) {
		return moves.get(index);
	}
	
	public int getMoveCount() {
		return moves.size();
	}

	public void takeDamage(int amount) {
		currentHP -= amount;
		
		if (currentHP < 0) {
			currentHP = 0;
		}
	}
	
	public boolean isFainted() {
		return currentHP <= 0;
	}
	
	// Getters
	
	public String getName() {
		return name;
	}
	
	public Type getPrimaryType() {
		return primaryType;
	}
	
	public Type getSecondaryType() {
		return secondaryType;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getSpAttack() {
		return spAttack;
	}
	
	public int getSpDefense() {
		return spDefense;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public ArrayList<Move> getMoves() {
		return moves;
	}
}
