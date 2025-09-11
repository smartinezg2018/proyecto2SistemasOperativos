import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

class Racer extends Robot implements Runnable {
    private Thread t;
    private AtomicInteger shared;
    private int number_beepers = 0;
    private int x,y;

    public Racer(int Street, int Avenue, Direction direction, int beeps, Color color, AtomicInteger ref) {
        super(Street, Avenue, direction, beeps, color);
        this.y = Street;
        this.x = Avenue;
        this.shared = ref;
        World.setupThread(this);
        t = new Thread(this);
    }
    
    private synchronized void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }
    public synchronized void largoAzul(){
        for (int i = 0; i < 4; i++) move();
        turnLeft();
        for (int i = 0; i < 10; i++) move();
        turnLeft();
        for (int i = 0; i < 3; i++) move();
        turnRight();
        for (int i = 0; i < 3; i++) move();
        turnRight();
        for (int i = 0; i < 8; i++) move();
        turnRight();
        for (int i = 0; i < 4; i++) move();
        turnRight();
        for (int i = 0; i < 3; i++) move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        for (int i = 0; i < 7; i++) move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnRight();
        for (int i = 0; i < 10; i++) move();
        turnLeft();
        for (int i = 0; i < 6; i++) move();
        turnLeft();
        move();
        turnLeft();
        move();
        turnRight();
        for (int i = 0; i < 6; i++) move();
        turnLeft();
        move();
        turnLeft();
        for (int i = 0; i < 6; i++) move();
        turnRight();

        for (int i = 0; i < 2; i++) move();
        turnRight();
        move();
        turnRight();
        move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        move();
        largoMorado();





    }



    public synchronized void largoMorado(){
        move();
        turnRight();
        for (int i = 0; i < 3; i++) move();
        turnRight();
        for (int i = 0; i < 8; i++) move();
        turnLeft();
        for (int i = 0; i < 2; i++) move();
        turnLeft();
        for (int i = 0; i < 4; i++) move();
        turnRight();
        for (int i = 0; i < 17; i++) move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        for (int i = 0; i < 9; i++) move();
        turnRight();
        for (int i = 0; i < 8; i++) move();
        turnRight();
        for (int i = 0; i < 2; i++) move();
        turnRight();
        // inicia recorrido zona verda
        for (int i = 0; i < 2; i++) move();
        turnLeft();
        for (int i = 0; i < 7; i++) move();
        turnLeft();
        move();
        turnLeft();
        for (int i = 0; i < 6; i++) move();
        turnRight();
        move();
        turnRight();
        for (int i = 0; i < 6; i++) move();
        turnLeft();
        move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        move();





        largoAzul();

        turnOff();
    }

    public synchronized void race() {
        if(y == 12){
            this.largoMorado();
            return;

        }
        this.largoAzul();

    }

    public void run() {
        race();
    }

    public void start() {
        t.start(); 
    }
}
