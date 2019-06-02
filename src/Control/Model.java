package Control;

import Guitar.*;

import java.util.ArrayList;

public class Model {

    private Mode currentMode;

    private Guitar guitar;

    private ArrayList<ModelListener> listeners;

    private String chordName;

    private ChordVoicing[] voicingsToDisplay;

    private int displayedChordIndex = 0;

    public void goNextChord() {
        this.displayedChordIndex += 1;
        if (this.displayedChordIndex >= voicingsToDisplay.length)
            this.displayedChordIndex = voicingsToDisplay.length - 1;
        this.notifySubscribers();
    }

    public void goPrevChord() {
        this.displayedChordIndex -= 1;
        if (this.displayedChordIndex < 0) this.displayedChordIndex = 0;
        this.notifySubscribers();
    }

    public ChordVoicing getCurrentChord() {
        return this.voicingsToDisplay[this.displayedChordIndex];
    }

    public boolean cursorAtFirstChord() {
        return displayedChordIndex == 0;
    }

    public boolean cursorAtLastChord() {
        return displayedChordIndex == voicingsToDisplay.length - 1;
    }

    public String getChordName() {
        return chordName;
    }

    public void setChordName(String chordName) {
        this.chordName = chordName;
    }

    public Model() {
        guitar = new Guitar();
        listeners = new ArrayList<>();
        chordName = "Unknown Chord";
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
