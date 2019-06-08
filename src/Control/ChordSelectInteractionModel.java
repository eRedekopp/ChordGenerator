package Control;

import Guitar.Note;
import Guitar.NoteName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChordSelectInteractionModel {

    private ArrayList<ModelListener> subscribers;

    private ArrayList<NoteName> reqNotes;

    private ArrayList<NoteName> otherNotes;

    private boolean dupNotesAllowed;

    private int topFretSpinnerValue;

    private int maxSpanSpinnerValue;

    private ArrayList<NoteName> tuning;

    private HashMap<Integer, NoteName> requiredStringNoteCombos;

    private static HashMap<NoteName, String> NOTES_TO_TEXT;

    public ChordSelectInteractionModel() {
        setNotesToText();
        this.subscribers = new ArrayList<>();
        this.reqNotes = new ArrayList<>();
        this.otherNotes = new ArrayList<>();
        this.dupNotesAllowed = true;
        this.tuning = new ArrayList<>();
        this.requiredStringNoteCombos = new HashMap<>();
    }

    private static void setNotesToText() {
        HashMap<NoteName, String> outMap = new HashMap<>();
        for (int i = 0; i < NoteName.values().length; i++) {
            outMap.put(NoteName.values()[i], ChordSelectView.NOTENAMES_FORMATTED[i]);
        }
        NOTES_TO_TEXT = outMap;
    }

    public void addSubscriber(ModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        for (ModelListener m : subscribers) m.modelChanged();
    }

    // todo return arrays instead of arraylist for efficiency in ChordSelectView.modelChanged()
    public ArrayList<String> getReqNoteAddBoxValues() {
        ArrayList<String> outList = new ArrayList<>(Arrays.asList(ChordSelectView.NOTENAMES_FORMATTED));
        for (NoteName note : NoteName.values()) {
            if (! this.reqNotes.contains(note)) outList.add(NOTES_TO_TEXT.get(note));
        }
        return outList;
    }

    public ArrayList<String> getReqNoteRemBoxValues() {
        ArrayList<String> outList = new ArrayList<>();
        for (NoteName note : reqNotes) outList.add(NOTES_TO_TEXT.get(note));
        return outList;
    }

    public int getTopFretSpinnerValue() {
        return this.topFretSpinnerValue;
    }

    public void setTopFretSpinnerValue(int value) {
        this.topFretSpinnerValue = value;
    }

    public int getMaxSpanSpinnerValue() {
        return this.maxSpanSpinnerValue;
    }

    public void setMaxSpanSpinnerValue(int value) {
        this.maxSpanSpinnerValue = value;
    }

    public ArrayList<String> getOtherNotesAddBoxValues() {
        ArrayList<String> outList = new ArrayList<>(Arrays.asList(ChordSelectView.NOTENAMES_FORMATTED));
        for (NoteName note : NoteName.values()) {
            if (! this.otherNotes.contains(note)) outList.add(NOTES_TO_TEXT.get(note));
        }
        return outList;
    }

    public ArrayList<String> getOtherNotesRemBoxValues() {
        ArrayList<String> outList = new ArrayList<>();
        for (NoteName note : NoteName.values())
            if (! this.otherNotes.contains(note)) outList.add(NOTES_TO_TEXT.get(note));
            return outList;
    }

    public ArrayList<String> getReqStringStringBoxValues() {
        ArrayList<String> outList = new ArrayList<>();
        for (int i = 1; i <= tuning.size(); i++)
            if (! requiredStringNoteCombos.containsKey(i)) outList.add(Integer.toString(i));
        return outList;
    }

    public boolean duplicatesAllowed() {
        return this.dupNotesAllowed;
    }

    public void setDupNotesAllowed(boolean b) {
        this.dupNotesAllowed = b;
    }

    public void addToTuning(NoteName note) {
        this.tuning.add(note);
    }

    public void clearTuning() {
        this.tuning = new ArrayList<>();
    }

    public String getReqNoteTextAreaValue() {
        StringBuilder builder = new StringBuilder();
        for (NoteName note : this.reqNotes) {
            builder.append(NOTES_TO_TEXT.get(note));
            builder.append(" ");
        }
        return builder.toString();
    }

    public String getOtherNoteTextAreaValue() {
        StringBuilder builder = new StringBuilder();
        for (NoteName note : this.otherNotes) {
            builder.append(NOTES_TO_TEXT.get(note));
            builder.append(" ");
        }
        return builder.toString();
    }

    public String getReqStringTextAreaValue() {
        StringBuilder builder = new StringBuilder();
        for (Integer i : this.requiredStringNoteCombos.keySet()) {
            builder.append(i);
            builder.append(" : ");
            builder.append(NOTES_TO_TEXT.get(this.requiredStringNoteCombos.get(i)));
            builder.append(", ");
        }
        return builder.toString();
    }

    public ArrayList<String> getReqStringRemBoxValues() {
        ArrayList<String> outList = new ArrayList<>();
        for (Integer i : this.requiredStringNoteCombos.keySet()) outList.add(i.toString());
        return outList;
    }

    public String getGuitarTuningTextAreaValue() {
        StringBuilder builder = new StringBuilder();
        for (NoteName note : this.tuning) {
            builder.append(NOTES_TO_TEXT.get(note));
            builder.append(" ");
        }
        return builder.toString();
    }

}
