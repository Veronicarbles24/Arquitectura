import java.util.Scanner;

public class Usuario {
    
    private Scanner scanner;
    
    public Usuario() {
        scanner = new Scanner(System.in);
    }
    
    public int ingresarNumero() {
        return scanner.nextInt();
    }
    
    public String elegirNivel() {
        return scanner.next();
    }
    
    public boolean decidirContinuar() {
        String respuesta = scanner.next().toLowerCase();
        return respuesta.equals("si") || respuesta.equals("s") || respuesta.equals("yes");
    }
}
