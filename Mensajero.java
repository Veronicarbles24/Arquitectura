/**
 * Clase Mensajero
 * 
 * Esta clase se encarga exclusivamente de mostrar mensajes por consola
 * para el juego Numlog. Centraliza toda la interacción textual con el usuario,
 * por ende solo imprime no cuenta con logica del juego.
 */
public class Mensajero {
    
    public void mostrarBienvenida() {
        System.out.println("\nBienvenido al juego Numlog!");
        System.out.println("Adivina el número secreto, recuerda que entre mayor sea el nivel, mayor será el rango de números.");
        System.out.println("¡Buena suerte!\n");
    }
    
    public void mostrarOpcionesNivel() {
        System.out.println("Selecciona el nivel de dificultad: ");
        System.out.println("1. Principiante (1-10)");
        System.out.println("2. Intermedio (1-100)");
        System.out.println("3. Avanzado (1-300)");
        System.out.print("Tu elección: ");
    }
    
    public void mostrarErrorEntrada() {
        System.out.println(" Error: Por favor, ingresa solo números válidos.");
    }
    
    public void mostrarPista(String pista) {
        System.out.println("Pista: " + pista);
    }
    
    public void mostrarResultado(String resultado) {
        System.out.println(resultado);
    }
    
    public void mostrarEficiencia(String eficiencia) {
        System.out.println(" Eficiencia: " + eficiencia);
    }
    
    public boolean preguntarContinuar() {
        System.out.print("\n¿Quieres jugar otra vez? (si/no): ");
        return true;
    }
}