import kareltherobot.*; 
import java.awt.Color; 
import java.util.concurrent.atomic.AtomicInteger;

public class MiPrimerRobot implements Directions { 
    public static void main(String[] args) { 

        AtomicInteger ref = new AtomicInteger(5);

        World.readWorld("Mundo.kwld"); 
        World.setVisible(true); 



        Racer first = new Racer(1, 1, East, 0, Color.RED,ref);
        Racer second = new Racer(1, 1, East, 0, Color.BLUE,ref);
        


        first.start();
        second.start();

    } 
}
