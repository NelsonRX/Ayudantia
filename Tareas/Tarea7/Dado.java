
public class Dado {

    private int caraResultante;

    Dado() {
        this.caraResultante = 0;
    }

    public void lanzarDado() {
        this.caraResultante = (int) (Math.random() * 6) + 1 + (int) (Math.random() * 6) + 1;;
    }

    public int getCaraResultante() {
        return this.caraResultante;
    }

}
