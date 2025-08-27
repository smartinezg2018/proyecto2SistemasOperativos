import kareltherobot.*; 
import java.awt.Color; 

public class MiPrimerRobot implements Directions { 
    public static void main(String[] args) { 

        World.readWorld("Mundo.kwld"); 
        World.setVisible(true); 


        Robot Karel = new Robot(1, 1, East, 0); 
        Robot Azul = new Robot(1, 1, East,0,Color.BLUE); 


        for (int i = 0; i < 4; i++) {
            Karel.move();
            Azul.move();
        }

        for (int i = 0; i < 5; i++) {
            Karel.pickBeeper();
        }


        Karel.turnLeft(); 
        Azul.turnLeft();


        for (int i = 0; i < 2; i++) {
            Karel.move();
            Azul.move();
        }


        for (int i = 0; i < 5; i++) {
            Karel.putBeeper();
        }


        Karel.move(); 
        Azul.move(); 


        Karel.turnOff(); 
        Azul.turnOff(); 
    } 
}
