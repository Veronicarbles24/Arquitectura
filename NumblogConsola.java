import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// ==================== ENUMS ====================
enum ResultadoComparacion {
    MAYOR, MENOR, IGUAL
}

enum ResultadoJuego {
    GANADO, CONTINUAR, ERROR_RANGO
}

// ==================== CLASE RANGO ====================
class Rango {
    private final int valorInicial;
    private final int valorFinal;
    private final int AMPLITUD_MAXIMA = 20;

    public Rango(int inicio, int fin) {
        this.valorInicial = inicio;
        this.valorFinal = fin;
    }

    public boolean esValido() {
        return valorInicial < valorFinal && 
               getAmplitud() <= AMPLITUD_MAXIMA &&
               valorInicial >= 1 && valorFinal <= 100;
    }

    public int getAmplitud() {
        return valorFinal - valorInicial + 1;
    }

    public boolean contiene(int numero) {
        return numero >= valorInicial && numero <= valorFinal;
    }

    public int getValorInicial() { return valorInicial; }
    public int getValorFinal() { return valorFinal; }

    @Override
    public String toString() {
        return valorInicial + " al " + valorFinal;
    }
}

// ==================== CLASE NUMERO SECRETO ====================
class NumeroSecreto {
    private int valor;
    private Rango rango;

    public NumeroSecreto(Rango rango) {
        this.rango = rango;
        generarNumero();
    }

    public void generarNumero() {
        Random rand = new Random();
        this.valor = rand.nextInt(rango.getAmplitud()) + rango.getValorInicial();
    }

    public ResultadoComparacion compararCon(int numero) {
        if (numero == valor) return ResultadoComparacion.IGUAL;
        return numero < valor ? ResultadoComparacion.MENOR : ResultadoComparacion.MAYOR;
    }

    public int getValor() { return valor; }
    public boolean esIgual(int numero) { return numero == valor; }
}

// ==================== CLASE INTENTO ====================
class Intento {
    private int numeroIngresado;
    private int numeroSecreto;
    private ResultadoComparacion resultado;
    private LocalDateTime fechaHora;

    public Intento(int numero, int secreto, ResultadoComparacion resultado) {
        this.numeroIngresado = numero;
        this.numeroSecreto = secreto;
        this.resultado = resultado;
        this.fechaHora = LocalDateTime.now();
    }

    public int getNumeroIngresado() { return numeroIngresado; }
    public ResultadoComparacion getResultado() { return resultado; }
    public int getNumeroSecreto() { return numeroSecreto; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}

// ==================== CLASE VALIDADOR ====================
class Validador {
    public static boolean validarEntrada(String entrada) {
        return esNumero(entrada);
    }

