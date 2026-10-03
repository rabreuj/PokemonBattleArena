package com.rabreu.pokebattlearena.ui;

import com.rabreu.pokebattlearena.util.*;
import com.rabreu.pokebattlearena.model.*;

public class TextManager {
	Language currentLanguage;
	Difficulty currentDifficulty;
	
	public void setLanguage(Language language) {
		this.currentLanguage = language;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.currentDifficulty = difficulty;
	}
	
	public String getStartBattleMessage() {
		if (currentLanguage == Language.ES) {
			return "\n   ¡El Entrenador Red te desafía!";
		} else {
			return "\n   You are challenged by Trainer Red!";
		}
	}
	
	public String getDifficultyTitle() {
		if (currentLanguage == Language.ES) {
			return "Seleccionar dificultad";
		} else {
			return "Select difficulty";
		}
	}
	
	public String getDifficultyEasy() {
		if (currentLanguage == Language.ES) {
			return "FÁCIL";
		} else {
			return "EASY";
		}
	}

	public String getDifficultyMedium() {
		if (currentLanguage == Language.ES) {
			return "MEDIO";
		} else {
			return "MEDIUM";
		}
	}

	public String getDifficultyHard() {
		if (currentLanguage == Language.ES) {
			return "DIFÍCIL";
		} else {
			return "HARD";
		}
	}
	
	public String getOptionMessage() {
		if (currentLanguage == Language.ES) {
			return "Seleccionar opción";
		} else {
			return "Select option";
		}
	}
	
	public String getInvalidOption() {
		if (currentLanguage == Language.ES) {
			return "Opción inválida";
		} else {
			return "Invalid option";
		}
	}
	
	public String getPlayerStatus() {
		if (currentLanguage == Language.ES) {
			return " de Jugador tiene: ";
		} else {
			return " of Player has: ";
		}
	}
	
	public String getRedStatus() {
		if (currentLanguage == Language.ES) {
			return " de Entrenador Red tiene: ";
		} else {
			return " of Trainer Red has: ";
		}
	}
	
	public String getFainted() {
		if (currentLanguage == Language.ES) {
			return "Debilitado";
		} else {
			return "Fainted";
		}
	}
	
	public String getMovesTitle() {
	    if (currentLanguage == Language.ES) {
	        return "MOVIMIENTOS";
	    } else {
	        return "MOVES";
	    }
	}

	public String getWhatWillDoTitle(String pokemon) {
	    if (currentLanguage == Language.ES) {
	        return "¿Qué hará " + pokemon + "?";
	    } else {
	        return "What will " + pokemon + " do?";
	    }
	}
	
	public String getOptionText() {
		if (currentLanguage == Language.ES) {
			return "Elige una opción ➤ ";
		} else {
			return "Choose an the option ➤ ";
		}
	}
	
	public String getPlayerActionAttack() {
		if (currentLanguage == Language.ES) {
			return "LUCHAR";
		} else {
			return "FIGHT";
		}
	}
	
	public String getPlayerActionSwitch() {
		if (currentLanguage == Language.ES) {
			return "POKÉMON";
		} else {
			return "POKÉMON";
		}
	}
	
	public String askMoveText() {
		if (currentLanguage == Language.ES) {
			return "¿Qué movimiento usará?";
		} else {
			return "Which move will it use?";		}
	}
	
	public String nextPokemonText() {
		if (currentLanguage == Language.ES) {
			return "Entrenador Red está a punto de enviar a ";
		} else {
			return "Trainer Red is about to send in ";
		}
	}
	
	public String askSwitch() {
		if (currentLanguage == Language.ES) {
			return "¿Quieres cambiar de Pokémon?\n   [1] Si\n   [2] No";
		} else {
			return "Will you switch your Pokémon?\n   [1] Yes\n   [2] No";
		}
	}
	
	public String voluntarySwitch() {
		if (currentLanguage == Language.ES) {
			return "¿A qué Pokémon quieres enviar?";
		} else {
			return "Which Pokémon will you send out?";
		}
	}
	
	public String getYouLabel() {
		if (currentLanguage == Language.ES) {
			return "TÚ";
		} else {
			return "YOU";
		}
	}
	
	public String forcedSwitch() {
		if (currentLanguage == Language.ES) {
			return "Debes escoger otro Pokémon";
		} else {
			return "You have to choose another Pokémon";
		}
	}
	
	public String cantSwitchFainted() {
		if (currentLanguage == Language.ES) {
			return "¡Ese Pokémon está debilitado!";
		} else {
			return "That Pokémon is fainted!";
		}
	}
	
	public String cantSwitchSame() {
		if (currentLanguage == Language.ES) {
			return "¡Ese Pokémon ya está en combate!";
		} else {
			return "That Pokémon is already in combat!";
		}
	}
	
