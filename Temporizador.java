public class Temporizador {
    private long inicio;
    private long fin;
    
    public void iniciar() {
        inicio = System.currentTimeMillis();
    }
    
    public void detener() {
        fin = System.currentTimeMillis();
    }
    
    public long obtenerTiempoTranscurridoSegundos() {
        return (fin - inicio) / 1000;
    }
}