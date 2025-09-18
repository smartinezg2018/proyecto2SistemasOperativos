@echo off
echo Ejecutando pruebas para la implementacion con HashMap...
echo.
echo =================================
echo      Prueba Unitaria (TestMapaHashMap)
echo =================================
java -cp ".;KarelJRobot.jar" TestMapaHashMap
echo.
echo =================================
echo      Prueba Completa (TestCompletoHashMap)
echo =================================
java -cp ".;KarelJRobot.jar" TestCompletoHashMap
echo.
echo Pruebas finalizadas.
pause