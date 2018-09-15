package infinitygame;

import java.util.Scanner;

public class Infinitygame {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int largo;
        int cantidadJugadores;
        int turno = 0;
        String jugadores[][];

        do {
            System.out.println("Ingrese el largo del tablero");
            largo = leer.nextInt();
        } while (largo < 20);
        char tablero[] = rellenarTablero(largo);

        mostrarTablero(tablero);
        System.out.println("\n");

        switch (opcionMenu(leer)) {
            case 1:
                System.out.println("HA INGRESADO AL MODO DE JUEGO SOLITARIO");
                System.out.println("Se obtuvo: " + lanzarDado() + " al lanzar los dados");
                break;
            case 2:
                System.out.println("HA INGRESADO AL MODO DE JUEGO MULTIJUGADOR");
                do {
                    System.out.println("Ingrese la cantidad de JUGADORES");
                    cantidadJugadores = leer.nextInt();
                } while (cantidadJugadores <= 0);
                jugadores = new String[cantidadJugadores][3];
                rellenar(jugadores);

                do {
                    agregarJugadorNick(jugadores, leer);
                } while (cantidadJugadores <= turno);
                mostrarJugadores(jugadores);

                caidaDesafio(turno, largo, jugadores);
                for (int i = 0; i < jugadores.length; i++) {
                    for (int j = 0; j < jugadores[0].length; j++) {
                        System.out.print(jugadores[i][j] + "\t");
                    }
                    System.out.println("");
                }
                System.out.println("Se obtuvo: " + lanzarDado() + " al lanzar los 2 dados");
                break;
            case 3:
                System.out.println("SALIENDO DEL JUEGO");
                break;
        }
    }

    public static int lanzarDado() {
        int numeroAzar = (int) (Math.random() * 12) + 1;
        return numeroAzar;
    }

    public static int opcionMenu(Scanner leer) {
        int opcion;
        menu();
        opcion = leer.nextInt();
        return opcion;
    }

    public static void menu() {
        System.out.println("Bienvenido a Infinity Game");
        System.out.println("");
        System.out.println("Elija un modo de juego");
        System.out.println("1: Modo Solitario");
        System.out.println("2: Modo Multijugador");
        System.out.println("3: Salir del juego");
    }

    public static char[] rellenarTablero(int largoTablero) {
        char array[] = new char[largoTablero];
        int arrayAuxiliar[] = new int[largoTablero];
        array[0] = 'I';
        array[largoTablero - 1] = 'F';
        for (int i = 1; i < arrayAuxiliar.length - 1; i++) {
            arrayAuxiliar[i] = (int) (Math.random() * 4);
            if (arrayAuxiliar[i] == 0) {
                array[i] = 'b';
            }
            if (arrayAuxiliar[i] == 1) {
                array[i] = 'p';
            }
            if (arrayAuxiliar[i] == 2) {
                array[i] = 's';
            }
            if (arrayAuxiliar[i] == 3) {
                array[i] = 'd';
            }
        }
        return array;
    }

    public static void mostrarTablero(char array[]) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
    }

    public static void mostrarJugadores(String array[][]) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public static String[][] caidaDesafio(int turno, int largoTablero, String jugadores[][]) {
        int desafio = (int) (Math.random() * 4);
        if (desafio == 0) {
            System.out.println("Avanza");
            avanza(jugadores, largoTablero, turno);
        }
        if (desafio == 1) {
            System.out.println("Retrocede");
            retrocede(jugadores, largoTablero, turno);
        }
        if (desafio == 2) {
            System.out.println("Aumenta la vida");
            aumentaVida(jugadores, turno);
        }
        if (desafio == 3) {
            System.out.println("Disminuye la vida");
            disminuyeVida(jugadores, turno);
        }
        return jugadores;
    }

    public static String[][] avanza(String jugadores[][], int largoTablero, int turno) {
        int azar = (int) (Math.random() * 5) + 1;
        jugadores[turno][2] = String.valueOf(Integer.parseInt(jugadores[turno][2]) + azar);
        if ((Integer.parseInt(jugadores[turno][2]) + azar) > largoTablero) {
            jugadores[turno][2] = String.valueOf(largoTablero);
        }
        return jugadores;
    }

    public static String[][] retrocede(String jugadores[][], int largoTablero, int turno) {
        int azar = (int) (Math.random() * 5) + 1;
        jugadores[turno][2] = String.valueOf(Integer.parseInt(jugadores[turno][2]) - azar);
        if ((Integer.parseInt(jugadores[turno][2]) - azar) < 1) {
            jugadores[turno][2] = "1";
        }
        return jugadores;
    }

    public static String[][] aumentaVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 4) + 1;
        jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) + azar);
        if ((Integer.parseInt(jugadores[turno][1]) + azar) > 15) {
            jugadores[turno][1] = "15";
        }
        return jugadores;
    }

    public static String[][] disminuyeVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 4) + 1;
        jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) - azar);
        if ((Integer.parseInt(jugadores[turno][1]) - azar) < 1) {
            jugadores[turno][1] = "1";
        }
        return jugadores;
    }

    public static String[][] caidaPortal(String tablero[], int turno, String jugadores[][]) {
        int cantidadPortal = 0;
        int portalAzar;
        for (int i = Integer.parseInt(jugadores[turno][2]) + 1; i < tablero.length - 1; i++) {
            if (tablero[i].equals('p')) {
                cantidadPortal++;
            }
        }
        portalAzar = (int) (Math.random() * cantidadPortal);
        cantidadPortal = 0;
        for (int i = Integer.parseInt(jugadores[turno][2]) + 1; i < tablero.length - 1; i++) {
            if (tablero[i].equals('p')) {
                cantidadPortal++;
            }
            if (cantidadPortal == portalAzar) {
                jugadores[turno][2] = tablero[i];
            }
        }
        return jugadores;
    }

    public static String[][] masVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 3) + 1;
        jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) + azar);
        if ((Integer.parseInt(jugadores[turno][1]) + azar) > 15) {
            jugadores[turno][1] = "15";
        }
        return jugadores;
    }

    public static String[][] menosVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 3) + 1;
        jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) - azar);
        if ((Integer.parseInt(jugadores[turno][1]) - azar) < 1) {
            jugadores[turno][1] = "1";
        }
        return jugadores;
    }

    public static String[][] agregarJugadorNick(String jugadores[][], Scanner leer) {
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i][0] = leer.next();
        }
        return jugadores;
    }

    public static void rellenar(String jugadores[][]) {
        for (int i = 0; i < jugadores.length; i++) {
            for (int j = 0; j < jugadores[0].length; j++) {
                jugadores[i][j] = "0";
            }
        }
    }
}
