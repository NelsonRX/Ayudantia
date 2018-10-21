package juegoinfinitygame;

import java.util.Scanner;

public class JuegoInfinityGame {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        String posicionesFinales[];
        int turno = 0;
        boolean salir = false;
        int cantidadJugadores = 0, largoTablero, opcion = 0;

        do {
            do {
                try {
                    System.out.println("Ingrese el largo del tablero");
                    largoTablero = leer.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Valor ingresado no válido");
                    leer.next();
                }
            } while (true);
        } while (largoTablero < 20);
        String tablero[] = rellenarTablero(largoTablero);

        System.out.println("\n");
        System.out.println("Bienvenido a Infinity Game");
        opcion = opcionMenu(1);
        System.out.println("opcion: " + opcion);
        switch (opcion) {
            case 1:
                System.out.println("ENTRANDO AL JUEGO...");
                do {
                    System.out.println("Ingrese cantidad de jugadores");
                    cantidadJugadores = opcion();
                } while (cantidadJugadores <= 0);
                jugador jugadores[] = new jugador[cantidadJugadores];
                for (int i = 0; i < jugadores.length; i++) {
                    System.out.println("Ingrese el nick del jugador " + (i + 1));
                    jugadores[i] = new jugador(leer.next());
                }
                mostrarJugadores(jugadores);
                mostrarTablero(tablero);
                posicionesFinales = new String[cantidadJugadores + 1];
                posicionesFinales[cantidadJugadores] = "Blanco";
                System.out.println("");
                do {
                    System.out.println("---------------N--------------");
                    opcion = opcionMenu(2);
                    if (opcion == 1) {
                        avanzarTablero(jugadores[turno], largoTablero);
                    } else {
                        opcion = opcionMenu(3);
                        if (opcion == 1) {

                            do {
                                System.out.println("Cuánto retrocederá?");
                                opcion = opcion();
                            } while (opcion <= 0 || opcion > 3);
                            opcion *= -1;
                            meditar(jugadores[turno], opcion, largoTablero);
                        } else if (opcion == 2) {
                            do {
                                System.out.println("Cuánto avanzará?");
                                opcion = opcion();
                            } while (opcion <= 0 || opcion > 3);
                            meditar(jugadores[turno], opcion, largoTablero);
                        }
                    }
                    System.out.println("-----------------------------");
                    mostrarJugadores(jugadores);
                    System.out.println("-----------------------------");
                    System.out.println("");
                    String a = tablero[jugadores[turno].getPosicion()];
                    System.out.println("valor de String " + a);
                    switch (a) {
                        case "P":
                            System.out.println("Partida");
                            jugadores[turno].setVida(15);
                            break;
                        case "b":
                            System.out.println("Blanco");
                            break;
                        case "p":
                            System.out.println("Portal");
                            caidaPortal(jugadores[turno], tablero);
                            System.out.println("-----------------------------");
                            mostrarJugadores(jugadores);
                            System.out.println("-----------------------------");
                            break;
                        case "s":
                            System.out.println("Salud");
                            int azar = (int) (Math.random());
                            int azar2 = (int) (Math.random() * 3) + 1;
                            if (azar == 0) {
                                jugadores[turno].setVida(corrigeSalud(jugadores[turno].getVida(), azar2));
                            }
                            if (azar == 1) {
                                jugadores[turno].setVida(corrigeSalud(jugadores[turno].getVida(), azar2 * -1));
                            }
                            break;
                        case "d":
                            System.out.println("Desafío");
                            caidaDesafio(largoTablero, jugadores[turno]);
                            System.out.println("-----------------------------");
                            mostrarJugadores(jugadores);
                            System.out.println("-----------------------------");
                            break;
                        case "F":
                            System.out.println("Final");
                            salir = true;
                            buscaGanador(jugadores, turno, posicionesFinales, jugadores[turno], salir);
                            break;
                    }

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

    public static String[] rellenarTablero(int largoTablero) {
        String array[] = new String[largoTablero];
        int arrayAuxiliar[] = new int[largoTablero];
        array[0] = "I";
        array[largoTablero - 1] = "F";
        for (int i = 1; i < arrayAuxiliar.length - 1; i++) {
            arrayAuxiliar[i] = (int) (Math.random() * 4);
            if (arrayAuxiliar[i] == 0) {
                array[i] = "b";
            }
            if (arrayAuxiliar[i] == 1) {
                array[i] = "p";
            }
            if (arrayAuxiliar[i] == 2) {
                array[i] = "s";
            }
            if (arrayAuxiliar[i] == 3) {
                array[i] = "d";
            }
        }
        return array;
    }

    public static int opcionMenu(int numeroMenu) {
        Scanner leer = new Scanner(System.in);
        int opcion;
        do {
            do {
                try {
                    menu(numeroMenu);
                    opcion = leer.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Valor ingresado no válido");
                    leer.next();
                }
            } while (true);
        } while (opcion != 1 && opcion != 2);
        return opcion;
    }

    public static int opcion() {
        Scanner leer = new Scanner(System.in);
        int cualquierVariable;
        do {
            try {
                cualquierVariable = leer.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Valor ingresado no válido");
                leer.next();
            }
        } while (true);
        return cualquierVariable;
    }

    private static void menu(int op) {
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
            case 3:
                System.out.println("");
                System.out.println("Elija una acción");
                System.out.println("1: Para retroceder");
                System.out.println("2: Para avanzar");
                break;
            case 4:
                //Sin menu
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }
    }

    public static void mostrarJugadores(jugador jugadores[]) {
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println(jugadores[i].getNick() + "\t" + jugadores[i].getVida() + "\t" + jugadores[i].getPosicion() + "\t" + jugadores[i].getOpcionesMeditar() + "\t");
        }
    }

