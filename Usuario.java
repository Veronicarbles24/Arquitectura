import java.util.Scanner;

/**
 * Clase Usuario
 * 
 * Esta clase representa la interacción directa del jugador
 * con el sistema. Se encarga de leer los datos que el usuario
 * ingresa por consola durante el juego.
 */
public class Usuario {

    // Scanner utilizado para leer la entrada del usuario
    private Scanner scanner;

    /**
     * Constructor de la clase Usuario.
     * Inicializa el Scanner para permitir la lectura
     * de datos desde la consola.
     */
    public Usuario() {
        scanner = new Scanner(System.in);
    }

    /**
     * Permite al usuario ingresar un número desde el teclado.
     * Se utiliza principalmente para adivinar el número secreto.
     */
    public int ingresarNumero() {
        return scanner.nextInt();
    }

    /**
     * Permite al usuario seleccionar el nivel de dificultad
     * del juego.
     */
    public String elegirNivel() {
        return scanner.next();
    }

    /**
     * Permite al usuario decidir si desea continuar jugando
     * una nueva partida.
     * 
     * @return true si el usuario responde "si", "s" o "yes",
     *         false en cualquier otro caso.
     */
    public boolean decidirContinuar() {
        String respuesta = scanner.next().toLowerCase();
        return respuesta.equals("si") || respuesta.equals("s") || respuesta.equals("yes");
    }
}
