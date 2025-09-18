import kareltherobot.*; 
import java.awt.Color; 
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Semaphore;

public class MiPrimerRobotHashMap implements Directions { 
    public static void main(String[] args) { 

        World.readWorld("mundos/parte2.kwld"); 
        World.setVisible(true); 
        World.setDelay(45);

        // Usar la nueva implementación con HashMap
        MapaHashMap map = new MapaHashMap();

        System.out.println("=== Iniciando simulación con HashMap ===");
        System.out.println("Mapa inicial:");
        map.print();
        
        System.out.println("\nEstado inicial del HashMap:");
        map.printHashMapState();

        for(int i = 0; i<20;i++){
            RacerHashMap second = new RacerHashMap(1, 7, East, map); // largoAzul
            RacerHashMap first = new RacerHashMap(12, 23, South, map); // largoMorado
            first.start();
            second.start();
        }

    }
} 
