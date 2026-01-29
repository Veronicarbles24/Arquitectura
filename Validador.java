public class Validador {
    
    public boolean esNumeroValido(String entrada, int min, int max) {
        try {
            int numero = Integer.parseInt(entrada);
            return numero >= min && numero <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean estaEnRango(int numero, int min, int max) {
        return numero >= min && numero <= max;
    }
}
