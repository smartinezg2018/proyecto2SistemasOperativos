import kareltherobot.*;
import java.awt.Color;

class RacerHashMap extends Robot implements Runnable {
    private Thread t;
    private int [] coor = new int[2];
    // El mapa debe ser una variable de instancia, no estática, para que cada robot
    // se refiera al mapa con el que fue creado.
    private final MapaHashMap map;

    public RacerHashMap(int Street, int Avenue, Direction direction, MapaHashMap map) {
        super(Street, Avenue, direction, 0, Color.BLUE);
        this.map = map;
        coor[0] = Street;
        coor[1] = Avenue;
        World.setupThread(this);
        t = new Thread(this);
        
        // El robot intenta ocupar su posición inicial. Si está ocupada, espera y reintenta.
        while (!map.createRobot(coor)) {
            try {
                Thread.sleep(50); // Espera 50ms antes de volver a intentar
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


    public void move(){
        int[] mov = getCurrentDirection(); 

        // El robot intenta moverse. Si la celda de destino está ocupada, espera y reintenta.
        while (!map.tryMove(coor, mov)) {
            try {
                Thread.sleep(20); // Pequeña pausa para no saturar la CPU (evita busy-waiting)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Si el hilo es interrumpido, se detiene.
            }
        }

        // Si llegamos aquí, el movimiento fue exitoso en la lógica del mapa.
        coor[0] += mov[0];
        coor[1] += mov[1];
        super.move(); // Ahora se mueve el robot visual.
    }
    

    private synchronized void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    public synchronized void zonaVerde() { // Se espera que el robot esté en 12, 30
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
        move(); // Acaba en 12, 23
    }

    public synchronized void zonaAzul() { // Se espera que el robot esté en 2, 8
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
        for (int i = 0; i < 6; i++) move(); // Acaba en 1,7
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

    public synchronized void rapidoAzul() { // Se espera que el robot esté en 1, 7
        for (int i = 0; i < 4; i++) move(); // Sale
        // Condición para saber por cuál camino sigue
        for (int i = 0; i < 4; i++) move(); // Entra al camino rápido
        // Condición para ver si el robot puede seguir avanzando
        for (int i = 0; i < 6; i++) move(); // Atraviesa el pare y siga
        for (int i = 0; i < 4; i++) move(); // Entra en la bahía
        // Condición para ver si el robot puede seguir avanzando
        for (int i = 0; i < 5; i++) move(); // Llega a la esquina
        turnLeft();
        for (int i = 0; i < 3; i++) move();
        // Condición para ver si el robot puede seguir avanzando
        for (int i = 0; i < 8; i++) move(); // Llega a zona verde

        // inicia recorrido zona verde
        zonaVerde();

        turnOff();
    }

    public synchronized void largoMorado() { // Se espera que el robot esté en 12, 23
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
        for (int i = 0; i < 2; i++) move(); // termina en 2,8

        // inicia recorrido zona azul
        zonaAzul();

        largoAzul();

        turnOff();
    }

    public synchronized void rapidoVerde() {
        // Condición para saber por cuál camino sigue
        for (int i = 0; i < 2; i++) move(); // Entra camino rápido
        turnLeft();
        for (int i = 0; i < 7; i++) move(); // Va hasta 30
        turnRight();
        for (int i = 0; i < 5; i++) move(); // Al frente de bahía 1
        turnRight();
        move();
        turnLeft();
        for (int i = 0; i < 4; i++) move(); // Entra segundo en pare y siga
        turnRight();
        for (int i = 0; i < 3; i++) move(); // Al frente de bahía 2
        turnRight();
        move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        move();
        turnRight();
        for (int i = 0; i < 5; i++) move(); // Al frente de bahía 3
        turnRight();
        move();
        turnLeft();
        for (int i = 0; i < 8; i++) move(); // Llega a zona azul
        zonaAzul();

        turnOff();
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
