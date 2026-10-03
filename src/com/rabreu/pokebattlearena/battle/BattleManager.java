package com.rabreu.pokebattlearena.battle;

import com.rabreu.pokebattlearena.model.*;
import com.rabreu.pokebattlearena.ui.*;
import com.rabreu.pokebattlearena.util.*;
import java.util.ArrayList;
import java.util.List;
/**
 * El controlador principal del combate.
 * Orquesta el flujo del juego por turnos, calcula el daño utilizando 
 * las utilidades, alterna los turnos entre los entrenadores y 
 * determina las condiciones de victoria o derrota.
 */
public class BattleManager {	
	private ArrayList<Pokemon> playerTeam;
	private ArrayList<Pokemon> redTeam;
	private TextManager textManager;
	private List<String> turnLog = new ArrayList<>();
	private boolean redPokemonChangedThisTurn = false;
	private int playerActiveIndex = 0;
	private int redActiveIndex = 0;
	
	public BattleManager(ArrayList<Pokemon> playerTeam, ArrayList<Pokemon> redTeam, TextManager textManager) {
		this.playerTeam = playerTeam;
		this.redTeam = redTeam;
		this.textManager = textManager;
	}
		
	public void attack(Pokemon attacker, Pokemon defender, int moveNumber) {
		Move attackMove = attacker.getMove(moveNumber);
		
		int roll = (int) (Math.random() * 100) + 1;
		
		if (roll > attackMove.getAccuracy()) {
			turnLog.add(textManager.getMissMessage(attacker.getName()));
			return;
		}
		
		int damage = DamageCalculator.calculateDamage(attacker, attackMove, defender);
		
		boolean isPlayerAttacking = (attacker == getPlayerActivePokemon());
		
		if (isPlayerAttacking) {
			turnLog.add(textManager.getPlayerAttackMessage(attacker.getName(), attackMove));
		} else {
			turnLog.add(textManager.getRedAttackMessage(attacker.getName(), attackMove));
		}
		
		if (damage == 0) {
			turnLog.add(textManager.getNoEffectMessage(defender.getName()));
	    } else {
	        defender.takeDamage(damage);
	        turnLog.add(textManager.getDamageMessage(defender.getName(), damage));
	        turnLog.add(buildHPBarText(defender));
	    }
				
		if (defender.isFainted()) {
			boolean isPlayerDefender = (defender == getPlayerActivePokemon());
	        if (isPlayerDefender) {
	            turnLog.add(textManager.getPlayerFaintedMessage(defender.getName()));
	        } else {
	            turnLog.add(textManager.getRedFaintedMessage(defender.getName()));
	        }
		}		
	}
	
	// Validar y ejecutar cambio de pokemon
	public String switchPlayerPokemon(int newIndex) {
		if (newIndex < 0 || newIndex >= playerTeam.size()) {
			return "INVALID";
		} else if (playerTeam.get(newIndex).isFainted()) {
			return "FAINTED";
		} else if (playerTeam.get(newIndex) == getPlayerActivePokemon()) {
			return "SAME";
		}
		playerActiveIndex = newIndex;
		return "";
	}
	
	// Comprbar estado del pokemon activo (HELPER)
	public boolean isPlayerActiveFainted() {
		return playerTeam.get(playerActiveIndex).isFainted();
	}
	
	public boolean isRedActiveFainted() {
		return redTeam.get(redActiveIndex).isFainted();
	}
	
