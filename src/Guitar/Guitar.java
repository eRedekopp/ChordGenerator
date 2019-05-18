package Guitar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that contains information the setup of a particular guitar, and utilities for finding chords on it
 *
 * @author redekopp
 */
public class Guitar {

    /**
     * The lowest note on the guitar
     */
    private Note.NoteName lowestNoteName;

    /**
     * Each string's pitch as an offset from the lowest note (eg. tuning[0] = 0 and represents the lowest string)
     */
    private int[] tuning;

    /**
     * The highest playable fret of the guitar
     */
    private int highestPlayableFret;

    /**
     * The maximum number of frets that a chord on this guitar may span
     */
    private int maxFretSpan;

    private static int[] STANDARD_TUNING = {0, 5, 10, 15, 19, 24};

    /**
     * Set up a new guitar in standard tuning
     */
    public Guitar() {
        this.tuning = STANDARD_TUNING;
        this.lowestNoteName = Note.NoteName.E;
        this.highestPlayableFret = 15;
        this.maxFretSpan = 4;
    }

    /**
     * @return The number of strings on the guitar
     */
    public int numberOfStrings() {
        return this.tuning.length;
    }

    /**
     * Finds all the notes on the fretboard with the given noteName
     *
     * @param noteName The name of the note to be searched
     * @return All the notes on the fretboard of the given name
     */
    public Note[] findNotes(Note.NoteName noteName) {
        ArrayList<Note> foundNotes = new ArrayList<>();
        for (int string = 0; string < this.numberOfStrings(); string++) {
            int curFret = this.findLowestFret(noteName, string);
            while (curFret < highestPlayableFret) {
                foundNotes.add(new Note(string, curFret, this));
                curFret += 12;
            }
        }
        return (Note[]) foundNotes.toArray();
    }

    /**
     * Find all the playable chords containing the given notes
     *
     * @param chord The chord whose voicings are to be found
     * @return All playable chord voicings with the given notes
     */
    public ChordVoicing[] findChords(Chord chord) {

        ChordBuilder chBuilder = new ChordBuilder(this, chord);
        return chBuilder.allChords();
    }

    /**
     * Find all the pitches on the fretboard that correspond to the given note name
     *
     * @param note The note for which to search
     * @return All pitches on the fretboard with the given name, represented as an offset from the lowest note
     */
    public Integer[] findPitches(Note.NoteName note) {
        int maxPitch = this.tuning[this.numberOfStrings()-1] + this.highestPlayableFret;
        int curPitch = this.findLowestPitch(note);
        ArrayList<Integer> pitches = new ArrayList<>();
        while (curPitch < maxPitch) {
            pitches.add(curPitch);
            curPitch += 12;
        }
        return (Integer[]) pitches.toArray();
    }

    /**
     * Find all the notes of the given noteName on the given string
     *
     * @param noteName The name of the note to be searched
     * @param string The string on which to find the note(s)
     * @return An ArrayList of Notes representing all the notes of the given note name on the given string
     */
    public ArrayList<Note> findNotesByString(Note.NoteName noteName, int string) {
        ArrayList<Note> outList = new ArrayList<>();
        int lowestFret = this.findLowestFret(noteName, string);
        outList.add(new Note(string, lowestFret, this));
        if (lowestFret + 12 < this.highestPlayableFret)
            outList.add(new Note(string, lowestFret+12, this));
        return outList;
    }

    /**
     * Find the lowest pitch on the fretboard that corresponds to the given note name
     *
     * @param note The note for which to search
     * @return The lowest pitch on the fretboard with the given name, represented as an offset from the lowest note
     */
    public int findLowestPitch(Note.NoteName note) {
        return this.findLowestFret(note, 0);
    }

    public int getMaxFretSpan() {
        return maxFretSpan;
    }

    /**
     * Find the lowest fret corresponding to the given note on the given string
     *
     * @param note The name of the note for which to search
     * @param string The string number as an offset from the lowest string (eg. lowest string is 0)
     * @return The lowest fret number corresponding to the given note on the string
     */
    public int findLowestFret(Note.NoteName note, int string) {
        int currentFret = 0;
        Note.NoteName currentNote = this.calcNoteName(this.tuning[string]);
        while (currentNote != note) {
            currentFret++;
            currentNote = this.calcNoteName(this.tuning[string] + currentFret);
        }
        if (currentFret > highestPlayableFret) throw new FretOutOfBoundsException();
        return currentFret;
    }

