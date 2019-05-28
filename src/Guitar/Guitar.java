package Guitar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that contains information the setup of a particular guitar, and utilities for finding chords on it
 *
 * @author redekopp
 */
public class Guitar {

    /**
     * The name of the lowest note on the guitar
     */
    private NoteName lowestNoteName;

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
        this.lowestNoteName = NoteName.E;
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
    public Note[] findNotes(NoteName noteName) {
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
        List<ChordVoicing> allChords = chBuilder.allChords();
        ChordVoicing[] outArray = new ChordVoicing[allChords.size()];   // can't just cast the list to an array
        for (int i = 0; i < allChords.size(); i++) {                    // because javac throws classCastException
            outArray[i] = allChords.get(i);
        }
        return outArray;
    }

    /**
     * Find all the pitches on the fretboard that correspond to the given note name
     *
     * @param note The note for which to search
     * @return All pitches on the fretboard with the given name, represented as an offset from the lowest note
     */
    public Integer[] findPitches(NoteName note) {
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
    public ArrayList<Note> findNotesByString(NoteName noteName, int string) {
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
    public int findLowestPitch(NoteName note) {
        for (int i = 0; i < 12; i++) if (this.calcNoteName(i) == note) return i;
        throw new RuntimeException("Unknown error"); // shouldn't happen
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
    public int findLowestFret(NoteName note, int string) {

        ChordDegree stringIntervalFromLowest = Chord.getIntervalFromPitchDiff(this.tuning[string]);
        NoteName stringNoteName = Chord.getNoteAtInterval(this.lowestNoteName, stringIntervalFromLowest);
        ChordDegree noteInterval = Chord.getInterval(stringNoteName, note);

        return Chord.getPitchDiffFromInterval(noteInterval);
    }

    /**
     * Calculate the note name based on a given pitch. Note: only works for tunings where the lowest note is E
     *
     * @param pitch The pitch as an offset from the lowest note
     * @return The name of the note represented by the pitch
     */
    public NoteName calcNoteName(int pitch) {

        // todo: remake this to work with different bottom notes
        if (this.lowestNoteName != NoteName.E)
            throw new RuntimeException("Non-standard tunings not yet supported");
        switch (pitch % 12) {
            case 0:  return NoteName.E;
            case 1:  return NoteName.F;
            case 2:  return NoteName.F_SHARP;
            case 3:  return NoteName.G;
            case 4:  return NoteName.G_SHARP;
            case 5:  return NoteName.A;
            case 6:  return NoteName.A_SHARP;
            case 7:  return NoteName.B;
            case 8:  return NoteName.C;
            case 9:  return NoteName.C_SHARP;
            case 10: return NoteName.D;
            case 11: return NoteName.D_SHARP;
            default: throw new RuntimeException("Unknown error"); // to satisfy the compiler
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
     * A utility class for generating chords
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
                for (NoteName noteName : chord.allNotes())
                    this.notesByString[string].addAll(this.guitar.findNotesByString(noteName, string));
            }

            // set up maxIndices
            for (int i = 0; i < guitar.numberOfStrings(); i++) maxIndices[i] = this.notesByString[i].size();
        }

        /**
         * @return All valid combinations of chords from notesByString
         */
        public List<ChordVoicing> allChords() {
            // brute force: just check every possible combination of notes in notesByString and add it to the list if
            // it's valid
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

            return outList;
        }

        /**
         * Go to the next index, wrap around to 0 if max index reached
         */
        private void incrementIndices() {
            try {
                for (int i = this.indices.length-1; i >= 0; i--) {
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
