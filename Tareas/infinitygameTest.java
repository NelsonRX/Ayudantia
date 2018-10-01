
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class infinitygameTest {
    
    @Before
    public void setUp() {
        
    }
    
    @Test
    public void testlanzarDado() {
        int cont = 0;
        do {
            int obtenido = infinitygame.InfinityGame.lanzarDado();
            assertTrue(obtenido >= 2 && obtenido <= 12);
            cont++;
        } while (cont != 100);
    }

    @Test
    public void testmasVida() {
        String jugadores[][] = {
            {"jug1", "15", "14"},
            {"jug2", "14", "18"},
            {"jug3", "8", "12"}
        };
        //para la primera fila, el jugador uno, la vida debe ser igual antes y después
        String antes = jugadores[0][1];
        infinitygame.InfinityGame.masVida(jugadores, 0);
        assertTrue(antes.equals(jugadores[0][1]));
        //para la segunda fila, el jugador dos, la vida debe cambiar y no deben ser iguales
        antes = jugadores[1][1];
        infinitygame.InfinityGame.masVida(jugadores, 1);
        assertTrue(!antes.equals(jugadores[1][1]) && jugadores[1][1].equals("15"));
    }

    @Test
    public void testmenosVida() {
        String jugadores[][] = {
            {"jug1", "0", "14"},
            {"jug2", "14", "18"},
            {"jug3", "8", "12"}
        };
        //para la primera fila, el jugador uno, la vida no debe ser negativa
        infinitygame.InfinityGame.menosVida(jugadores, 0);
        assertTrue(Integer.parseInt(jugadores[0][1]) == 0);
        //para la segunda fila, el jugador dos, la vida antes debe ser mayor que la vida después
        int antes = Integer.parseInt(jugadores[1][1]);
        infinitygame.InfinityGame.menosVida(jugadores, 1);
        assertTrue(antes > Integer.parseInt(jugadores[0][1]));
    }

    @Test
    public void testcaidaPortal() {
        String jugadores[][] = {
            {"jug1", "0", "8"},
            {"jug2", "5", "3"}
        };
        //                 0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15
        char tablero[] = {'P', 's', 'd', 'p', 'b', 's', 'd', 'b', 'p', 's', 'd', 's', 'b', 'd', 's', 'F'};
        //la posicion inicial es 8, luego la posicion debe ser 3 para el jugador 1, esto porque desde la psoición 8 en adelante 
        //no existen más portales así que se da la vuelta y comienza de nuevo el tablero en la posicion 3 en donde hay un portal.
        infinitygame.InfinityGame.caidaPortal(tablero, 0, jugadores);
        assertTrue(jugadores[0][2].equals("3") && tablero[Integer.parseInt(jugadores[0][2])] == 'p');
        
        //para el jugador 2, la posicion inicial es 3, luego la posicion final debería ser 8
        infinitygame.InfinityGame.caidaPortal(tablero, 1, jugadores);
        assertTrue(jugadores[1][2].equals("8") && tablero[Integer.parseInt(jugadores[1][2])] == 'p');
    }
}
