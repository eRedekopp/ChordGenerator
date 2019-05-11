package Guitar;

import java.util.ArrayList;

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

    private static int[] STANDARD_TUNING = {0, 5, 10, 15, 19, 24};

    /**
     * Set up a new guitar in standard tuning
     */
    public Guitar() {
        this.tuning = STANDARD_TUNING;
        this.lowestNoteName = Note.NoteName.E;
        this.highestPlayableFret = 15;
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
        //TODO
        // this.findNotes() ...
        return null;
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
     * Find the lowest pitch on the fretboard that corresponds to the given note name
     *
     * @param note The note for which to search
     * @return The lowest pitch on the fretboard with the given name, represented as an offset from the lowest note
     */
    public int findLowestPitch(Note.NoteName note) {
        return this.findLowestFret(note, 0);
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
}
