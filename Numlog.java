import java.util.Random;

/**
 * Clase Numlog
 * 
 * Esta clase representa el juego principal Numlog.
 * Aquí se controla la lógica del juego: selección de nivel,
 * generación del número secreto, validaciones, intentos,
 * tiempo de juego y resultados finales.
 */
public class Numlog {

    // Número que el usuario debe adivinar
    private int numeroSecreto;

    // Cantidad de intentos realizados
    private int intentos;

    // Objetos que apoyan el funcionamiento del juego
    private Temporizador temporizador;
    private Validador validador;
    private Mensajero mensajero;
    private Usuario usuario;

    // Nivel de dificultad seleccionado
    private Nivel nivelActual;

    /**
     * Constructor de la clase Numlog.
     * Inicializa los objetos necesarios para el juego.
     */
    public Numlog() {
        temporizador = new Temporizador();
        validador = new Validador();
        mensajero = new Mensajero();
        usuario = new Usuario();
    }

    /**
     * Define el nivel de dificultad según la opción elegida por el usuario.
     */
    public void seleccionarNivel(String nivel) {
        switch (nivel) {
            case "1":
                nivelActual = Nivel.PRINCIPIANTE;
                break;
            case "2":
                nivelActual = Nivel.INTERMEDIO;
                break;
            case "3":
                nivelActual = Nivel.AVANZADO;
                break;
            default:
                nivelActual = Nivel.PRINCIPIANTE;
        }
    }

    /**
     * Genera un número secreto aleatorio según el nivel seleccionado.
     */
    public void generarNumeroSecreto() {
        Random random = new Random();
        int max = obtenerRangoMaximo();
        numeroSecreto = random.nextInt(max) + 1;
    }

    /**
     * Obtiene el rango máximo permitido según el nivel actual.
     */
    private int obtenerRangoMaximo() {
        switch (nivelActual) {
            case PRINCIPIANTE:
                return 10;
            case INTERMEDIO:
                return 100;
            case AVANZADO:
                return 300;
            default:
                return 10;
        }
    }

    /**
     * Valida que el número ingresado esté dentro del rango permitido.
     */
    public boolean validarNumero(String numero) {
        int max = obtenerRangoMaximo();
        return validador.esNumeroValido(numero, 1, max);
    }

    /**
     * Compara el número ingresado por el usuario con el número secreto.
     */
    public String compararNumero(int numero) {
        if (numero < numeroSecreto) {
            return "mayor";
        } else if (numero > numeroSecreto) {
            return "menor";
        } else {
            return "correcto";
        }
    }

    /**
     * Calcula la eficiencia del jugador según los intentos realizados.
     */
    public String calcularEficiencia(long tiempoSegundos) {
        int max = obtenerRangoMaximo();
        double eficienciaIdeal = Math.log(max) / Math.log(2);

        Eficiencia nivel;
        if (intentos <= eficienciaIdeal * 1.5) {
            nivel = Eficiencia.AVANZADA;
        } else if (intentos <= eficienciaIdeal * 3) {
            nivel = Eficiencia.MEDIA;
        } else {
            nivel = Eficiencia.BAJA;
        }

        return nivel.toString();
    }

    /**
     * Inicia el temporizador y reinicia el contador de intentos.
     */
    public void iniciarTemporizador() {
        temporizador.iniciar();
        intentos = 0;
    }

    /**
     * Detiene el temporizador del juego.
     */
    public void detenerTemporizador() {
        temporizador.detener();
    }

    /**
     * Reinicia los valores principales del juego.
     */
    public void reiniciarJuego() {
        numeroSecreto = 0;
        intentos = 0;
    }

    /**
     * Método principal que controla el flujo completo del juego.
     */
    public void jugar() {

        // Mensaje inicial
        mensajero.mostrarBienvenida();

        boolean continuarJugando = true;

        while (continuarJugando) {

            // Selección de nivel
            mensajero.mostrarOpcionesNivel();
            String nivelSeleccionado = usuario.elegirNivel();
            seleccionarNivel(nivelSeleccionado);

            // Preparar juego
            generarNumeroSecreto();
            iniciarTemporizador();

            int max = obtenerRangoMaximo();
            System.out.println("\n¡Adivina el número entre 1 y " + max + "!");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

            boolean adivinado = false;

            // Ciclo de intentos
            while (!adivinado) {
                System.out.print("Ingresa tu número: ");

                try {
                    int numeroUsuario = usuario.ingresarNumero();
                    intentos++;

                    if (!validador.estaEnRango(numeroUsuario, 1, max)) {
                        mensajero.mostrarErrorEntrada();
                        intentos--;
                        continue;
                    }

                    String resultado = compararNumero(numeroUsuario);

                    if (resultado.equals("correcto")) {
                        detenerTemporizador();
                        long tiempoTranscurrido = temporizador.obtenerTiempoTranscurridoSegundos();

                        System.out.println("\nFelicidades! ¡Has adivinado el número secreto!");
                        System.out.println("\n Resultados:");
                        System.out.println("   • Número secreto: " + numeroSecreto);
                        System.out.println("   • Intentos: " + intentos);
                        System.out.println("   • Tiempo: " + tiempoTranscurrido + " segundos");

                        String eficiencia = calcularEficiencia(tiempoTranscurrido);
                        mensajero.mostrarEficiencia(eficiencia);

                        adivinado = true;
                    } else if (resultado.equals("mayor")) {
                        mensajero.mostrarPista("El número es MAYOR");
                    } else {
                        mensajero.mostrarPista("El número es MENOR");
                    }

                } catch (Exception e) {
                    mensajero.mostrarErrorEntrada();
                    usuario = new Usuario();
                }
            }

            // Decidir si continúa
            if (mensajero.preguntarContinuar()) {
                continuarJugando = usuario.decidirContinuar();
                if (continuarJugando) {
                    reiniciarJuego();
                    System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                }
            }
        }

        System.out.println("\n¡Gracias por jugar! ¡Hasta pronto!");
    }

    /**
     * Punto de entrada del programa.
     */
    public static void main(String[] args) {
        Numlog juego = new Numlog();
        juego.jugar();
    }
}
