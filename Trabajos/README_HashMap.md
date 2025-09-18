# Implementación de HashMap para el Mapa de Karel

## Archivos creados:

1. **MapaHashMap.java** - Nueva implementación del mapa usando HashMap
2. **RacerHashMap.java** - Clase Racer adaptada para usar MapaHashMap
3. **MiPrimerRobotHashMap.java** - Versión del programa principal que usa HashMap
4. **TestMapaHashMap.java** - Pruebas unitarias de la implementación
5. **TestCompletoHashMap.java** - Prueba completa con simulación de robots

## Características de la implementación:

### Ventajas del HashMap:
- **Memoria eficiente**: Solo almacena posiciones que han sido modificadas
- **Misma interfaz**: Mantiene los mismos métodos que la implementación original
- **Compatibilidad**: Funciona con el código existente sin cambios

### Funcionamiento:
- **Valor por defecto**: Todas las posiciones tienen valor 1 por defecto
- **Clave compuesta**: Usa formato "y,x" para las coordenadas
- **Optimización**: No almacena valores por defecto en el HashMap
- **Thread-safe**: Mantiene la sincronización con Semaphore

## Cómo usar:

### Compilar:
```bash
javac -cp ".;KarelJRobot.jar" MapaHashMap.java RacerHashMap.java MiPrimerRobotHashMap.java
```

### Ejecutar:
```bash
java -cp ".;KarelJRobot.jar" MiPrimerRobotHashMap
```

### Pruebas:
```bash
# Prueba unitaria
java TestMapaHashMap

# Prueba completa con simulación
java -cp ".;KarelJRobot.jar" TestCompletoHashMap
```

## Comparación con la implementación original:

| Aspecto | Matriz Original | HashMap |
|---------|----------------|---------|
| Memoria | O(n*m) siempre | O(posiciones modificadas) |
| Acceso | O(1) directo | O(1) con hash |
| Inicialización | O(n*m) | O(1) |
| Flexibilidad | Tamaño fijo | Tamaño dinámico |
| Complejidad | Simple | Media |

## Métodos disponibles:

- `createRobot(int[] coor)` - Crea un robot en la posición especificada
- `reviewLocation(int[] coor)` - Verifica si se puede colocar un robot
- `move(int[] coor, int[] mov)` - Mueve un robot de una posición a otra
- `print()` - Imprime el estado actual del mapa
- `printHashMapState()` - Muestra el contenido interno del HashMap

La implementación mantiene exactamente el mismo comportamiento que la versión original, pero con las ventajas de eficiencia de memoria del HashMap.
