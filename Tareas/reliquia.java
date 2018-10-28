package juegoinfinitygame;

public class reliquia {

    reliquia() {
    }

    public int meditarExtra(int meditar) {
        int array[] = new int[100];
        probabilidad(array, 5);
        if (array[(int) (Math.random() * array.length)] == 1) {
            meditar += 1;
        }
        return meditar;
    }

    public int restaurarVida(int dado, int salud) {
        int array[] = new int[100];
        if (dado != 12) {
            probabilidad(array, 1);
        } else {
            probabilidad(array, 50);
        }
        if (array[(int) (Math.random() * array.length)] == 1) {
            salud = 15;
        }
        return salud;
    }

    private int[] probabilidad(int array[], int porcentajeProba) {
        int ocupados[] = new int[porcentajeProba];
        int random;
        boolean disponible;
        inicializar(ocupados);
        for (int i = 0; i < porcentajeProba; i++) {
            do {
                disponible = true;
                random = (int) (Math.random() * array.length);
                for (int j = 0; j < ocupados.length; j++) {
                    if (ocupados[j] == random) {
                        disponible = false;
                    }
                }
            } while (!disponible);
            array[random] = 1;
            ocupados[i] = random;
        }
        return array;
    }

    private static void inicializar(int array[]) {
        for (int i = 0; i < array.length; i++) {
            array[i] = -1;
        }
    }
}
