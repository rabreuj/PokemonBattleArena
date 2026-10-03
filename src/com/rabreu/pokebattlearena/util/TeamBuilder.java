package com.rabreu.pokebattlearena.util;

import com.rabreu.pokebattlearena.model.*;
import java.util.ArrayList;

/**
 * Clase de utilidad encargada de instanciar y configurar los equipos 
 * predeterminados del juego con sus estadísticas y movimientos exactos.
 */
public class TeamBuilder {
		
	/**
	 * Método auxiliar para crear movimientos de forma más limpia y legible.
	 * Utilizo la practica profesional DRY (dont repeat yourself)
	 */
	private static Move createMove(String name, String nameES, Type type, int power, int accuracy, boolean isPhysical) {
		return new Move(name, nameES, type, power, accuracy, isPhysical);
    }
	
	 /**
     * Crea y devuelve el equipo del Entrenador RED (Monte Plateado).
     */
    public static ArrayList<Pokemon> createRedTeam() {
        ArrayList<Pokemon> redTeam = new ArrayList<>();

        // 1. PIKACHU (Nivel 88)
        Pokemon pikachu = new Pokemon("PIKACHU", Type.ELECTRIC, null, 88, 250, 210, 130, 180, 190, 240);
        pikachu.learnMove(createMove("Volt Tackle", "Placaje Eléctrico", Type.ELECTRIC, 120, 100, true));
        pikachu.learnMove(createMove("Iron Tail", "Cola Férrea", Type.STEEL, 100, 75, true));
        pikachu.learnMove(createMove("Quick Attack", "Ataque Rápido", Type.NORMAL, 40, 100, true));
        pikachu.learnMove(createMove("Thunderbolt", "Rayo", Type.ELECTRIC, 90, 100, false));
        redTeam.add(pikachu);

        // 2. LAPRAS (Nivel 80)
        Pokemon lapras = new Pokemon("LAPRAS", Type.WATER, Type.ICE, 80, 310, 190, 180, 200, 210, 150);
        lapras.learnMove(createMove("Body Slam", "Golpe Cuerpo", Type.NORMAL, 85, 100, true));
        lapras.learnMove(createMove("Brine", "Salmuera", Type.WATER, 65, 100, false));
        lapras.learnMove(createMove("Blizzard", "Ventisca", Type.ICE, 110, 70, false));
        lapras.learnMove(createMove("Psychic", "Psíquico", Type.PSYCHIC, 90, 100, false));
        redTeam.add(lapras);

        // 3. SNORLAX (Nivel 82)
        Pokemon snorlax = new Pokemon("SNORLAX", Type.NORMAL, null, 82, 410, 240, 160, 170, 200, 80);
        snorlax.learnMove(createMove("Shadow Ball", "Bola Sombra", Type.GHOST, 80, 100, false));
        snorlax.learnMove(createMove("Crunch", "Triturar", Type.DARK, 80, 100, true));
        snorlax.learnMove(createMove("Blizzard", "Ventisca", Type.ICE, 110, 70, false));
        snorlax.learnMove(createMove("Giga Impact", "Gigaimpacto", Type.NORMAL, 150, 90, true));
        redTeam.add(snorlax);

        // 4. VENUSAUR (Nivel 84)
        Pokemon venusaur = new Pokemon("VENUSAUR", Type.GRASS, Type.POISON, 84, 295, 180, 190, 220, 210, 180);
        venusaur.learnMove(createMove("Frenzy Plant", "Planta Feroz", Type.GRASS, 150, 90, false));
        venusaur.learnMove(createMove("Giga Drain", "Gigadrenado", Type.GRASS, 75, 100, false));
        venusaur.learnMove(createMove("Sludge Bomb", "Bomba Lodo", Type.POISON, 90, 100, false));
        venusaur.learnMove(createMove("Sleep Powder", "Somnífero", Type.GRASS, 0, 75, false));
        redTeam.add(venusaur);

        // 5. CHARIZARD (Nivel 84)
        Pokemon charizard = new Pokemon("CHARIZARD", Type.FIRE, Type.FLYING, 84, 285, 190, 180, 230, 200, 210);
        charizard.learnMove(createMove("Blast Burn", "Anillo Ígneo", Type.FIRE, 150, 90, false));
        charizard.learnMove(createMove("Flare Blitz", "Envite Ígneo", Type.FIRE, 120, 100, true));
        charizard.learnMove(createMove("Air Slash", "Tajo Aéreo", Type.FLYING, 75, 95, false));
        charizard.learnMove(createMove("Dragon Pulse", "Pulso Dragón", Type.DRAGON, 85, 100, false));
        redTeam.add(charizard);

        // 6. BLASTOISE (Nivel 84)
        Pokemon blastoise = new Pokemon("BLASTOISE", Type.WATER, null, 84, 295, 180, 220, 210, 230, 170);
        blastoise.learnMove(createMove("Hydro Cannon", "Hidrocañón", Type.WATER, 150, 90, false));
        blastoise.learnMove(createMove("Blizzard", "Ventisca", Type.ICE, 110, 70, false));
        blastoise.learnMove(createMove("Flash Cannon", "Foco Resplandor", Type.STEEL, 80, 100, false));
        blastoise.learnMove(createMove("Focus Blast", "Onda Certera", Type.FIGHTING, 120, 70, false));
        redTeam.add(blastoise);
        return redTeam;
    }
    
