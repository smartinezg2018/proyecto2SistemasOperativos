import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

class Racer extends Robot implements Runnable {
    private Thread t;
    private AtomicInteger shared;
    private int number_beepers = 0;

    // public Racer(int Street, int Avenue, Direction direction, int beeps) {
    //     super(Street, Avenue, direction, beeps);
    //     World.setupThread(this); 
    //     t = new Thread(this);
    // }

    public Racer(int Street, int Avenue, Direction direction, int beeps, Color color, AtomicInteger ref) {
        super(Street, Avenue, direction, beeps, color);
        this.shared = ref;
        World.setupThread(this);
        t = new Thread(this);
    }
    
    public synchronized void race() {

        
        // 1. Mover 4 pasos
        for (int i = 0; i < 4; i++) {
            move();
        }

        // 2. Recoger 5 beepers
        for (int i = 0; i < 5; i++) {
            // System.out.println(shared);
            int newValue = shared.decrementAndGet();
            if(newValue>=0){
                pickBeeper();
                number_beepers++;
            }

        }
    

        // 3. Girar a la izquierda
        turnLeft();

        // 4. Mover 2 pasos
        for (int i = 0; i < 2; i++) {
            
            move();
        }
        
        // 5. Poner 5 beepers
        for (int i = 0; i < number_beepers; i++) {
            putBeeper();
        }
        
        // 6. Mover 1 paso
        move();
        
        // 7. Apagar
        turnOff();
    }

    public void run() {
        race();
    }

    public void start() {
        t.start(); // arranca el hilo
    }
}
