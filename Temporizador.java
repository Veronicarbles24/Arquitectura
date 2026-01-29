/**
 * Clase Temporizador
 * 
 * Esta clase se encarga de medir el tiempo que dura una partida
 * del juego. Permite iniciar, detener y calcular el tiempo
 * transcurrido en segundos.
 */
public class Temporizador {

    // Momento en el que inicia el conteo
    private long inicio;

    // Momento en el que finaliza el conteo
    private long fin;

    /**
     * Inicia el temporizador registrando el tiempo actual.
     */
    public void iniciar() {
        inicio = System.currentTimeMillis();
    }

    /**
     * Detiene el temporizador registrando el tiempo final.
     */
    public void detener() {
        fin = System.currentTimeMillis();
    }

    /**
     * Calcula el tiempo total transcurrido entre el inicio
     * y el fin del juego, expresado en segundos.
     * 
     * @return tiempo transcurrido en segundos
     */
    public long obtenerTiempoTranscurridoSegundos() {
        return (fin - inicio) / 1000;
    }
}
