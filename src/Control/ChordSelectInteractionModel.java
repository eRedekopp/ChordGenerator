package Control;

import Guitar.NoteName;

import java.util.ArrayList;
import java.util.HashMap;

public class ChordSelectInteractionModel {

    private ArrayList<ModelListener> subscribers;

    private ArrayList<NoteName> reqNotes;

    private ArrayList<NoteName> otherNotes;

    private boolean dupNotesAllowed;

    private ArrayList<NoteName> tuning;

    private HashMap<Integer, NoteName> requiredStringNoteCombos;

    public ChordSelectInteractionModel() {
        this.subscribers = new ArrayList<>();
        this.reqNotes = new ArrayList<>();
        this.otherNotes = new ArrayList<>();
        this.dupNotesAllowed = true;
        this.tuning = new ArrayList<>();
        this.requiredStringNoteCombos = new HashMap<>();
    }

    public void addSubscriber(ModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        for (ModelListener m : subscribers) m.modelChanged();
    }

}
