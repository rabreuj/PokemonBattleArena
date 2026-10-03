@echo off
chcp 65001 >nul
title Pokemon Battle Arena
cls

echo.
echo   =========================================
echo         POKEMON BATTLE ARENA
echo   =========================================
echo.

:: Verificar Java
java -version >nul 2>&1
if errorlevel 1 (
    echo   [ERROR] Java no esta instalado.
    echo   Descargalo desde: https://www.java.com
    echo.
    pause
    exit /b
)

:: Verificar que existe el JAR
if not exist "dist\PokemonBattleArena.jar" (
    echo   [ERROR] No se encontro: dist\PokemonBattleArena.jar
    echo.
    pause
    exit /b
)

:: Ejecutar el juego directamente
java -jar "dist\PokemonBattleArena.jar"

echo.
echo   =========================================
echo   Gracias por jugar!
echo   =========================================
echo.
pause