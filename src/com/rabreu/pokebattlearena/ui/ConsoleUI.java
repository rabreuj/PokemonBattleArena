package com.rabreu.pokebattlearena.ui;

import java.util.ArrayList;
import java.util.Scanner;
import com.rabreu.pokebattlearena.battle.*;
import com.rabreu.pokebattlearena.ui.*;
import com.rabreu.pokebattlearena.util.DamageCalculator;
import com.rabreu.pokebattlearena.util.Difficulty;
import com.rabreu.pokebattlearena.util.Language;
import com.rabreu.pokebattlearena.util.MusicPlayer;
import com.rabreu.pokebattlearena.model.*;

/**
 * Maneja toda la interacción con el usuario a través de la consola.
 * Es la única clase que debería usar System.out para imprimir y 
 * java.util.Scanner para leer datos. Muestra menús, estados de los 
 * Pokémon y traduce las acciones del usuario para el BattleManager.
 */
public class ConsoleUI {
	
	private enum SwitchContext {
		FORCED,
		VOLUNTARY,
		REACTIVE
	}
	
	// Colores en consola
	private static final String RESET  = "\u001B[0m";
	private static final String RED    = "\u001B[31m";
	private static final String YELLOW = "\u001B[33m";
	private static final String GREEN  = "\u001B[32m";
	private static final String BOLD   = "\u001B[1m";
	
	Scanner sc = new Scanner(System.in);
	BattleManager battle;
	TextManager text;
	MusicPlayer music;
	
	public ConsoleUI (BattleManager battle, TextManager text) {
		this.battle = battle;
		this.text = text;
		this.music = new MusicPlayer();
	}
	
