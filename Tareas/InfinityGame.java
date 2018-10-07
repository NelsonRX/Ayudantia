package infinitygame;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InfinityGame {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        boolean salir = false;
        int largo;
        int cantidadJugadores;
        int turno = 0;
        int opcion;
        String jugadores[][];
        String posicionesFinales[];

        do {
            do {
                try {
                    System.out.println("Ingrese el largo del tablero");
                    largo = leer.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Valor ingresado no válido");
                    leer.next();
                }
            } while (true);
        } while (largo < 20);
        char tablero[] = rellenarTablero(largo);
        System.out.println("\n");
        System.out.println("Bienvenido a Infinity Game");
        do {
            do {
                try {
                    opcion = opcionMenu(leer, 1);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Valor ingresado no válido");
                    leer.next();
                }
            } while (true);
        } while (opcion != 1 && opcion != 2);
        switch (opcion) {
            case 1:
                System.out.println("ENTRANDO AL JUEGO...");
                do {
                    do {
                        try {
                            System.out.println("Ingrese la cantidad de JUGADORES");
                            cantidadJugadores = leer.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Valor ingresado no válido");
                            leer.next();
                        }
                    } while (true);
                } while (cantidadJugadores <= 0);
                jugadores = new String[cantidadJugadores][4];
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
                    System.out.println("---------------N--------------");
                    opcion = opcionMenu(leer, 2);
                    if (opcion == 1) {
                        int n = lanzarDado();
                        System.out.println("Valor dado: " + n);
                        avanzarTablero(jugadores, largo, turno, n);
                    } else {
                        System.out.println("1: Para retroceder");
                        System.out.println("2: Para avanzar");
                        opcion = leer.nextInt();
                        if (opcion == 1) {

                            do {
                                do {
                                    try {
                                        System.out.println("Cuánto retrocederá?");
                                        opcion = (leer.nextInt()) * -1;
                                        break;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Valor ingresado no válido");
                                        leer.next();
                                    }
                                } while (true);
                            } while (opcion < -3 && opcion > 3);

                            meditar(jugadores, turno, largo, opcion);
                        } else if (opcion == 2) {
                            do {
                                do {
                                    try {
                                        System.out.println("Cuánto avanzará?");
                                        opcion = leer.nextInt();
                                        break;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Valor ingresado no válido");
                                        leer.next();
                                    }
                                } while (true);
                            } while (opcion < -3 || opcion > 3);
                            meditar(jugadores, turno, largo, opcion);
                        }
                        int n = leer.nextInt();
                    }

                    System.out.println("-----------------------------");
                    mostrarJugadores(jugadores);
                    System.out.println("-----------------------------");
                    System.out.println("valor posición: " + jugadores[turno][2]);

                    System.out.println("");
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
                            salir = true;
                            caidaFinal(jugadores, tablero, turno);
                            break;
                    }
                    buscaGanador(jugadores, largo, turno, posicionesFinales, tablero);
                    turno++;
                    if (turno > cantidadJugadores - 1) {
                        turno = 0;
                    }
                    System.out.println("");

                } while (!salir);

                mostrarJugadores(jugadores);
                System.out.println("");
                tablaPosiciones(jugadores);
                mostrarJugadores(jugadores);
                break;
            case 2:
                System.out.println("SALIENDO DEL JUEGO");
                break;
        }
    }

    public static int lanzarDado() {
        int dado1 = (int) (Math.random() * 6) + 1;
        int dado2 = (int) (Math.random() * 6) + 1;
        return dado1 + dado2;
    }

    //los datos ingresados dentro de tres casillas a la redonda se validará direcamente en el metodo main
    public static String[][] meditar(String jugadores[][], int turno, int largoTablero, int eleccion) {
        if (Integer.parseInt(jugadores[turno][3]) <= 0) {
            jugadores[turno][1] = String.valueOf(corrigeSalud(Integer.parseInt(jugadores[turno][1]), -1));
        } else {
            jugadores[turno][1] = String.valueOf(corrigeSalud(Integer.parseInt(jugadores[turno][1]), 1));
            jugadores[turno][2] = String.valueOf(corrigePosicion(eleccion, largoTablero, Integer.parseInt(jugadores[turno][2])));
        }
        jugadores[turno][3] = String.valueOf(Integer.parseInt(jugadores[turno][3]) - 1);
        if (Integer.parseInt(jugadores[turno][3]) <= 0) {
            jugadores[turno][3] = "0";
        }
        return jugadores;
    }

    public static int corrigePosicion(int nuevaPosicion, int largoTablero, int posicionInicial) {
        int posicionValida;
        posicionValida = (nuevaPosicion + posicionInicial) - (largoTablero * ((nuevaPosicion + posicionInicial) / largoTablero));
        if (posicionValida < 0) {
            posicionValida += largoTablero;
        }
        return posicionValida;
    }

    public static int corrigeSalud(int saludActual, int aumentoSalud) {
        saludActual += aumentoSalud;
        if (saludActual < 0) {
            saludActual = 0;
        }
        if (saludActual > 15) {
            saludActual = 15;
        }
        return saludActual;
    }

    public static int opcionMenu(Scanner leer, int numeroMenu) {
        int opcion;
        menu(numeroMenu);
        opcion = leer.nextInt();
        return opcion;
    }

    public static void menu(int op) {
        switch (op) {
            case 1:
                System.out.println("");
                System.out.println("Elija un modo de juego");
                System.out.println("1: Jugar");
                System.out.println("2: Salir");
                break;
            case 2:
                System.out.println("");
                System.out.println("Elija una acción");
                System.out.println("1: Lanzar dados");
                System.out.println("2: Meditar");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }

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
        corrigePosicion(azar, largoTablero, Integer.parseInt(jugadores[turno][2]));
        return jugadores;
    }

    public static String[][] retrocede(String jugadores[][], int largoTablero, int turno) {
        int azar = ((int) (Math.random() * 5) + 1) * -1;
        jugadores[turno][2] = String.valueOf(corrigePosicion(azar, largoTablero, Integer.parseInt(jugadores[turno][2])));
        return jugadores;
    }

    public static String[][] aumentaVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 4) + 1;
        int cont = 0;
        do {
            if (cont != turno) {
                jugadores[cont][1] = String.valueOf(corrigeSalud(Integer.parseInt(jugadores[cont][1]), azar));
            }
            cont++;
        } while (cont < jugadores.length);
        return jugadores;
    }

    public static String[][] disminuyeVida(String jugadores[][], int turno) {
        int azar = ((int) (Math.random() * 4) + 1) * -1;
        int cont = 0;
        do {
            if (cont != turno) {
                jugadores[cont][1] = String.valueOf(corrigeSalud(Integer.parseInt(jugadores[cont][1]), azar));
            }
            cont++;
        } while (cont < jugadores.length);
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

        if (cantidadPortal == 0) {
            for (int i = 1; i < tablero.length - 1; i++) {
                if (String.valueOf(tablero[i]).equals("p")) {
                    cantidadPortal++;
                }
            }
            portalAzar = (int) (Math.random() * cantidadPortal - 1);
            cantidadPortal = -1;
            for (int i = 1; i < tablero.length - 1; i++) {
                if (String.valueOf(tablero[i]).equals("p")) {
                    cantidadPortal++;
                }
                if (cantidadPortal == portalAzar) {
                    jugadores[turno][2] = String.valueOf(i);
                    break;
                }
            }
        } else {
            portalAzar = (int) (Math.random() * cantidadPortal - 1);
            cantidadPortal = -1;
            for (int i = Integer.parseInt(jugadores[turno][2]) + 1; i < tablero.length - 1; i++) {
                if (String.valueOf(tablero[i]).equals("p")) {
                    cantidadPortal++;
                }
                if (cantidadPortal == portalAzar) {
                    jugadores[turno][2] = String.valueOf(i);
                    break;
                }
            }
        }
        return jugadores;
    }

    public static String[][] masVida(String jugadores[][], int turno) {
        int azar = (int) (Math.random() * 3) + 1;
        jugadores[turno][1] = String.valueOf(corrigeSalud(Integer.parseInt(jugadores[turno][1]), azar));
        return jugadores;
    }

    public static String[][] menosVida(String jugadores[][], int turno) {
        int azar = ((int) (Math.random() * 3) + 1) * -1;
        jugadores[turno][1] = String.valueOf(corrigeSalud(Integer.parseInt(jugadores[turno][1]), azar));
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
                if (j == 3) {
                    jugadores[i][j] = "5";
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
        jugadores[turno][2] = String.valueOf(corrigePosicion(dado, largoTablero, Integer.parseInt(jugadores[turno][2])));
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

    public static String[][] ganadorPorCasillaFinal(char tablero[], String jugadores[][], int turno) {
        String auxiliar[] = new String[3];
//        if (caidaFinal(jugadores, tablero, turno)) {
//            for (int i = 0; i < jugadores.length; i++) {
//                posicionesFinales[i] = jugadores[turno][0];
//            }
//        }

        if (caidaFinal(jugadores, tablero, turno)) {
            for (int i = 0; i < jugadores[0].length - 1; i++) {
                auxiliar[i] = jugadores[0][i];
                jugadores[0][i] = jugadores[turno][i];
                jugadores[turno][i] = auxiliar[i];
            }

//            for (int i = 0; i < jugadores.length; i++) {
//                posicionesFinales[i] = jugadores[turno][0];
//            }
        }
        return jugadores;
    }

    public static String[][] buscaGanador(String jugadores[][], int largo, int turno, String posiconesFinales[], char tablero[]) {
        if (jugadores[turno][2].equals(String.valueOf(largo - 1))) {
            ganadorPorCasillaFinal(tablero, jugadores, turno);
        }
        if (jugadores[turno][1].equals("0")) {
            ganadorPorVida(jugadores, posiconesFinales, turno);
        }
        return jugadores;
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

    public static void tablaPosiciones(String[][] jugadores) {
        int puntoViejo = 1;
        int puntoNuevo;
        ordenamientoPosiciones(jugadores, 1, 1, jugadores.length);
        for (int i = 0; i < cantidadIteraciones(jugadores); i++) {
            puntoNuevo = buscaRangos(jugadores, puntoViejo);
            ordenamientoPosiciones(jugadores, 2, puntoViejo, puntoNuevo);
            puntoViejo = puntoNuevo;
        }
    }

    public static String[][] ordenamientoPosiciones(String[][] jugadores, int columnaOrdenar, int puntoIncial, int puntoFinal) {
        String aux[] = new String[3];
        for (int i = puntoIncial; i < puntoFinal; i++) {
            for (int j = puntoIncial; j < puntoFinal - 1; j++) {
                if (Integer.parseInt(jugadores[j][columnaOrdenar]) < Integer.parseInt(jugadores[j + 1][columnaOrdenar])) {
                    for (int k = 0; k < jugadores[0].length - 1; k++) {
                        aux[k] = jugadores[j][k];
                        jugadores[j][k] = jugadores[j + 1][k];
                        jugadores[j + 1][k] = aux[k];
                    }
                }
            }
        }
        return jugadores;
    }

    public static int buscaRangos(String jugadores[][], int puntoViejo) {
        int rango = 0;
        for (int i = puntoViejo; i < jugadores.length - 1; i++) {
            if (Integer.parseInt(jugadores[puntoViejo][1]) != Integer.parseInt(jugadores[i + 1][1])) {
                rango = i + 1;
                break;
            } else {
                rango = jugadores.length;
            }
        }
        return rango;
    }

    public static int cantidadIteraciones(String jugadores[][]) {
        int cont = 0;
        String arreglo[] = new String[jugadores.length];
        for (int i = 0; i < jugadores.length; i++) {
            arreglo[i] = jugadores[i][1];
        }

        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo.length; j++) {
                if (arreglo[i].equals(arreglo[j]) && j != i) {
                    arreglo[i] = "0";
                }
            }
        }
        for (int i = 0; i < arreglo.length; i++) {
            if (!arreglo[i].equals("0")) {
                cont += 1;
            }
        }
        return cont;
    }
}
