package com.rabreu.pokebattlearena.util;

import com.rabreu.pokebattlearena.model.*;
/**
 * Clase de utilidades estáticas encargada de realizar los cálculos matemáticos del combate.
 */
public class DamageCalculator {
	
	/**
     * Calcula el daño de un movimiento.
     * @return La cantidad de daño entero a restar al HP del defensor.
     */
    public static int calculateDamage(Pokemon attacker, Move move, Pokemon defender) {
        // 1. Determinar estadísticas a usar (Físico vs Especial)
        int attackStat = move.isPhysical() ? attacker.getAttack() : attacker.getSpAttack();
        int defenseStat = move.isPhysical() ? defender.getDefense() : defender.getSpDefense();

        // 2. Fórmula base de daño de Pokémon (Simplificada)
        // (((2 * Nivel / 5 + 2) * Potencia * (Ataque / Defensa)) / 50) + 2
        double baseDamage = (((2.0 * attacker.getLevel() / 5.0 + 2.0) * move.getPower() * (attackStat / (double) defenseStat)) / 50.0) + 2.0;

        // 3. Modificadores
        double modifier = 1.0;

        // A) STAB (Same Type Attack Bonus): Si el atacante tiene el mismo tipo que el movimiento, x1.5
        if (attacker.getPrimaryType() == move.getType() || 
           (attacker.getSecondaryType() != null && attacker.getSecondaryType() == move.getType())) {
            modifier *= 1.5;
        }

        // B) Efectividad de tipos (Aquí pondremos un placeholder simple por ahora)
        double typeEffectiveness = getTypeEffectiveness(move.getType(), defender.getPrimaryType(), defender.getSecondaryType());
        modifier *= typeEffectiveness;

        // C) Aleatoriedad (Entre 0.85 y 1.00, como en el juego real)
        double randomFactor = 0.85 + (Math.random() * 0.15);
        modifier *= randomFactor;

        // 4. Calcular daño final y asegurar que sea al menos 1 si no es inmune
        int finalDamage = (int) Math.floor(baseDamage * modifier);
        return Math.max(0, finalDamage); 
    }

    /**
     * Calcula la efectividad total contra los tipos del defensor.
     * Si el defensor tiene dos tipos, multiplica los multiplicadores de ambos.
     */
    public static double getTypeEffectiveness(Type moveType, Type defType1, Type defType2) {
        double multiplier = getSingleTypeEffectiveness(moveType, defType1);
        
        // Si el defensor tiene un segundo tipo, calculamos su efectividad y la multiplicamos
        if (defType2 != null) {
            multiplier *= getSingleTypeEffectiveness(moveType, defType2);
        }
        
        return multiplier;
    }
    
    /**
     * Calcula la efectividad de un tipo de movimiento contra UN SOLO tipo defensor.
     * Basado en la tabla de tipos de la 4ª Generación (HeartGold/SoulSilver).
     */
    private static double getSingleTypeEffectiveness(Type moveType, Type defType) {
        switch (moveType) {
            case NORMAL:
                if (defType == Type.ROCK || defType == Type.STEEL) return 0.5;
                if (defType == Type.GHOST) return 0.0;
                break;
            case FIRE:
                if (defType == Type.GRASS || defType == Type.ICE || defType == Type.BUG || defType == Type.STEEL) return 2.0;
                if (defType == Type.FIRE || defType == Type.WATER || defType == Type.ROCK || defType == Type.DRAGON) return 0.5;
                break;
            case WATER:
                if (defType == Type.FIRE || defType == Type.GROUND || defType == Type.ROCK) return 2.0;
                if (defType == Type.WATER || defType == Type.GRASS || defType == Type.DRAGON) return 0.5;
                break;
            case GRASS:
                if (defType == Type.WATER || defType == Type.GROUND || defType == Type.ROCK) return 2.0;
                if (defType == Type.FIRE || defType == Type.GRASS || defType == Type.POISON || defType == Type.FLYING || 
                    defType == Type.BUG || defType == Type.DRAGON || defType == Type.STEEL) return 0.5;
                break;
            case ELECTRIC:
                if (defType == Type.WATER || defType == Type.FLYING) return 2.0;
                if (defType == Type.ELECTRIC || defType == Type.GRASS || defType == Type.DRAGON) return 0.5;
                if (defType == Type.GROUND) return 0.0;
                break;
            case ICE:
                if (defType == Type.GRASS || defType == Type.GROUND || defType == Type.FLYING || defType == Type.DRAGON) return 2.0;
                if (defType == Type.FIRE || defType == Type.WATER || defType == Type.ICE || defType == Type.STEEL) return 0.5;
                break;
            case FIGHTING:
                if (defType == Type.NORMAL || defType == Type.ICE || defType == Type.ROCK || defType == Type.DARK || defType == Type.STEEL) return 2.0;
                if (defType == Type.POISON || defType == Type.FLYING || defType == Type.PSYCHIC || defType == Type.BUG) return 0.5;
                if (defType == Type.GHOST) return 0.0;
                break;
            case POISON:
                if (defType == Type.GRASS) return 2.0;
                if (defType == Type.POISON || defType == Type.GROUND || defType == Type.ROCK || defType == Type.GHOST) return 0.5;
                if (defType == Type.STEEL) return 0.0;
                break;
            case GROUND:
                if (defType == Type.FIRE || defType == Type.ELECTRIC || defType == Type.POISON || defType == Type.ROCK || defType == Type.STEEL) return 2.0;
                if (defType == Type.GRASS || defType == Type.BUG) return 0.5;
                if (defType == Type.FLYING) return 0.0;
                break;
            case FLYING:
                if (defType == Type.GRASS || defType == Type.FIGHTING || defType == Type.BUG) return 2.0;
                if (defType == Type.ELECTRIC || defType == Type.ROCK || defType == Type.STEEL) return 0.5;
                break;
            case PSYCHIC:
                if (defType == Type.FIGHTING || defType == Type.POISON) return 2.0;
                if (defType == Type.PSYCHIC || defType == Type.STEEL) return 0.5;
                if (defType == Type.DARK) return 0.0;
                break;
            case BUG:
                if (defType == Type.GRASS || defType == Type.PSYCHIC || defType == Type.DARK) return 2.0;
                if (defType == Type.FIRE || defType == Type.FIGHTING || defType == Type.POISON || defType == Type.FLYING || 
                    defType == Type.GHOST || defType == Type.STEEL) return 0.5;
                break;
            case ROCK:
                if (defType == Type.FIRE || defType == Type.ICE || defType == Type.FLYING || defType == Type.BUG) return 2.0;
                if (defType == Type.FIGHTING || defType == Type.GROUND || defType == Type.STEEL) return 0.5;
                break;
            case GHOST:
                if (defType == Type.PSYCHIC || defType == Type.GHOST) return 2.0;
                if (defType == Type.DARK) return 0.5;
                if (defType == Type.NORMAL) return 0.0;
                break;
            case DRAGON:
                if (defType == Type.DRAGON) return 2.0;
                if (defType == Type.STEEL) return 0.5;
                break;
            case DARK:
                if (defType == Type.PSYCHIC || defType == Type.GHOST) return 2.0;
                if (defType == Type.FIGHTING || defType == Type.DARK) return 0.5;
                break;
            case STEEL:
                if (defType == Type.ICE || defType == Type.ROCK) return 2.0;
                if (defType == Type.FIRE || defType == Type.WATER || defType == Type.ELECTRIC || defType == Type.STEEL) return 0.5;
                break;
        }
        
        // Si no entra en ningún if, el daño es neutro (x1)
        return 1.0; 
    }
}
