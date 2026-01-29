/**
 * Clase Validador
 * 
 * Esta clase se encarga de verificar que los datos ingresados
 * por el usuario sean correctos antes de ser utilizados en el juego.
 * Ayuda a evitar errores y entradas inválidas.
 */
public class Validador {

    /**
     * Verifica si una entrada de texto corresponde a un número válido
     * y si se encuentra dentro de un rango permitido.
     */
    public boolean esNumeroValido(String entrada, int min, int max) {
        try {
            int numero = Integer.parseInt(entrada);
            return numero >= min && numero <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica si un número entero se encuentra dentro de un rango específico.
     */
    public boolean estaEnRango(int numero, int min, int max) {
        return numero >= min && numero <= max;
    }
}
