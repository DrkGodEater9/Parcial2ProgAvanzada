package Servidor.edu.progavud.parcial2.control;

import java.util.Random;

/**
 * Controlador específico del juego de memoria que maneja la lógica del tablero.
 * 
 * Esta clase se encarga de:
 * - Inicializar las posiciones de las cartas (20 parejas = 40 cartas)
 * - Crear la distribución de parejas (cada número del 0-19 aparece dos veces)
 * - Aleatorizar las posiciones usando el algoritmo Fisher-Yates
 * - Proporcionar acceso a las posiciones para otros componentes
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class ControlJuego {
    
    /** Referencia al controlador principal del servidor */
    private ControlPrincipalServidor cServidorS;
    
    /** 
     * Array que contiene las posiciones de las cartas del juego.
     * Estructura: 40 elementos representando 20 parejas (0-19, cada uno duplicado)
     * Ejemplo después de inicializar: [3, 7, 3, 15, 7, 1, 15, 1, ...]
     */
    private int[] posiciones;

    /**
     * Establece las posiciones iniciales del juego creando parejas ordenadas.
     * 
     * Algoritmo:
     * 1. Primera mitad del array: números secuenciales 0, 1, 2, ..., 19
     * 2. Segunda mitad del array: números en orden inverso 19, 18, 17, ..., 0
     * 3. Resultado: cada número del 0-19 aparece exactamente dos veces
     * 4. Llama automáticamente a setearRandomPosiciones() para aleatorizar
     * 
     * Ejemplo del array resultante antes de aleatorizar:
     * [0, 1, 2, ..., 19, 19, 18, 17, ..., 0]
     */
    public void setearPosicionesIniciales() {
        // Llenar primera mitad con números secuenciales (0 a 19)
        for(int i = 0; i < posiciones.length/2; i++) {
            posiciones[i] = i;
            // Llenar segunda mitad con los mismos números en orden inverso
            posiciones[posiciones.length - 1 - i] = i;
        }
        // Aleatorizar las posiciones una vez creadas las parejas
        setearRandomPosiciones();
    }
    
    /**
     * Aleatoriza las posiciones de las cartas usando el algoritmo Fisher-Yates.
     * 
     * Este algoritmo garantiza una distribución uniforme de todas las permutaciones
     * posibles, donde cada configuración tiene la misma probabilidad de ocurrir.
     * 
     * Complejidad temporal: O(n) donde n = 40
     * Complejidad espacial: O(1) - solo variables temporales
     * 
     * Algoritmo Fisher-Yates:
     * 1. Para cada posición i del array (0 a 39)
     * 2. Generar índice aleatorio j entre 0 y 39
     * 3. Intercambiar elementos en posiciones i y j
     * 4. Continuar hasta recorrer todo el array
     */
    public void setearRandomPosiciones() {
        // Crear generador de números aleatorios
        Random rand = new Random();
        
        // Aplicar algoritmo Fisher-Yates para aleatorización uniforme
        for (int i = 0; i < posiciones.length; i++) {
            // Generar índice aleatorio para intercambio
            int randomIndexToSwap = rand.nextInt(posiciones.length);
            
            // Realizar intercambio de elementos (swap)
            int temp = posiciones[randomIndexToSwap];
            posiciones[randomIndexToSwap] = posiciones[i];
            posiciones[i] = temp;
        }
    }
    
    /**
     * Obtiene el array completo de posiciones de las cartas.
     * 
     * @return Array de enteros con las posiciones actuales de todas las cartas.
     *         Cada elemento representa el número de la carta en esa posición del tablero.
     */
    public int[] getPosiciones() {
        return posiciones;
    }

    /**
     * Establece un nuevo array de posiciones para las cartas.
     * 
     * @param posiciones Nuevo array de posiciones a establecer.
     *                   Debe contener 40 elementos para representar el tablero completo.
     */
    public void setPosiciones(int[] posiciones) {
        this.posiciones = posiciones;
    }

    /**
     * Constructor de la clase ControlJuego.
     * 
     * Inicializa el controlador del juego con una referencia al servidor principal
     * y prepara el array de posiciones para 40 cartas (20 parejas).
     * 
     * @param cServidorS Referencia al controlador principal del servidor.
     *                   Permite comunicación con otros subsistemas.
     */
    public ControlJuego(ControlPrincipalServidor cServidorS) {
        // Establecer referencia al controlador principal
        this.cServidorS = cServidorS;
        
        // Inicializar array para 40 cartas (20 parejas)
        this.posiciones = new int[40];
    }
}