    public static void mostrarTablero(String array[]) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
    }

    public static jugador meditar(jugador objJugador, int eleccion, int largoTablero) {
        if (objJugador.getOpcionesMeditar() == 0) {
            if (objJugador.getOpcionesMeditar() <= 0) {
                objJugador.setOpcionesMeditar(0);
            }
            objJugador.setVida(corrigeSalud(objJugador.getVida(), -1));

        } else {
            objJugador.setVida(corrigeSalud(objJugador.getVida(), 1));
            objJugador.setPosicion(corrigePosicion(eleccion, largoTablero, objJugador.getPosicion()));
            objJugador.setOpcionesMeditar(objJugador.getOpcionesMeditar() - 1);
        }
        return objJugador;
    }

    public static jugador caidaPortal(jugador jugador, String tablero[]) {
        int cantidadPortal = 0;
        int portalAzar;
        for (int i = jugador.getPosicion() + 1; i < tablero.length - 1; i++) {
            if (tablero[i].equals("p")) {
                cantidadPortal++;
            }
        }
        if (cantidadPortal == 0) {
            for (int i = 1; i < jugador.getPosicion(); i++) {
                if (tablero[i].equals("p")) {
                    cantidadPortal++;
                }
            }
            if (cantidadPortal == 0) {
                jugador.setPosicion(corrigePosicion(1, tablero.length, jugador.getPosicion()));
            }
            portalAzar = (int) (Math.random() * cantidadPortal - 1);
            cantidadPortal = -1;
            for (int i = 1; i < tablero.length - 1; i++) {
                if (tablero[i].equals("p")) {
                    cantidadPortal++;
                }
                if (cantidadPortal == portalAzar) {
                    jugador.setPosicion(i);
                    break;
                }
            }
        } else {
            portalAzar = (int) (Math.random() * cantidadPortal - 1);
            cantidadPortal = -1;
            for (int i = jugador.getPosicion() + 1; i < tablero.length - 1; i++) {
                if (tablero[i].equals("p")) {
                    cantidadPortal++;
                }
                if (cantidadPortal == portalAzar) {
                    jugador.setPosicion(i);
                    break;
                }
            }
        }
        return jugador;
    }

    public static int corrigePosicion(int aumentoPosicion, int largoTablero, int posicionInicial) {
        int posicionValida;
        posicionValida = (aumentoPosicion + posicionInicial) - (largoTablero * ((aumentoPosicion + posicionInicial) / largoTablero));
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

    public static jugador avanzarTablero(jugador jugador, int largoTablero) {
        jugador.setPosicion(corrigePosicion(lanzarDado(), largoTablero, jugador.getPosicion()));
        return jugador;
    }

    private static int lanzarDado() {
        int dado1 = (int) (Math.random() * 6) + 1;
        int dado2 = (int) (Math.random() * 6) + 1;
        System.out.println("valor dado: " + (dado1 + dado2));
        return dado1 + dado2;
    }

    public static jugador caidaDesafio(int largoTablero, jugador jugador) {
        int desafio = (int) (Math.random() * 4);
        int azar = (int) (Math.random() * 5) + 1;
        int azar2 = (int) (Math.random() * 4) + 1;
        if (desafio == 0) {
            System.out.println("Avanza");
            jugador.setPosicion(corrigePosicion(azar, largoTablero, jugador.getPosicion()));
        }
        if (desafio == 1) {
            System.out.println("Retrocede");
            jugador.setPosicion(corrigePosicion(azar * -1, largoTablero, jugador.getPosicion()));
        }
        if (desafio == 2) {
            System.out.println("Aumenta la vida");
            jugador.setVida(corrigeSalud(jugador.getVida(), azar2));
        }
        if (desafio == 3) {
            System.out.println("Disminuye la vida");
            jugador.setVida(corrigeSalud(jugador.getVida(), azar2 * -1));
        }
        return jugador;
    }

    private static jugador[] ganadorPorCasillaFinal(jugador jugadores[], jugador jugador, int turno, boolean salir) {
        if (salir) {
            jugador auxiliar;
            auxiliar = jugadores[0];
            jugadores[0] = jugador;
            jugadores[turno] = auxiliar;
        }

        return jugadores;
    }

    public static jugador[] buscaGanador(jugador jugadores[], int turno, String posicionesFinales[], jugador jugador, boolean salir) {
        ganadorPorCasillaFinal(jugadores, jugador, turno, salir);
        if (jugador.getVida() == 0) {
            muertosPorVida(posicionesFinales, jugador);
        }
        return jugadores;
    }

    private static String[] muertosPorVida(String posicionesFinales[], jugador jugador) {
        if (jugador.getVida() == 0) {
            for (int i = 0; i < posicionesFinales.length; i++) {
                if (posicionesFinales[i].length() > 0) {
                    posicionesFinales[i - 1] = jugador.getNick();
                }
                break;
            }
        }
        return posicionesFinales;
    }

    private static jugador[] ordenarSegunVidas(jugador jugadores[]) {
        jugador aux;
        for (int i = 0; i < jugadores.length; i++) {
            for (int j = 0; j < jugadores.length - 1; j++) {
                if (jugadores[j].getVida() < jugadores[j + 1].getVida()) {
                    aux = jugadores[j];
                    jugadores[j] = jugadores[j + 1];
                    jugadores[j + 1] = aux;
                }
            }
        }
        return jugadores;
    }

    private static jugador[] ordenarSegunPosiciones(jugador jugadores[]) {
        jugador aux;
        for (int i = 0; i < jugadores.length; i++) {
            for (int j = 0; j < jugadores.length - 1; j++) {
                if (jugadores[j].getPosicion() < jugadores[j + 1].getPosicion()) {
                    aux = jugadores[j];
                    jugadores[j] = jugadores[j + 1];
                    jugadores[j + 1] = aux;
                }
            }
        }
        return jugadores;
    }

    public static jugador[] tablaPosiciones(jugador jugadores[]) {
        int puntoViejo = 1;
        int puntoNuevo;
        ordenarSegunVidas(jugadores);
        for (int i = 0; i < cantidadIteraciones(jugadores); i++) {
            puntoNuevo = buscaRangos(puntoViejo, jugadores);
            ordenarSegunPosiciones(jugadores);
            puntoViejo = puntoNuevo;
        }
        return jugadores;
    }

    private static int buscaRangos(int puntoViejo, jugador jugadores[]) {
        int rango = 0;
        for (int i = puntoViejo; i < jugadores.length - 1; i++) {
            if (jugadores[puntoViejo].getVida() != jugadores[i + 1].getVida()) {
                rango = i + 1;
                break;
            } else {
                rango = jugadores.length;
            }
        }
        return rango;
    }

    private static int cantidadIteraciones(jugador jugadores[]) {
        int cont = 0;
        int arreglo[] = new int[jugadores.length];
        for (int i = 0; i < jugadores.length; i++) {
            arreglo[i] = jugadores[i].getVida();
        }
        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo.length; j++) {
                if (arreglo[i] == arreglo[j] && j != i) {
                    arreglo[i] = 0;
                }
            }
        }
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] != 0) {
                cont += 1;
            }
        }
        return cont;
    }
}
