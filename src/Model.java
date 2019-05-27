import Guitar.*;

import java.util.ArrayList;

public class Model {

    private Mode currentMode;

    private Guitar guitar;

    private ArrayList<ModelListener> listeners;

    public ChordVoicing[] voicingsToDisplay;

    public Model() {
        guitar = new Guitar();
        listeners = new ArrayList<>();
    }

    public ChordVoicing[] getVoicings() {
        return this.voicingsToDisplay;
    }

    public Mode getMode() {
        return this.currentMode;
    }

    public void setMode(Mode newMode) {
        this.currentMode = newMode;
        this.notifySubscribers();
    }

    public void notifySubscribers() {
        for (ModelListener ml : listeners) ml.modelChanged();
    }

    public void addSubscriber(ModelListener newListener) {
        listeners.add(newListener);
    }

    public void setVoicings(Chord chord) {
        this.voicingsToDisplay = guitar.findChords(chord);
    }

}
