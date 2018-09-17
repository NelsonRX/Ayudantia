package infinitygame;

import java.util.Scanner;

public class InfinityGame {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int largo;
        int cantidadJugadores;
        int turno = 0;
        String jugadores[][];
        String posicionesFinales[];

        do {
            System.out.println("Ingrese el largo del tablero");
            largo = leer.nextInt();
        } while (largo < 20);
        char tablero[] = rellenarTablero(largo);
        System.out.println("\n");

        switch (opcionMenu(leer)) {
            case 1:
                System.out.println("HA INGRESADO AL MODO DE JUEGO SOLITARIO");
                jugadores = new String[1][3];
                rellenar(jugadores);
                System.out.println("Ingrese el Nick del Jugador");
                agregarJugadorNick(jugadores, leer);
                mostrarJugadores(jugadores);
                mostrarTablero(tablero);

                System.out.println("");
                do {
                    System.out.println("-------------------- Nuevo movimiento -------------------------");
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
                            //No es necesario en MODO SOLITARIO
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

                } while (caidaFinal(jugadores, tablero, turno) == false || jugadores[0][1].equals("0"));

                break;
            case 2:
                System.out.println("HA INGRESADO AL MODO DE JUEGO MULTIJUGADOR");
                do {
                    System.out.println("Ingrese la cantidad de JUGADORES");
                    cantidadJugadores = leer.nextInt();
                } while (cantidadJugadores <= 0);
                jugadores = new String[cantidadJugadores][3];
                rellenar(jugadores);
                System.out.println("Ingrese el Nick del Jugador");
                agregarJugadorNick(jugadores, leer);
                mostrarJugadores(jugadores);
                mostrarTablero(tablero);
                posicionesFinales = new String[cantidadJugadores + 1];
                rellenarVacio(posicionesFinales);
                posicionesFinales[cantidadJugadores] = "Blanco";
                System.out.println("");
                
                    do {
                        System.out.println("-------------------- Nuevo turno -------------------------");
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
                                caidaBlanco(turno, cantidadJugadores);
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
                        buscaGanador(jugadores, largo, turno, posicionesFinales, tablero);
                        turno++;
                        if (turno > cantidadJugadores - 1) {
                            turno = 0;
                        }
                        System.out.println("********** resultados ************");
                        System.out.println("");
                        for (int i = 0; i < posicionesFinales.length; i++) {
                            System.out.print("| "+ posicionesFinales[i]+" |"+"\t");
                        }
                        System.out.println("********** resultados ************");
                    } while (resultadosListos(posicionesFinales) == false);
                
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
                jugadores[turno][2] = String.valueOf(i);
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

    public static int caidaBlanco(int turno, int cantidadJugadores) {
        turno++;
        if (turno > cantidadJugadores - 1) {
            turno = 0;
        }
        return turno;
    }

    public static boolean caidaFinal(String jugadores[][], char tablero[], int turno) {
        boolean ganador = false;
        if (String.valueOf(tablero[Integer.parseInt(jugadores[turno][2])]).equals("F")) {
            ganador = true;
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

    public static String[] ganadorPorVida(String jugadores[][], String posicionesFinales[], int turno) {
        if (jugadores[turno][1].equals("0")) {
            for (int i = 0; i < posicionesFinales.length; i++) {
                if (posicionesFinales[i].length() > 0) {
                    posicionesFinales[i - 1] = jugadores[turno][0];
                }
                break;
            }
        }
        return posicionesFinales;
    }

    public static String[] ganadorPorCasillaFinal(char tablero[], String jugadores[][], String posicionesFinales[], int turno) {
        if (caidaFinal(jugadores, tablero, turno)) { 
            for (int i = 0; i < jugadores.length; i++) {
                posicionesFinales[i] = jugadores[turno][0];
            }
        }
        return posicionesFinales;
    }

    public static String[] buscaGanador(String jugadores[][], int largo, int turno, String posiconesFinales[], char tablero[]) {
        if (jugadores[turno][2].equals(String.valueOf(largo-1))) {
            ganadorPorCasillaFinal(tablero, jugadores, posiconesFinales, turno);
        }
        if (jugadores[turno][1].equals("0")) {
            ganadorPorVida(jugadores, posiconesFinales, turno);
        }
        return posiconesFinales;
    }

    public static boolean resultadosListos(String posicionesFinales[]) {
        boolean resultado = true;
        for (int i = 0; i < posicionesFinales.length; i++) {
            if (posicionesFinales[i].length() == 0) {
                resultado = false;
            }
        }
        return resultado;
    }

    public static void rellenarVacio(String posicionesFinales[]) {
        for (int i = 0; i < posicionesFinales.length; i++) {
            posicionesFinales[i] = "";
        }
    }
}
