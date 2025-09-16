import kareltherobot.*; 
import java.awt.Color; 
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Semaphore;

public class MiPrimerRobot implements Directions { 
    public static void main(String[] args) { 

        World.readWorld("mundos/parte2.kwld"); 
        World.setVisible(true); 
        World.setDelay(17);

        Mapa map = new Mapa();


        for(int i = 0; i<20;i++){
            Racer second = new Racer(1, 7, East, map); // largoAzul
            Racer first = new Racer(12, 23, South, map); // largoMorado
            first.start();
            second.start();
        }

    }
} 