    public static boolean esNumero(String entrada) {
        try {
            Integer.parseInt(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean esRangoValido(int inicio, int fin) {
        return inicio < fin && (fin - inicio + 1) <= 20 && inicio >= 1 && fin <= 100;
    }

    public static boolean validarNumeroEnRango(int numero, Rango rango) {
        return rango.contiene(numero);
    }
}

// ==================== CLASE JUEGO LOGICA ====================
class JuegoNumLogic {
    private Rango rangoActual;
    private NumeroSecreto numeroSecreto;
    private List<Intento> intentos;
    private int contadorIntentos;
    private boolean juegoActivo;

    public JuegoNumLogic() {
        intentos = new ArrayList<>();
        juegoActivo = false;
    }

    public void configurarRango(int inicio, int fin) {
        rangoActual = new Rango(inicio, fin);
        if (rangoActual.esValido()) {
            numeroSecreto = new NumeroSecreto(rangoActual);
            contadorIntentos = 0;
            juegoActivo = true;
            intentos.clear();
        }
    }

    public ResultadoJuego procesarIntento(int numero) {
        if (!rangoActual.contiene(numero)) {
            return ResultadoJuego.ERROR_RANGO;
        }

        contadorIntentos++;
        ResultadoComparacion resultado = numeroSecreto.compararCon(numero);
        intentos.add(new Intento(numero, numeroSecreto.getValor(), resultado));

        if (resultado == ResultadoComparacion.IGUAL) {
            juegoActivo = false;
            return ResultadoJuego.GANADO;
        }

        return ResultadoJuego.CONTINUAR;
    }

    public void finalizarJuego() {
        juegoActivo = false;
    }

    public void reiniciarPartida() {
        if (rangoActual != null) {
            numeroSecreto = new NumeroSecreto(rangoActual);
            contadorIntentos = 0;
            juegoActivo = true;
            intentos.clear();
        }
    }

    public int getContadorIntentos() { return contadorIntentos; }
    public boolean isJuegoActivo() { return juegoActivo; }
    public Rango getRangoActual() { return rangoActual; }
    public NumeroSecreto getNumeroSecreto() { return numeroSecreto; }
}

// ==================== CLASE CONSOLA ====================
public class NumblogConsola {
    private Scanner scanner;
    private JuegoNumLogic juego;

    public NumblogConsola() {
        scanner = new Scanner(System.in);
        juego = new JuegoNumLogic();
    }

    public void ejecutar() {
        mostrarMensaje("Bienvenido a Numblog");
        
        Rango rango = solicitarRango();
        juego.configurarRango(rango.getValorInicial(), rango.getValorFinal());
        
        int intentosMaximos = Integer.MAX_VALUE;
        
        while (juego.isJuegoActivo() && juego.getContadorIntentos() < intentosMaximos) {
            mostrarMensaje("Ingresa un número entre " + rango + ": ");
            String entrada = scanner.nextLine();
            
            if (!Validador.validarEntrada(entrada)) {
                mostrarError("Ingrese solo números");
                continue;
            }
            
            int numero = Integer.parseInt(entrada);
            
            if (!Validador.validarNumeroEnRango(numero, rango)) {
                mostrarError("El número debe estar entre " + rango);
                continue;
            }
            
            ResultadoJuego resultado = juego.procesarIntento(numero);
            
            switch (resultado) {
                case GANADO:
                    mostrarMensaje("¡Felicidades! Ganaste con " + juego.getContadorIntentos() + " intentos.");
                    juego.finalizarJuego();
                    break;
                case CONTINUAR:
                    ResultadoComparacion comparacion = juego.getNumeroSecreto().compararCon(numero);
                    if (comparacion == ResultadoComparacion.MENOR) {
                        mostrarMensaje("Pista: El número secreto es MAYOR");
                    } else {
                        mostrarMensaje("Pista: El número secreto es MENOR");
                    }
                    break;
                case ERROR_RANGO:
                    mostrarError("Número fuera de rango");
                    break;
            }
            
            if (juego.getContadorIntentos() >= intentosMaximos && juego.isJuegoActivo()) {
                mostrarMensaje("¡Agotaste tus " + intentosMaximos + " intentos! El número era: " + 
                             juego.getNumeroSecreto().getValor());
                juego.finalizarJuego();
            }
        }
        
        scanner.close();
    }

    private Rango solicitarRango() {
        while (true) {
            mostrarMensaje("Elige un rango del 1 al 100 cuya amplitud no supere 20 números, ejemplo del 1 al 20");
            
            mostrarMensaje("Ingresa el valor inicial: ");
            String inicioStr = scanner.nextLine();
            mostrarMensaje("Ingresa el valor final: ");
            String finStr = scanner.nextLine();
            
            if (!Validador.esNumero(inicioStr) || !Validador.esNumero(finStr)) {
                mostrarError("Debes ingresar números válidos");
                continue;
            }
            
            int inicio = Integer.parseInt(inicioStr);
            int fin = Integer.parseInt(finStr);
            
            if (Validador.esRangoValido(inicio, fin)) {
                return new Rango(inicio, fin);
            } else {
                mostrarError("Rango inválido. Asegúrate que: inicio < fin, amplitud ≤ 20, y esté entre 1 y 100");
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    private void mostrarError(String error) {
        System.out.println("Error: " + error);
    }

    public static void main(String[] args) {
        NumblogConsola consola = new NumblogConsola();
        consola.ejecutar();
    }
}