    /**
     * Calculate the note name based on a given pitch
     *
     * @param pitch The pitch as an offset from the lowest note
     * @return The name of the note represented by the pitch
     */
    public Note.NoteName calcNoteName(int pitch) {
        // Calculate based on lowestNoteName of A; modulate accordingly
        switch (this.lowestNoteName) {
            case A:                   break;
            case A_SHARP: pitch -= 1; break;
            case B:       pitch -= 2; break;
            case C:       pitch -= 3; break;
            case C_SHARP: pitch -= 4; break;
            case D:       pitch -= 5; break;
            case D_SHARP: pitch =- 6; break;
            case E:       pitch += 5; break;
            case F:       pitch += 4; break;
            case F_SHARP: pitch += 3; break;
            case G:       pitch += 2; break;
            case G_SHARP: pitch += 1; break;
        }
        while (pitch < 0) pitch += 12;
        switch (pitch % 12) {
            case 0:  return Note.NoteName.A;
            case 1:  return Note.NoteName.A_SHARP;
            case 2:  return Note.NoteName.B;
            case 3:  return Note.NoteName.C;
            case 4:  return Note.NoteName.C_SHARP;
            case 5:  return Note.NoteName.D;
            case 6:  return Note.NoteName.D_SHARP;
            case 7:  return Note.NoteName.E;
            case 8:  return Note.NoteName.F;
            case 9:  return Note.NoteName.F_SHARP;
            case 10: return Note.NoteName.G;
            case 11: return Note.NoteName.G_SHARP;
            default: return null;
        }
    }

    /**
     * Calculate the pitch of a note given its string and fret
     *
     * @param string The string number as an offset from the lowest string (eg. lowest string is 0)
     * @param fret The fret number (eg. open is 0, first fret is 1, etc.)
     * @return The pitch of the note as an offset from the lowest note
     */
    public int calcPitch(int string, int fret) {
        return this.tuning[string] + fret;
    }


    /**
     * A utility class to generate combinations of 2d lists of unknown size for generating chords
     */
    private class ChordBuilder {

        int[] indices;      // current index of each ArrayList in notesByString. A cursor, sort of
        int[] maxIndices;   // the length of each ArrayList in notesByString
        Guitar guitar;      // the guitar on which to build the chord
        Chord chord;        // the chord to be built
        ArrayList<Note>[] notesByString; // each chord tone

        public ChordBuilder(Guitar guitar, Chord chord) {

            this.guitar = guitar;
            this.indices = new int[guitar.numberOfStrings()];
            this.maxIndices = new int[guitar.numberOfStrings()];
            this.chord = chord;

            this.notesByString = new ArrayList[this.guitar.numberOfStrings()];  // set up notesByString
            for (int string = 0; string < this.guitar.numberOfStrings(); string++) {
                this.notesByString[string] = new ArrayList<>();
                for (Note.NoteName noteName : chord.allNotes())
                    this.notesByString[string].addAll(this.guitar.findNotesByString(noteName, string));
            }

            // set up maxIndices
            for (int i = 0; i < guitar.numberOfStrings(); i++) maxIndices[i] = this.notesByString[i].size();
        }

        /**
         * @return All valid combinations of chords from notesByString
         */
        public ChordVoicing[] allChords() {
            final int[] zeros = new int[indices.length]; // zero'd out array for comparison later
            ArrayList<ChordVoicing> outList = new ArrayList<>();

            do {
                Note[] voicing = new Note[indices.length];
                for (int string = 0; string < indices.length; string++) {
                    try {
                        voicing[string] = notesByString[string].get(indices[string]);
                    } catch (IndexOutOfBoundsException e) {
                        voicing[string] = null;
                    }
                }
                ChordVoicing chVoicing= new ChordVoicing(this.chord.getRoot(), this.chord.getBigNotes(),
                                                         this.chord.getOtherNotes(), voicing, this.guitar);
                if (chVoicing.isValid()) outList.add(chVoicing);
                incrementIndices();
            } while (!Arrays.equals(this.indices, zeros)); // repeat until indices roll over

            return (ChordVoicing[]) outList.toArray();
        }

        /**
         * Go to the next index, wrap around to 0 if max index reached
         */
        private void incrementIndices() {
            try {
                for (int i = this.indices.length; i >= 0; i--) { 
                    this.indices[i]++;
                    if (this.indices[i] > this.maxIndices[i]) { // indices[i] == maxIndices[i] represents a muted string
                        this.indices[i - 1]++;                  // (gets set to null in allChords)
                        this.indices[i] = 0;
                    } else return;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Arrays.fill(indices, 0); // roll over when indices[0] reaches its max and tries to fetch indices[-1]
            }
        }
    }

}