    /**
     * Crea y devuelve el equipo del JUGADOR.
     */
    public static ArrayList<Pokemon> createPlayerTeam() {
        ArrayList<Pokemon> playerTeam = new ArrayList<>();

        // 1. MAMOSWINE (Nivel 85) - Asesino de Pikachu y Charizard
        Pokemon mamoswine = new Pokemon("MAMOSWINE", Type.ICE, Type.GROUND, 85, 320, 260, 180, 140, 170, 180);
        mamoswine.learnMove(createMove("Earthquake", "Terremoto", Type.GROUND, 100, 100, true));
        mamoswine.learnMove(createMove("Ice Shard", "Canto Helado", Type.ICE, 40, 100, true));
        mamoswine.learnMove(createMove("Stone Edge", "Roca Afilada", Type.ROCK, 100, 80, true));
        mamoswine.learnMove(createMove("Superpower", "Fuerza Bruta", Type.FIGHTING, 120, 100, true));
        playerTeam.add(mamoswine);

        // 2. MACHAMP (Nivel 85) - Destructor de Snorlax y Lapras
        Pokemon machamp = new Pokemon("MACHAMP", Type.FIGHTING, null, 85, 310, 260, 170, 140, 180, 150);
        machamp.learnMove(createMove("Dynamic Punch", "Puño Dinámico", Type.FIGHTING, 100, 50, true));
        machamp.learnMove(createMove("Stone Edge", "Roca Afilada", Type.ROCK, 100, 80, true));
        machamp.learnMove(createMove("Payback", "Vendetta", Type.DARK, 50, 100, true));
        machamp.learnMove(createMove("Ice Punch", "Puño Hielo", Type.ICE, 75, 100, true));
        playerTeam.add(machamp);

        // 3. TYRANITAR (Nivel 86) - Tanque y asesino de Charizard
        Pokemon tyranitar = new Pokemon("TYRANITAR", Type.ROCK, Type.DARK, 86, 330, 260, 220, 190, 210, 160);
        tyranitar.learnMove(createMove("Stone Edge", "Roca Afilada", Type.ROCK, 100, 80, true));
        tyranitar.learnMove(createMove("Crunch", "Triturar", Type.DARK, 80, 100, true));
        tyranitar.learnMove(createMove("Earthquake", "Terremoto", Type.GROUND, 100, 100, true));
        tyranitar.learnMove(createMove("Fire Fang", "Colmillo Ígneo", Type.FIRE, 65, 95, true));
        playerTeam.add(tyranitar);

        // 4. GENGAR (Nivel 85) - Inmune a Normal, rápido y letal
        Pokemon gengar = new Pokemon("GENGAR", Type.GHOST, Type.POISON, 85, 240, 150, 140, 260, 160, 230);
        gengar.learnMove(createMove("Shadow Ball", "Bola Sombra", Type.GHOST, 80, 100, false));
        gengar.learnMove(createMove("Focus Blast", "Onda Certera", Type.FIGHTING, 120, 70, false));
        gengar.learnMove(createMove("Sludge Bomb", "Bomba Lodo", Type.POISON, 90, 100, false));
        gengar.learnMove(createMove("Thunderbolt", "Rayo", Type.ELECTRIC, 90, 100, false));
        playerTeam.add(gengar);

        // 5. VILEPLUME (Nivel 84) - Tanque especial contra Blastoise
        Pokemon vileplume = new Pokemon("VILEPLUME", Type.GRASS, Type.POISON, 84, 290, 170, 180, 220, 190, 140);
        vileplume.learnMove(createMove("Solar Beam", "Rayo Solar", Type.GRASS, 120, 100, false));
        vileplume.learnMove(createMove("Sludge Bomb", "Bomba Lodo", Type.POISON, 90, 100, false));
        vileplume.learnMove(createMove("Sleep Powder", "Somnífero", Type.GRASS, 0, 75, false));
        vileplume.learnMove(createMove("Moonlight", "Luz Lunar", Type.NORMAL, 0, 100, false));
        playerTeam.add(vileplume);

        // 6. AMPHAROS (Nivel 84) - Soporte especial y cobertura
        Pokemon ampharos = new Pokemon("AMPHAROS", Type.ELECTRIC, null, 84, 300, 160, 170, 240, 190, 150);
        ampharos.learnMove(createMove("Thunderbolt", "Rayo", Type.ELECTRIC, 90, 100, false));
        ampharos.learnMove(createMove("Focus Blast", "Onda Certera", Type.FIGHTING, 120, 70, false));
        ampharos.learnMove(createMove("Signal Beam", "Doble Rayo", Type.BUG, 75, 100, false));
        ampharos.learnMove(createMove("Power Gem", "Joya de Luz", Type.ROCK, 80, 100, false));
        playerTeam.add(ampharos);

        return playerTeam;
    }
}
