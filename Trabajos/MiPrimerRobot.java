import kareltherobot.*; 
import java.awt.Color; 
import java.util.concurrent.atomic.AtomicInteger;

public class MiPrimerRobot implements Directions { 
    public static void main(String[] args) { 

        AtomicInteger ref = new AtomicInteger(5);

        World.readWorld("mundos/parte2.kwld"); 
        World.setVisible(true); 
        World.setDelay(3);

        Mapa map = new Mapa();


        Racer second = new Racer(1, 7, East, map); // largoAzul
        
        Racer first = new Racer(12, 23, South, map); // largoMorado



        first.start();
        second.start();
    }
} 

