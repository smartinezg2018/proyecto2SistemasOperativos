import kareltherobot.*;
import java.awt.Color;

class Racer extends Robot implements Runnable {
    private Thread t;
    private int [] coor = new int[2];
    private final Mapa map;
    String dir;

    private static final Object bridge1Lock = new Object();
    private static final Object bridge2Lock = new Object();
    private static final Object bridge3Lock = new Object();

    public Racer(int Street, int Avenue, Direction direction, Mapa map) {
        super(Street, Avenue, direction, 0, Color.BLUE);
        this.map = map;
        coor[0] = Street;
        coor[1] = Avenue;
        World.setupThread(this);
        t = new Thread(this);
        

        while (!map.createRobot(coor)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int[] getCurrentDirection() {
        if (facingNorth()) return new int[]{1, 0};
        else if (facingEast())  return new int[]{0, 1};
        else if (facingSouth()) return new int[]{-1, 0};
        else  return new int[]{0, -1};
    }

    public synchronized void move(){
        int[] mov = getCurrentDirection(); 

        while (!map.tryMove(coor, mov)) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

  
        coor[0] += mov[0];
        coor[1] += mov[1];
        
        super.move(); 
    }

    public void moveBridge1() {
        synchronized (bridge1Lock) {
            if ("r".equals(dir)) {
                for (int i = 0; i < 6; i++) move(); 
            } else {
                move();
                turnRight();
                for (int i = 0; i < 5; i++) move();
                turnRight();
                move();
            }
        }
    }

    public void moveBridge2() {
        synchronized (bridge2Lock) {
            if ("r".equals(dir)) {
                for (int i = 0; i < 5; i++) move(); 
            } else {
                move();
                turnRight();
                for (int i = 0; i < 3; i++) move();
                turnRight();
                move();
            }
        }
    }

    public void moveBridge3() {
        synchronized (bridge3Lock) {
            if ("r".equals(dir)) {
                for (int i = 0; i < 7; i++) move();
            } else {
                move();
                turnRight();
                for (int i = 0; i < 5; i++) move();
                turnRight();
                move();
            }
        }
    }

    private synchronized void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    public synchronized void zonaVerde() { // Robot expected at 12,30
        for(int i = 0; i<4;i++)putBeeper();

        for (int i = 0; i < 4; i++) move();
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
        move(); // Ends at 12,23
        
       

        rapidoVerde();
    }

    public synchronized void zonaAzul() { // Robot expected at 2,8
        for(int i = 0; i<4;i++)putBeeper();
        turnRight();
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
        for (int i = 0; i < 6; i++) move(); // Ends at 1,7
        for(int i = 0; i<4;i++)pickBeeper();
    }

    public synchronized void largoAzul() {
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
        for (int i = 0; i < 2; i++) move();
        zonaVerde();
        
        largoMorado();
    }
    
    public synchronized void rapidoAzul() { // Robot expected at 1,7
        // Pedir permiso al mapa para tomar 4 beepers de la estación azul
        if (!map.tryTakeBeepers("azul", 4)) {
            System.out.println("Robot en zona azul terminando: No hay más beepers.");
            return; // Termina el trabajo si no hay beepers
        }
        // Si se obtuvo permiso, recoger los beepers físicamente
        for (int i = 0; i < 4; i++) pickBeeper();

        if(map.inicioLlenoBajo()){ largoAzul();return;}
        dir = "r";
        for (int i = 0; i < 4; i++) move(); // exit
        for (int i = 0; i < 4; i++) move(); // enter fast lane

        moveBridge1();
        for (int i = 0; i < 4; i++) move(); 
        moveBridge2();

        turnLeft();
        for (int i = 0; i < 3; i++) move();

        moveBridge3();
        move();

        zonaVerde();

  
    }

    public synchronized void largoMorado() { // Robot expected at 12,23
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
        for (int i = 0; i < 2; i++) move(); // ends at 2,8

        zonaAzul();
        largoAzul();
  
    }

    public synchronized void rapidoVerde() {
        // Pedir permiso al mapa para tomar 4 beepers de la estación verde
        if (!map.tryTakeBeepers("verde", 4)) {
            System.out.println("Robot en zona verde terminando: No hay más beepers.");
            return; // Termina el trabajo si no hay beepers
        }
        // Si se obtuvo permiso, recoger los beepers físicamente
        for (int i = 0; i < 4; i++) pickBeeper();

        if(map.inicioLlenoArriba()) largoMorado();
        dir = "l";
        for (int i = 0; i < 2; i++) move(); 
        turnLeft();
        for (int i = 0; i < 6; i++) move(); 

        moveBridge3();
        turnLeft();
        for (int i = 0; i < 3; i++) move();

        moveBridge2();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();

        moveBridge1();
        turnLeft();
        for (int i = 0; i < 8; i++) move();

        zonaAzul();
        rapidoAzul();
    }

    public synchronized void race() {
        if (coor[0] == 12) {
            this.rapidoVerde();
            return;
        }
        this.rapidoAzul();
    }

    public void run() {
        race();
    }

    public void start() {
        t.start(); 
    }
}