	public void processTurn(PlayerAction playerAction, int playerMoveIndex, int playerSwitchIndex) {
		resetTurnFlags();
		
		Pokemon playerPoke = getPlayerActivePokemon();
		Pokemon redPoke = getRedActivePokemon();
		
		int redMoveIndex = (int) (Math.random() * redPoke.getMoves().size()); // numero aleatorio entre 0 y la cantidad de movimientos de red

		
		if (playerAction == PlayerAction.SWITCH) {
			switchPlayerPokemon(playerSwitchIndex);
			 playerPoke = getPlayerActivePokemon();
			 
			 turnLog.add(textManager.getPlayerEnterBattleMessage(playerPoke.getName()));
		     
		     attack(redPoke, playerPoke, redMoveIndex);
		     return;
		}
		
		Pokemon first, second;
		int firstMoveIndex, secondMoveIndex;
			
		if (playerPoke.getSpeed() > redPoke.getSpeed()) {
			first = playerPoke;
			firstMoveIndex = playerMoveIndex;
			
			second = redPoke;
			secondMoveIndex = redMoveIndex;
				
		} else if (playerPoke.getSpeed() < redPoke.getSpeed()) {
			first = redPoke;
			firstMoveIndex = redMoveIndex;

			second = playerPoke;
			secondMoveIndex = playerMoveIndex;
				
		} else {
			// Empate de velocidad, decidir al azar
			if (Math.random() > 0.5) {
				first = playerPoke;
				firstMoveIndex = playerMoveIndex;
					
				second = redPoke;
				secondMoveIndex = redMoveIndex;
			} else {
				first = redPoke;
				firstMoveIndex = redMoveIndex;
					
				second = playerPoke;
				secondMoveIndex = playerMoveIndex;
			}
		}
			
		attack(first, second, firstMoveIndex);
			
		if (second.isFainted()) {
			if (second != playerPoke) {
				if (!isTeamDefeated(redTeam)) {
				    advanceActivePokemon(false); // Cambio automatico de red
				    redPokemonChangedThisTurn = true;
				}
			} 		
		} else {
            attack(second, first, secondMoveIndex);
                
            if (first.isFainted()) {
            	if (first != playerPoke) {
            		if (!isTeamDefeated(redTeam)) {
            		    advanceActivePokemon(false);
            		    redPokemonChangedThisTurn = true; // Cambio automatico de red
            		}
               	}     
            }
		}
	}		
	
	
	private void advanceActivePokemon(boolean isPlayer) {
		ArrayList<Pokemon> team = isPlayer ? playerTeam : redTeam;
		int currentIndex = isPlayer ? playerActiveIndex : redActiveIndex;
		
		// Buscamos el siguiente índice que NO esté debilitado
		int nextIndex = currentIndex + 1;
		while (nextIndex < team.size() && team.get(nextIndex).isFainted()) {
			nextIndex++;
		}
		
		if (nextIndex < team.size()) {
			// Actualizamos el índice activo
			if (isPlayer) {
				playerActiveIndex = nextIndex;
			} else {
				redActiveIndex = nextIndex;
			}
		} else {
	 }
	}
	
	public boolean isTeamDefeated(ArrayList<Pokemon> team) {
		for (Pokemon pokemon : team) {
			if (!pokemon.isFainted()) {
				return false;
			}
		}
		return true;
	}
		
	public void clearTurnLog() {
		turnLog.clear();
	}
	
	public void resetTurnFlags() {
	    redPokemonChangedThisTurn = false;
	}
	
	private String buildHPBarText(Pokemon pokemon) {
	    int barLength = 20;
	    int filled = (int) Math.round(((double) pokemon.getCurrentHP() / pokemon.getMaxHP()) * barLength);
	    int empty = barLength - filled;
	    
	    // Códigos ANSI para colores
	    String RESET  = "\u001B[0m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String GREEN  = "\u001B[32m";
	    String BOLD   = "\u001B[1m";
	    
	    // Elegir color según porcentaje
	    String color;
	    double percentage = (double) pokemon.getCurrentHP() / pokemon.getMaxHP();
	    if (percentage > 0.5) color = GREEN;
	    else if (percentage > 0.2) color = YELLOW;
	    else color = RED;
	    
	    // Construir la barra
	    StringBuilder bar = new StringBuilder();
	    bar.append("   ");
	    bar.append(color).append(BOLD);
	    for (int i = 0; i < filled; i++) bar.append("█");
	    bar.append(RESET);
	    for (int i = 0; i < empty; i++) bar.append("░");
	    bar.append("  ");
	    bar.append(pokemon.getCurrentHP()).append("/").append(pokemon.getMaxHP()).append(" HP");
	    
	    return bar.toString();
	}
	
	// Getters
	
	public Pokemon getPlayerActivePokemon() {
		return playerTeam.get(playerActiveIndex);
	}
	
	public int getPlayerActiveIndex() {
		return playerActiveIndex;
	}
	
	public Pokemon getRedActivePokemon() {
		return redTeam.get(redActiveIndex);
	}
	
	public ArrayList<Pokemon> getPlayerTeam() {
		return playerTeam;
	}

	public ArrayList<Pokemon> getRedTeam() {
		return redTeam;
	}
	
	public boolean didRedPokemonChange() {
	    return redPokemonChangedThisTurn;
	}
	
	public List<String> getTurnLog() {
		return turnLog;
	}
}
