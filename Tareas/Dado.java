package juegoinfinitygame;

public class Dado {

    private int caraResultante;

    Dado() {
        this.caraResultante=0;
    }

    public int lanzarDado() {
        this.caraResultante = (int) (Math.random() * 6) + 1 + (int) (Math.random() * 6) + 1;;
        return caraResultante;
    }
}
