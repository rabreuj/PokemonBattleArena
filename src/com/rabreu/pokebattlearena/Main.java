/**
 * =====================================================================
 * Proyecto:    PokemonBattleArena (Simulador de Combate Pokémon)
 * Descripción: Juego por consola que simula un combate 1vs1 entre dos 
 *              entrenadores. El usuario controla a su equipo mediante 
 *              un menú interactivo, mientras que el rival es controlado 
 *              por una Inteligencia Artificial (IA) que toma decisiones 
 *              automáticas basadas en el estado del combate.
 *              
 * Autor:       Raúl Abreu Jorge
 * Fecha:       9/27/2026
 * Versión:     1.0.0
 * 
 * Derechos de Autor: Copyright (c) 2026 Raul Abreu Jorge. 
 * Todos los derechos reservados. Este proyecto ha sido desarrollado con 
 * fines educativos y de aprendizaje de Programación Orientada a Objetos 
 * en Java. Pokémon es una marca registrada de Nintendo, Game Freak y Creatures Inc.
 * =====================================================================
 */
package com.rabreu.pokebattlearena;

import com.rabreu.pokebattlearena.battle.BattleManager;
import com.rabreu.pokebattlearena.ui.ConsoleUI;
import com.rabreu.pokebattlearena.ui.TextManager;
import com.rabreu.pokebattlearena.util.TeamBuilder;
import java.util.ArrayList;
import com.rabreu.pokebattlearena.model.Pokemon;

/**
 * Punto de entrada de la aplicación.
 * Inicializa los equipos, el controlador de batalla y la interfaz de usuario.
 */
public class Main {
	public static void main(String[] args) {
		// Crear los equipos usando TeamBuilder
        ArrayList<Pokemon> playerTeam = TeamBuilder.createPlayerTeam();
        ArrayList<Pokemon> redTeam = TeamBuilder.createRedTeam();
        
        // Crear el gestor de textos (para los idiomas)
        TextManager text = new TextManager();
        
        // Crear el controlador de batalla (el cerebro)
        BattleManager battle = new BattleManager(playerTeam, redTeam, text);
           
        // Crear la interfaz de usuario, pasándole el cerebro y el gestor de textos
        ConsoleUI ui = new ConsoleUI(battle, text);
        
        // Iniciar el juego
        ui.startGame();
	}
}
