package Guitar;

import java.util.*;

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

    public ChordVoicing(Note.NoteName root, Note.NoteName[] bigNotes, Note.NoteName[] otherNotes, Note[] voicing,
                        Guitar guitar) {
        super(root, bigNotes, otherNotes);
        this.voicing = voicing;
        this.guitar = guitar;
    }

    /**
     * @return True if the fret span of the chord is within the limit of this.guitar, the chord contains all of
     * this.bigNotes, and the chord doesn't contain any invalid notes, else return false
     */
    public boolean isValid() {
        List<Note.NoteName> allChordNotes = Arrays.asList(this.allNotes());
        HashSet<Note.NoteName> noteNames = new HashSet<>();
        for (int string = 0; string < voicing.length; string++) {
            Note note = voicing[string];
            if (note != null) noteNames.add(note.noteName);
        }
        ArrayList<Integer> frets = new ArrayList<>();
        for (Note note : voicing) if (note != null && note.fret != 0) frets.add(note.fret);
        if (frets.size() != 0
            && Collections.max(frets) - Collections.min(frets) + 1 > this.guitar.getMaxFretSpan()) return false;
        for (Note.NoteName noteName : this.bigNotes) if (!noteNames.contains(noteName)) return false;
        for (Note.NoteName noteName : noteNames) if (!allChordNotes.contains(noteName)) return false;
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

    public int numberOfStrings() {
        return this.guitar.numberOfStrings();
    }

    public String toString() {
        // todo: displays chord chart sideways
        int[] frets = this.getVoicingTab();
        int minFret = min(frets), maxFret = max(frets), minFretDisplayed = minFret - 2, maxFretDisplayed = maxFret + 2;
        // use a stringbuilder for each line of output
        StringBuilder[] stringBuilders = new StringBuilder[frets.length];
        // keep track of open strings
        ArrayList<Integer> openStrings = new ArrayList<>();
        for (int string = 0; string < this.numberOfStrings(); string++) if (frets[string] == 0) openStrings.add(string);
        for (int i = 0; i < stringBuilders.length; i++) stringBuilders[i] = new StringBuilder();

        // make first vertical line
        for (int string = 0; string < this.numberOfStrings(); string++) stringBuilders[string].append('|');
        // mark frets
        for (int fret = minFretDisplayed; fret <= maxFretDisplayed; fret++) {
            if (fret <= 0) continue;
            for (int string = 0; string < this.numberOfStrings(); string++) {
                if (openStrings.contains(string)) stringBuilders[string].append("~~~|");
                else if (frets[string] == fret) {
                    stringBuilders[string].append("-X-|");
                }
                else stringBuilders[string].append("---|");
            }
        }
        // append note name to end of line
        for (int string = 0; string < this.numberOfStrings(); string++) {
            StringBuilder builder = stringBuilders[string];
            builder.append(' ');
            if (frets[string] != -1) {
                builder.append(this.getChordDegree(this.guitar.calcNoteName(
                        this.guitar.calcPitch(string, frets[string]))).toString());
            }
            else builder.append('X');
            builder.append('\n');
        }
        // add fret numbers
        StringBuilder fretNumberBuilder = new StringBuilder();
        if (minFretDisplayed <= 0) minFretDisplayed = 1;
        for (int fret = minFretDisplayed; fret < maxFretDisplayed; fret++) {
            if (fret % 2 == 0) fretNumberBuilder.append("    "); // 4 blank spaces
            else {
                fretNumberBuilder.append("  "); // centre fret number on fret
                fretNumberBuilder.append(fret);
                fretNumberBuilder.append(' ');
            }
        }

        // concat and return
        for (int string = 1; string < this.numberOfStrings(); string++)
            stringBuilders[0].append(stringBuilders[string].toString()); // append all to stringbuilders[0]
        stringBuilders[0].append(fretNumberBuilder.toString());
        return stringBuilders[0].toString();
    }

    private static int min(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Cannot find smallest item in empty array");
        int smallest = Integer.MAX_VALUE;
        for (int i : arr) if (i < smallest && i != -1) smallest = i;
        return smallest;
    }

    private static int max(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Cannot find largest item in empty array");
        int largest = Integer.MIN_VALUE;
        for (int i : arr) if (i > largest && i != -1) largest = i;
        return largest;
    }

}
