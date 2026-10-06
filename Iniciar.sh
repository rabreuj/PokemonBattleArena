#!/bin/bash

# ==========================================
# POKEMON BATTLE ARENA - Launcher Linux
# ==========================================

clear

echo ""
echo "  ========================================="
echo "        POKEMON BATTLE ARENA"
echo "  ========================================="
echo ""

# Verificar que Java está instalado
if ! command -v java &> /dev/null; then
    echo "  [ERROR] Java no está instalado."
    echo "  Instálalo con:"
    echo "    Ubuntu/Debian: sudo apt install openjdk-17-jre"
    echo "    Fedora:        sudo dnf install java-17-openjdk"
    echo "    Arch:          sudo pacman -S jre17-openjdk"
    echo ""
    read -p "  Presiona Enter para salir..."
    exit 1
fi

# Verificar la versión de Java
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "  [ADVERTENCIA] Se recomienda Java 17 o superior."
    echo "  Versión detectada: Java $JAVA_VERSION"
    echo ""
fi

# Buscar el JAR en varias ubicaciones
JAR_PATH=""

# Buscar en dist/
if [ -f "dist/PokemonBattleArena.jar" ]; then
    JAR_PATH="dist/PokemonBattleArena.jar"
fi

# Buscar en la raíz
if [ -f "PokemonBattleArena.jar" ]; then
    JAR_PATH="PokemonBattleArena.jar"
fi

# Si no se encontró
if [ -z "$JAR_PATH" ]; then
    echo "  [ERROR] No se encontró PokemonBattleArena.jar"
    echo "  Asegúrate de que el JAR esté en la misma carpeta que este script."
    echo ""
    read -p "  Presiona Enter para salir..."
    exit 1
fi

echo "  [INFO] JAR encontrado en: $JAR_PATH"
echo "  [INFO] Iniciando juego..."
echo ""

# Ejecutar el juego
java -jar "$JAR_PATH"

echo ""
echo "  ========================================="
echo "  ¡Gracias por jugar!"
echo "  ========================================="
echo ""
read -p "  Presiona Enter para salir..."