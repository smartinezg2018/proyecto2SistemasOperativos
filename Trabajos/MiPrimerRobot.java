import kareltherobot.*; 
import java.awt.Color; 
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Semaphore;
// import java.util.time;

public class MiPrimerRobot implements Directions { 
    public static void main(String[] args) { 

        World.readWorld("mundos/parte2.kwld"); 
        World.setVisible(true); 
        World.setDelay(45);

        // Usar la nueva implementación con HashMap
        Mapa map = new Mapa();

        System.out.println("=== Iniciando simulación con HashMap ===");
        System.out.println("Mapa inicial:");
        // ();
        
        System.out.println("\nEstado inicial del HashMap:");
        // HashMapState();

        for(int i = 0; i<10;i++){
            Racer second = new Racer(1, 7, East, map); // largoAzul
            second.start();
            // Thread.sleep(2000);
            Racer first = new Racer(12, 23, South, map); // largoMorado
            first.start();
        }

    }
} 
