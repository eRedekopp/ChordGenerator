package Guitar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class that represents a specific voicing of a chord
 */
public class ChordVoicing extends Chord {

    /**
     * One Note for each string, or null if string is not played
     */
    private Note[] voicing;

    /**
     * The guitar on which this chord is to be played
     */
    private Guitar guitar;

    public ChordVoicing(String type, Note.NoteName root, Note[] voicing, Guitar guitar) {
        super(root, type);
        this.voicing = voicing;
        this.guitar = guitar;
    }

    /**
     * @return True if the fret span of the chord is within the limit of this.guitar and the chord contains all of
     * this.bigNotes, else return false
     */
    public boolean isValid() {
        HashSet<Note.NoteName> noteNames = new HashSet<>();
        for (int string = 0; string < voicing.length; string++) {
            Note note = voicing[string];
            if (note != null) noteNames.add(note.noteName);
        }
        ArrayList<Integer> frets = new ArrayList<>();
        for (Note note : voicing) if (note != null) frets.add(note.fret);
        if (Collections.max(frets) - Collections.min(frets) + 1 > this.guitar.getMaxFretSpan()) return false;
        for (Note.NoteName noteName : this.bigNotes) if (!noteNames.contains(noteName)) return false;
        return true;
    }

    /**
     * @return The fret number for each string in this chord voicing, or -1 if string not played
     */
    public int[] getVoicingTab() {
        int[] outArray = new int[this.guitar.numberOfStrings()];
        for (int i = 0; i < this.guitar.numberOfStrings(); i++) {
            if (this.voicing[i] != null) outArray[i] = this.voicing[i].fret;
            else outArray[i] = -1;
        }
        return outArray;
    }

    private boolean equals(ChordVoicing o) {
        if (this.voicing.length != o.voicing.length) return false;
        for (int i = 0; i < voicing.length; i++)
            if (this.voicing[i] == null && o.voicing[i] != null
                    || this.voicing[i] != null && (o.voicing[i] == null || !this.voicing[i].equals(o.voicing[i])))
                return false;
        return true;
    }

}
