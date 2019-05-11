import Guitar.*;

public class Model {

    private Guitar guitar;

    public Model() {
        guitar = new Guitar();
    }

    public Guitar getGuitar() {
        return guitar;
    }

    public void setGuitar(Guitar guitar) {
        this.guitar = guitar;
    }
}
