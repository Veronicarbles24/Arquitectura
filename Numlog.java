import java.util.Random;

public class Numlog {
    private int numeroSecreto;
    private int intentos;
   
    
    private Temporizador temporizador;
    private Validador validador;
    private Mensajero mensajero;
    private Usuario usuario;
    private Nivel nivelActual;
    
    public Numlog() {
        temporizador = new Temporizador();
        validador = new Validador();
        mensajero = new Mensajero();
        usuario = new Usuario();
    }
    
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
    
    public void generarNumeroSecreto() {
        Random random = new Random();
        int max = obtenerRangoMaximo();
        numeroSecreto = random.nextInt(max) + 1;
    }
    
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
    
    public boolean validarNumero(String numero) {
        int max = obtenerRangoMaximo();
        return validador.esNumeroValido(numero, 1, max);
    }
    
    public String compararNumero(int numero) {
        if (numero < numeroSecreto) {
            return "mayor";
        } else if (numero > numeroSecreto) {
            return "menor";
        } else {
            return "correcto";
        }
    }
    
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
    
    public void iniciarTemporizador() {
        temporizador.iniciar();
        intentos = 0;
    }
    
    public void detenerTemporizador() {
        temporizador.detener();
    }
    
    public void reiniciarJuego() {
        numeroSecreto = 0;
        intentos = 0;
    }
    
    public void jugar() {
        // Mostrar bienvenida
        mensajero.mostrarBienvenida();
        
        boolean continuarJugando = true;
        
        while (continuarJugando) {
            // Configuración inicial
            mensajero.mostrarOpcionesNivel();
            String nivelSeleccionado = usuario.elegirNivel();
            seleccionarNivel(nivelSeleccionado);
            
            // Generar número secreto aleatorio
            generarNumeroSecreto();
            
            // Inicializar contador
            iniciarTemporizador();
            
            int max = obtenerRangoMaximo();
            System.out.println("\n¡Adivina el número entre 1 y " + max + "!");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            
            boolean adivinado = false;
            
            // Ciclo principal del juego
            while (!adivinado) {
                System.out.print("Ingresa tu número: ");
                
                try {
                    int numeroUsuario = usuario.ingresarNumero();
                    intentos++;
                    
                    // Validar entrada
                    if (!validador.estaEnRango(numeroUsuario, 1, max)) {
                        mensajero.mostrarErrorEntrada();
                        intentos--; // No contar intentos inválidos
                        continue;
                    }
                    
                    // Comparar número
                    String resultado = compararNumero(numeroUsuario);
                    
                    if (resultado.equals("correcto")) {
                        // Número adivinado - ganó
                        detenerTemporizador();
                        long tiempoTranscurrido = temporizador.obtenerTiempoTranscurridoSegundos();
                        
                        System.out.println("\nFelicidades! ¡Has adivinado el número secreto!");
                        System.out.println("\n Resultados:");
                        System.out.println("   • Número secreto: " + numeroSecreto);
                        System.out.println("   • Intentos: " + intentos);
                        System.out.println("   • Tiempo: " + tiempoTranscurrido + " segundos");
                        
                        // Calcular eficiencia
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
                    usuario = new Usuario(); // Reiniciar scanner si hay error
                }
            }
            
            // Preguntar si quiere continuar
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
    
    public static void main(String[] args) {
        Numlog juego = new Numlog();
        juego.jugar();
    }
}