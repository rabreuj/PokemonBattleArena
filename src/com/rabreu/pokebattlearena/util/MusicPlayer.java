package com.rabreu.pokebattlearena.util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Gestiona la reproducción de música de fondo durante el juego.
 * Carga los archivos de audio desde dentro del JAR (recursos embebidos).
 */
public class MusicPlayer {
    private Clip clip;
    private boolean isPlaying;
    
    /**
     * Reproduce un archivo de música en bucle continuo.
     * El archivo debe estar en la carpeta assets/ dentro de src/.
     * 
     * @param fileName Solo el nombre del archivo (ej: "battle_music.wav")
     */
    public void play(String fileName) {
        stop(); // Detener la música anterior si existe
        
        InputStream rawStream = null;
        BufferedInputStream bufferedStream = null;
        AudioInputStream audioStream = null;
        
        try {
            // Cargar el archivo desde dentro del JAR
            rawStream = getClass().getResourceAsStream("/assets/" + fileName);
            
            if (rawStream == null) {
                System.out.println("  ⚠ Música no encontrada: " + fileName);
                return;
            }
            
            // BufferedInputStream es NECESARIO para que AudioSystem pueda leer el stream
            bufferedStream = new BufferedInputStream(rawStream);
            audioStream = AudioSystem.getAudioInputStream(bufferedStream);
            
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            isPlaying = true;
            
        } catch (UnsupportedAudioFileException e) {
            System.out.println("  ⚠ Formato de audio no soportado: " + fileName);
        } catch (IOException e) {
            System.out.println("  ⚠ Error al leer el archivo: " + fileName);
        } catch (LineUnavailableException e) {
            System.out.println("  ⚠ No se pudo acceder al dispositivo de audio");
        } catch (Exception e) {
            System.out.println("  ⚠ Error al reproducir música: " + e.getMessage());
        } finally {
            // Cerrar streams para liberar recursos
            closeQuietly(audioStream);
            closeQuietly(bufferedStream);
            closeQuietly(rawStream);
        }
    }
    
    /**
     * Detiene la música actual.
     */
    public void stop() {
        if (clip != null) {
            try {
                if (isPlaying) {
                    clip.stop();
                }
                clip.close();
            } catch (Exception e) {
                // Ignorar errores al cerrar
            }
            clip = null;
            isPlaying = false;
        }
    }
    
    /**
     * Cambia el volumen de la música.
     * 
     * @param volume Valor entre 0.0 (silencio) y 1.0 (máximo)
     */
    public void setVolume(float volume) {
        if (clip != null && volume >= 0.0f && volume <= 1.0f) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                // Convertir volumen lineal (0.0-1.0) a decibelios
                float dB = (float) (Math.log(Math.max(volume, 0.0001)) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            } catch (Exception e) {
                // Algunas plataformas no soportan control de volumen
            }
        }
    }
    
    /**
     * Devuelve true si hay música sonando actualmente.
     */
    public boolean isPlaying() {
        return isPlaying;
    }
    
    /**
     * Cierra un stream de forma segura, ignorando excepciones.
     */
    private void closeQuietly(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ignored) {
                // Ignorar
            }
        }
    }
}