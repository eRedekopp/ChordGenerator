import Guitar.*;

import java.util.ArrayList;

public class CommandLineController {

    public enum Mode { GET, DISPLAY }

    private Mode currentMode;

    private ChordVoicing[] voicings;

    boolean running;

    private ArrayList<ModelListener> listeners;

    public CommandLineController() {

    }

    public void notifySubscribers() {
        for (ModelListener ml : listeners) ml.modelChanged();
    }

    public void addSubscriber(ModelListener newListener) {
        listeners.add(newListener);
    }

    public ChordVoicing[] getVoicings() {
        return voicings;
    }

    public void setVoicings(ChordVoicing[] voicings) {
        this.voicings = voicings;
    }

    public void start() {
        this.running = true;
        this.setMode(Mode.GET);
    }

    public Mode getMode() {
        return currentMode;
    }

    public void setMode(Mode newMode) {
        this.currentMode = newMode;
        this.notifySubscribers();
    }

    public void displayVoicings() {
        if (this.voicings == null) throw new IllegalStateException("No voicings to display");
        this.setMode(Mode.DISPLAY);
    }

    public void quit() {
        System.exit(0);
    }

}
