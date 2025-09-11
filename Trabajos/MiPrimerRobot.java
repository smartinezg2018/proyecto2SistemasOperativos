import kareltherobot.*; 
import java.awt.Color; 
import java.util.concurrent.atomic.AtomicInteger;

public class MiPrimerRobot implements Directions { 
    public static void main(String[] args) { 

        AtomicInteger ref = new AtomicInteger(5);

        World.readWorld("mundos/parte2.kwld"); 
        World.setVisible(true); 
        // World.setSpeed(7);
        RacerAzul first = new RacerAzul(1, 7, East, 0,ref);
        // Racer second = new Racer(12, 30, South, 0, Color.BLUE,ref);

        first.start();
        // second.start();

    } 
}
