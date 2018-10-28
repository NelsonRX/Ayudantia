package juegoinfinitygame;

public class jugador {

    private String nick;
    private int vida;
    private int posicion;
    private int opcionesMeditar;

    jugador(String nick) {
        this.nick = nick;
        this.vida = 15;
        this.posicion = 0;
        this.opcionesMeditar = 5;

    }

    public String getNick() {
        return this.nick;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida(int nuevaVida) {
        this.vida=nuevaVida;
        
    }

    public int getPosicion() {
        return this.posicion;
    }

    public void setPosicion(int nuevaPosicion) {
        this.posicion = nuevaPosicion;
    }

    public int getOpcionesMeditar() {
        return this.opcionesMeditar;
    }

    public void setOpcionesMeditar(int nuevaOpcionesMeditar) {
        this.opcionesMeditar = nuevaOpcionesMeditar;
    }
    
    public int lanzarDados(){
        Dado objDado=new Dado();
        return objDado.lanzarDado();
    }
}