	public void startGame() {	
		
		ArrayList<Pokemon> playerTeam = battle.getPlayerTeam();
		ArrayList<Pokemon> redTeam = battle.getRedTeam();
				
		text.setLanguage(selectLanguage());
		text.setDifficulty(selectDifficulty());
		
		
		// ═══════════════════════════════════════
	    // Música de combate
	    // ═══════════════════════════════════════
		music.play("battle_music.wav");
		music.setVolume(0.35f); // Un poco más alta durante el combate
		println(text.getStartBattleMessage());
		
		while (!battle.isTeamDefeated(playerTeam) && !battle.isTeamDefeated(redTeam)) {
			executeTurn();
		}
		
		// ═══════════════════════════════════════
	    // Música de victoria/derrota
	    // ═══════════════════════════════════════
	    music.stop(); // Detener música de combate
		
		if (battle.isTeamDefeated(redTeam)) {
			println(text.showVictoryMessage());
			music.play("victory_music.wav");
	        music.setVolume(0.4f);
		} else {
			println(text.showDefeatMessage());
		}
		
		// Pausa para que se escuche la música final
	    try {
	        Thread.sleep(10000); // 10 segundos
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    music.stop();
	}
	
	private void executeTurn() {
		int moveIndex = 0, switchIndex = 0;
		
		System.out.println();
		println("  " + BOLD + text.showTurnText() + RESET);
		System.out.println();
		
		showStatus();
		
		PlayerAction action = askAction();
		
		if (action == PlayerAction.ATTACK) {
			moveIndex = askMove();
		} else if (action == PlayerAction.SWITCH) {
			switchIndex = askSwitchPokemon(SwitchContext.VOLUNTARY);
		}
		
		battle.processTurn(action, moveIndex, switchIndex);
		
		printSeparator();
		
		for (String log : battle.getTurnLog()) {
			println(log);
		}
		
		printSeparator();
		
		battle.clearTurnLog();
		
		if (battle.isPlayerActiveFainted()) {
		    askSwitchPokemon(SwitchContext.FORCED);
		    System.out.println();
		    println(text.getPlayerEnterBattleMessage(battle.getPlayerActivePokemon().getName()));
		}

		if (battle.didRedPokemonChange() && !battle.isTeamDefeated(battle.getRedTeam())) {
			int indexBefore = battle.getPlayerActiveIndex();
		    
		    askSwitchPokemon(SwitchContext.REACTIVE);
		    
		    int indexAfter = battle.getPlayerActiveIndex();
		    
		    if (indexAfter != indexBefore) {
		        System.out.println();
		        println(text.getPlayerEnterBattleMessage(battle.getPlayerActivePokemon().getName()));
		    }
		    
		    System.out.println();
		    println(text.getRedEnterBattleMessage(battle.getRedActivePokemon().getName()));
		}
	}
	
	private void printSeparator() {
	    System.out.println();
	    System.out.println("  ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─");
	    System.out.println();
	}
	
	private void printTitle() {
	    System.out.println();
	    System.out.println("  " + BOLD + "⚡ POKÉMON BATTLE ARENA ⚡" + RESET);
	    System.out.println();
	}
	
	private Language selectLanguage() {
	    int option = -1;
	    do {
	        printTitle();
	        System.out.println("  " + BOLD + "🌐 Idiomas | Languages" + RESET);
	        System.out.println();
	        System.out.println("  " + "  [1] 🇪🇸 Español");
	        System.out.println("  " + "  [2] 🇬🇧 English");
	        System.out.println();
	        print("  ➤ Select / Elige: ");
	        
	        if (sc.hasNextInt()) {
	            option = sc.nextInt();
	        } else {
	            option = -1;
	            println("  ⚠ Opción inválida | Invalid option");
	        }
	        sc.nextLine();
	    } while (option != 1 && option != 2);
	    
	    return option == 1 ? Language.ES : Language.EN;
	}
	
	private Difficulty selectDifficulty() {
	    int option = -1;
	    do {
	        System.out.println();
	        System.out.println("  " + BOLD + "⚙ " + text.getDifficultyTitle() + RESET);
	        System.out.println();
	        System.out.println("  " + "  [1] " + text.getDifficultyEasy());
	        System.out.println("  " + "  [2] " + text.getDifficultyMedium());
	        System.out.println("  " + "  [3] " + text.getDifficultyHard());
	        System.out.println();
	        
	        print("  " + text.getOptionText());
	        
	        if (sc.hasNextInt()) {
	            option = sc.nextInt();
	        } else {
	            option = -1;
	        }
	        sc.nextLine();
	        
	        if (option < 1 || option > 3) {
	            println("  " + text.getInvalidOption());
	        } 
	        
	    } while (option < 1 || option > 3);
	    
	    if (option == 1) return Difficulty.EASY;
	    if (option == 2) return Difficulty.MEDIUM;
	    return Difficulty.HARD;
	}
	
	private void showStatus() {
	    Pokemon playerActive = battle.getPlayerActivePokemon();
	    Pokemon redActive = battle.getRedActivePokemon();
	    
	    String redBar = renderHPBar(redActive.getCurrentHP(), redActive.getMaxHP());
	    String playerBar = renderHPBar(playerActive.getCurrentHP(), playerActive.getMaxHP());
	    
	    String redName = String.format("%-12s", redActive.getName());
	    String playerName = String.format("%-12s", playerActive.getName());
	    
	    System.out.println();
	    System.out.println("  " + BOLD + "🔴 RIVAL" + RESET);
	    System.out.println("  " + redName + "  " + redBar + "  " + 
	        redActive.getCurrentHP() + "/" + redActive.getMaxHP() + " HP");
	    System.out.println();
	    System.out.println("  " + BOLD + "🔵 " + text.getYouLabel() + RESET);
	    System.out.println("  " + playerName + "  " + playerBar + "  " + 
	        playerActive.getCurrentHP() + "/" + playerActive.getMaxHP() + " HP");
	    System.out.println();
	}
	
	private PlayerAction askAction() {
	    int option = -1;
	    String pokemonName = battle.getPlayerActivePokemon().getName();
	    String title = text.getWhatWillDoTitle(pokemonName);
	    
	    do {
	        System.out.println();
	        System.out.println("  " + BOLD + "➤ " + title + RESET);
	        System.out.println();
	        System.out.println("  " + "  [1] ⚔  " + text.getPlayerActionAttack());
	        System.out.println("  " + "  [2] ⇄  " + text.getPlayerActionSwitch());
	        System.out.println();
	        
	        print(text.getOptionText());
	        
	        if (sc.hasNextInt()) {
	            option = sc.nextInt();
	        } else {
	            println(text.getInvalidOption());
	            option = -1;
	            sc.nextLine();
	        }
	        
	        if (option != 1 && option != 2) {
	            println(text.getInvalidOption());
	        }
	        
	    } while (option != 1 && option != 2);
	    
	    return option == 1 ? PlayerAction.ATTACK : PlayerAction.SWITCH;
	}
	
	private int askMove() {
	    int option = -1;
	    ArrayList<Move> moves = battle.getPlayerActivePokemon().getMoves();
	    
	    do {
	        System.out.println();
	        System.out.println("  " + BOLD + "➤ " + text.getMovesTitle() + RESET);
	        System.out.println();
	        
	        for (int i = 0; i < moves.size(); i++) {
	            Move move = moves.get(i);
	            String typeLabel = getTypeLabel(move);
	            String moveName = text.getMoveName(move);
	            String line = "  " + "  [" + (i + 1) + "] " + typeLabel + " " + moveName;
	            
	            if (text.areHintsEnabled()) {
	            	Pokemon redActive = battle.getRedActivePokemon();
	            	double effectiveness = DamageCalculator.getTypeEffectiveness(move.getType(), 
	            			redActive.getPrimaryType(), redActive.getSecondaryType());
	            	
	            	if (effectiveness == 0.0) {
	            	    line += text.getNoEffect();
	            	} else if (effectiveness < 1.0) {
	            	    line += text.getNotVeryEffective();
	            	} else if (effectiveness >= 2.0) {
	            	    line += text.getSuperEffective();
	            	} else {
	            	    line += text.getEffective();
	            	}
	            }
	            
	            System.out.println(line);
	        }
	        System.out.println();
	        
	        print(text.getOptionText());
	        
	        if (sc.hasNextInt()) {
	            option = sc.nextInt();
	        } else {
	            println(text.getInvalidOption());
	            option = -1;
	            sc.nextLine();
	        }
	        
	        if (option < 1 || option > moves.size()) {
	            println(text.getInvalidOption());
	        }
	        
	    } while (option < 1 || option > moves.size());
	    
	    return option - 1;
	}
	
	private int askSwitchPokemon(SwitchContext context) {
		int option = -1;
		String errorReason;
		
		if (context == SwitchContext.REACTIVE) {
	        System.out.println();
	        
	        println(text.nextPokemonText() + battle.getRedActivePokemon().getName() + "...");
	        System.out.println();
	        println(text.askSwitch());
	        print(text.getOptionText());
	        
	        if (sc.hasNextInt()) {
	            option = sc.nextInt();
	        } else {
	            option = -1;
	            sc.nextLine();
	        }
	        
	        if (option != 1) {
	            return battle.getPlayerActiveIndex();
	        } 
		}
		
		do {
			
			 if (context == SwitchContext.FORCED) {
				 println(text.forcedSwitch());
			 } else {
				 println(text.voluntarySwitch());
			 }
				 
			showPokemonList();
			print(text.getOptionText());
				 
			if (sc.hasNextInt()) {
	            option = sc.nextInt();
	        } else {
	            option = -1;
	            sc.nextLine(); // Limpiar buffer
	        }
			
			errorReason = battle.switchPlayerPokemon(option);
			
			if (!errorReason.isEmpty()) {
				System.out.println();
	            if (errorReason.equals("FAINTED")) {
	            	println(text.cantSwitchFainted());
	            } else if (errorReason.equals("SAME")) {
	            	println(text.cantSwitchSame());
	            } else {
	            	println(text.getInvalidOption());
	            }
	            System.out.println();
	        }
			 
		} while (!errorReason.isEmpty());
		return option;
	}
	
	private void showPokemonList() {
	    ArrayList<Pokemon> team = battle.getPlayerTeam();
	    
	    System.out.println();
	    System.out.println("  " + BOLD + "➤ " + text.getTeamLabel() + RESET);
	    System.out.println();
	    
	    for (int i = 0; i < team.size(); i++) {
	        Pokemon pokemon = team.get(i);
	        
	        if (pokemon.isFainted()) {
	            System.out.println("  " + "  [" + i + "] " + pokemon.getName() + "  " + 
	                RED + BOLD + text.getFainted() + RESET);
	            System.out.println();
	        } else {
	        	String name = String.format("%-12s", pokemon.getName());
	            String bar = renderHPBar(pokemon.getCurrentHP(), pokemon.getMaxHP());
	            System.out.println("    [" + i + "] " + name + " " + bar + "  " + 
	            	    pokemon.getCurrentHP() + "/" + pokemon.getMaxHP() + " HP");
	            System.out.println();
	        }
	    }
	    System.out.println();
	}
	
	/**
	 * Devuelve el nombre completo del tipo del movimiento, traducido y entre corchetes.
	 * Ejemplo: Earthquake → "[TIERRA]" (ES) o "[GROUND]" (EN)
	 */
	private String getTypeLabel(Move move) {
	    String type = move.getType().toString().toUpperCase();
	    
	    if (text.getCurrentLanguage() == Language.ES) {
	        switch (type) {
	            case "NORMAL":    return "[NORMAL]";
	            case "FIRE":      return "[FUEGO]";
	            case "WATER":     return "[AGUA]";
	            case "ELECTRIC":  return "[ELÉCTRICO]";
	            case "GRASS":     return "[PLANTA]";
	            case "ICE":       return "[HIELO]";
	            case "FIGHTING":  return "[LUCHA]";
	            case "POISON":    return "[VENENO]";
	            case "GROUND":    return "[TIERRA]";
	            case "FLYING":    return "[VOLADOR]";
	            case "PSYCHIC":   return "[PSÍQUICO]";
	            case "BUG":       return "[BICHO]";
	            case "ROCK":      return "[ROCA]";
	            case "GHOST":     return "[FANTASMA]";
	            case "DRAGON":    return "[DRAGÓN]";
	            case "DARK":      return "[SINIESTRO]";
	            case "STEEL":     return "[ACERO]";
	            case "FAIRY":     return "[HADA]";
	            default:          return "[???]";
	        }
	    } else {
	        // Inglés: nombre completo entre corchetes
	        return "[" + type + "]";
	    }
	}
	
	private String renderHPBar(int currentHP, int maxHP) {
	    int barLength = 20; // Longitud de la barra
	    int filled = (int) Math.round(((double) currentHP / maxHP) * barLength);
	    int empty = barLength - filled;
	    
	    // Color según el porcentaje
	    String color;
	    double percentage = (double) currentHP / maxHP;
	    if (percentage > 0.5) color = GREEN;
	    else if (percentage > 0.2) color = YELLOW;
	    else color = RED;
	    
	    // Construir la barra visual
	    StringBuilder bar = new StringBuilder();
	    bar.append(color).append(BOLD);
	    for (int i = 0; i < filled; i++) bar.append("█");
	    bar.append(RESET);
	    for (int i = 0; i < empty; i++) bar.append("░");
	    
	    return bar.toString();
	}
	
	private void println(String message) {
		System.out.println("  " + message);
		try {
			Thread.sleep(1500); // 1500 milisegundos de pausa entre cada línea
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void print(String message) {
		System.out.print("  " + message);
		try {
			Thread.sleep(1500); // 1500 milisegundos de pausa entre cada línea
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
