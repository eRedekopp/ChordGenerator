package Control;

public class Controller {

    private Model model;

    boolean running;

    public void setModel(Model model) {
        this.model = model;
    }

    public void start() {
        this.running = true;

        while (this.running) {
            model.setMode(Mode.GET_MODE);
        }
    }

    public void quit() {
        this.running = false;
    }

}
