package infinitygame;

import java.util.Scanner;

public class InfinityGame {

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
        System.out.println("\n");

        switch (opcionMenu(leer)) {
            case 1:
                System.out.println("HA INGRESADO AL MODO DE JUEGO SOLITARIO");
                do {
                    System.out.println("Ingrese la cantidad de JUGADORES");
                    cantidadJugadores = leer.nextInt();
                } while (cantidadJugadores <= 0);
                jugadores = new String[1][3];
                rellenar(jugadores);
                agregarJugadorNick(jugadores, leer);
                mostrarJugadores(jugadores);
                mostrarTablero(tablero);

                do {
                    System.out.println("arriba");
                    int n = lanzarDado();
                    System.out.println("Valor dado: " + n);

                    avanzarTablero(jugadores, largo, turno, n);
                    System.out.println("-----------------------------");
                    mostrarJugadores(jugadores);
                    System.out.println("-----------------------------");
                    System.out.println("valor posición: " + jugadores[turno][2]);
                    String a = String.valueOf(tablero[Integer.parseInt(jugadores[turno][2])]);
                    System.out.println("valor de String " + a);
                    switch (a) {
                        case "P":
                            System.out.println("Partida");
                            caidaPartida(jugadores, tablero, turno);
                            break;
                        case "b":
                            System.out.println("Blanco");
                            caidaBlanco(turno);
                            break;
                        case "p":
                            System.out.println("Portal");
                            caidaPortal(tablero, turno, jugadores);
                            System.out.println("-----------------------------");
                            mostrarJugadores(jugadores);
                            System.out.println("-----------------------------");
                            break;
                        case "s":
                            System.out.println("Salud");
                            int azar = (int) (Math.random());
                            if (azar == 0) {
                                masVida(jugadores, turno);
                            }
                            if (azar == 1) {
                                menosVida(jugadores, turno);
                            }
                            break;
                        case "d":
                            System.out.println("Desafío");
                            caidaDesafio(turno, largo, jugadores);
                            System.out.println("-----------------------------");
                            mostrarJugadores(jugadores);
                            System.out.println("-----------------------------");
                            break;
                        case "F":
                            System.out.println("Final");
                            caidaFinal(jugadores, tablero, turno);
                            break;

                    }

                } while (caidaFinal(jugadores, tablero, turno) == true || jugadores[turno][1].equals("0"));

            //break;
            case 2:
                System.out.println("HA INGRESADO AL MODO DE JUEGO MULTIJUGADOR");
                do {
                    System.out.println("Ingrese la cantidad de JUGADORES");
                    cantidadJugadores = leer.nextInt();
                } while (cantidadJugadores <= 0);
                jugadores = new String[cantidadJugadores][3];
                rellenar(jugadores);
                agregarJugadorNick(jugadores, leer);
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
        if ((Integer.parseInt(jugadores[turno][2]) + azar) > largoTablero - 1) {
            jugadores[turno][2] = String.valueOf((Integer.parseInt(jugadores[turno][2]) + azar) - largoTablero);
        } else {
            jugadores[turno][2] = String.valueOf(Integer.parseInt(jugadores[turno][2]) + azar);
        }
        return jugadores;
    }

    public static String[][] retrocede(String jugadores[][], int largoTablero, int turno) {
        int azar = (int) (Math.random() * 5) + 1;
        if ((Integer.parseInt(jugadores[turno][2]) - azar) < 0) {
            jugadores[turno][2] = String.valueOf((Integer.parseInt(jugadores[turno][2]) - azar) + largoTablero);
        } else {
            jugadores[turno][2] = String.valueOf(Integer.parseInt(jugadores[turno][2]) - azar);
        }
        return jugadores;
    }

    public static String[][] aumentaVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 4) + 1;
        if ((Integer.parseInt(jugadores[turno][1]) + azar) > 15) {
            jugadores[turno][1] = "15";
        } else {
            jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) + azar);
        }
        return jugadores;
    }

    public static String[][] disminuyeVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 4) + 1;
        if ((Integer.parseInt(jugadores[turno][1]) - azar) <= 0) {
            jugadores[turno][1] = "0";
        } else {
            jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) - azar);
        }
        return jugadores;
    }

    public static String[][] caidaPortal(char tablero[], int turno, String jugadores[][]) {
        int cantidadPortal = 0;
        int portalAzar;
        for (int i = Integer.parseInt(jugadores[turno][2]) + 1; i < tablero.length - 1; i++) {
            if (String.valueOf(tablero[i]).equals("p")) {
                cantidadPortal++;
            }
        }
        portalAzar = (int) (Math.random() * cantidadPortal);
        cantidadPortal = 0;
        for (int i = Integer.parseInt(jugadores[turno][2]) + 1; i < tablero.length - 1; i++) {
            if (String.valueOf(tablero[i]).equals("p")) {
                cantidadPortal++;
            }
            if (cantidadPortal == portalAzar) {
                jugadores[turno][2] = String.valueOf(i + 1);
            }
        }
        return jugadores;
    }

    public static String[][] masVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 3) + 1;
        if ((Integer.parseInt(jugadores[turno][1]) + azar) > 15) {
            jugadores[turno][1] = "15";
        } else {
            jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) + azar);
        }
        return jugadores;
    }

    public static String[][] menosVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 3) + 1;
        if ((Integer.parseInt(jugadores[turno][1]) - azar) <= 0) {
            jugadores[turno][1] = "0";
        } else {
            jugadores[turno][1] = String.valueOf(Integer.parseInt(jugadores[turno][1]) - azar);
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
                if (j == 0) {
                    jugadores[i][j] = "";
                }
                if (j == 1) {
                    jugadores[i][j] = "15";
                }
                if (j == 2) {
                    jugadores[i][j] = "0";
                }
            }
        }
    }

    public static String[][] caidaPartida(String jugadores[][], char tablero[], int turno) {
        if (String.valueOf(tablero[Integer.parseInt(jugadores[turno][2])]).equals("P")) {
            jugadores[turno][1] = "15";
        } else {

        }
        return jugadores;
    }

    public static int caidaBlanco(int turno) {
        turno++;
        return turno;
    }

    public static boolean caidaFinal(String jugadores[][], char tablero[], int turno) {
        boolean ganador = true;
        if (String.valueOf(tablero[Integer.parseInt(jugadores[turno][2])]).equals("F")) {
            ganador = false;
        }
        return ganador;
    }

    public static String[][] avanzarTablero(String jugadores[][], int largoTablero, int turno, int dado) {
        if ((Integer.parseInt(jugadores[turno][2]) + dado) > largoTablero - 1) {
            jugadores[turno][2] = String.valueOf((Integer.parseInt(jugadores[turno][2]) + dado) - largoTablero);
        } else {
            jugadores[turno][2] = String.valueOf(Integer.parseInt(jugadores[turno][2]) + dado);
        }
        return jugadores;
    }
}