	public String showTurnText() {
		if (currentLanguage == Language.ES) {
			return "──────────── INICIO DE TURNO ────────────";
		} else {
			return "──────────── TURN BEGINS ────────────";
		}
	}
	
	public String showVictoryMessage() {
		if (currentLanguage == Language.ES) {
			return "¡Derrotaste al Entranador Red!";
		} else {
			return "You defeated Trainer Red!";
		}
	}
	
	public String showDefeatMessage() {
		if (currentLanguage == Language.ES) {
			return "Has sido derrotado por el Entrenador Red...";
		} else {
			return "You have been defeated by Trainer Red...";
		}
	}
	
	public String getPlayerAttackMessage(String attacker, Move move) {
	    String moveName = getMoveName(move);
	    if (currentLanguage == Language.ES) {
	        return "¡" + attacker + " usó " + moveName + "!";
	    } else {
	        return attacker + " used " + moveName + "!";
	    }
	}
	
	public String getRedAttackMessage(String attacker, Move move) {
	    String moveName = getMoveName(move);
	    if (currentLanguage == Language.ES) {
	        return "¡El " + attacker + " enemigo usó " + moveName + "!";
	    } else {
	        return "The foe's " + attacker + " used " + moveName + "!";
	    }
	}
	
	public String getNoEffectMessage(String defender) {
		if (currentLanguage == Language.ES) {
			return "No afecta a " + defender + "...";
		} else {
			return "It doesn't affect "  + defender + "...";
		}
	}
	
	public String getDamageMessage(String defender, int damage) {
		if (currentLanguage == Language.ES) {
			return "   " + defender + " recibe " + damage + " PS.";
		} else {
			return "   " + defender + " took " + damage + " HP.";
		}
	}
	
	public String getHPMessage(String defender, int currentHP, int maxHP) {
		if (currentLanguage == Language.ES) {
			return "HP restante de " + defender + ": " + currentHP + "/" + maxHP;
		} else {
			return defender + "'s remaining HP: " + currentHP + "/" + maxHP;
		}
	}
	
	public String getPlayerFaintedMessage(String pokemon) {
		if (currentLanguage == Language.ES) {
			return "¡" + pokemon + " se debilitó!";
		} else {
			return pokemon + " fainted!";
		}
	}
	
	public String getRedFaintedMessage(String pokemon) {
		if (currentLanguage == Language.ES) {
			return "¡El " + pokemon + " del enemigo se debilitó!";
		} else {
			return "The foe's " + pokemon + " fainted!";
		}
	}
	
	public String getTeamLabel() {
		if (currentLanguage == Language.ES) {
			return "TU EQUIPO";
		} else {
			return "YOUR TEAM";
		}
	}
	
	public String getPlayerEnterBattleMessage(String pokemon) {
		if (currentLanguage == Language.ES) {
			return "¡Adelante, " + pokemon + "!";
		} else {
			return "Go! " + pokemon + "!";
		}
	}
	
	public String getRedEnterBattleMessage(String pokemon) {
		if (currentLanguage == Language.ES) {
			return "¡Entrenador Red envió a " + pokemon + "!";
		} else {
			return "Trainer Red sent out " + pokemon + "!";
		}
	}
	
	public String getMustChooseAnotherPokemon() {
		if (currentLanguage == Language.ES) {
			return "¡Debes elegir otro Pokémon!";
		} else {
			return "You must choose another Pokémon!";
		}
	}
	
	public String getSuperEffective() {
		if (currentLanguage == Language.ES) {
			return "   ⬆ Súper efectivo";
		} else {
			return "   ⬆ Super effective";
		}
	}
	
	public String getEffective() {
		if (currentLanguage == Language.ES) {
			return "   = Efectivo";
		} else {
			return "   = Effective";
		}
	}
	
	public String getNotVeryEffective() {
		if (currentLanguage == Language.ES) {
			return "   ⬇ Poco efectivo";
		} else {
			return "   ⬇ Not very effective";
		}
	}
	
	public String getNoEffect() {
		if (currentLanguage == Language.ES) {
			return "   ✕ No afecta";
		} else {
			return "   ✕ No effect";
		}
	}
	
	public String getMissMessage(String pokemon) {
		if (currentLanguage == Language.ES) {
			return "¡El ataque de " + pokemon + " falló!";
		} else {
			return pokemon + "'s attack missed!";
		}
	}
	
	public Language getCurrentLanguage() {
	    return currentLanguage;
	}
	
	public boolean areHintsEnabled() {
		if (currentDifficulty == Difficulty.EASY || currentDifficulty == Difficulty.MEDIUM) {
			return true;
		}
		return false;
	}
	
	public String getMoveName(Move move) {
	    if (currentLanguage == Language.ES) {
	        return move.getNameES();
	    } else {
	        return move.getName();
	    }
	}
}
