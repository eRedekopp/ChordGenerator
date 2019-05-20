package Guitar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that represents a guitar chord
 *
 * @author redekopp
 */
public class Chord {

    public enum ChordDegree {ROOT, THIRD, FLAT_THIRD, FLAT_FIFTH, FIFTH, SHARP_FIFTH, FLAT_SEVENTH, SEVENTH,
                              FLAT_NINTH, NINTH, ELEVENTH, THIRTEENTH}

    /**
     * Mandatory notes in the chord (must be present in all voicings)
     */
    protected Note.NoteName[] bigNotes;

    /**
     * Non-mandatory notes in the chord
     */
    protected Note.NoteName[] otherNotes;

    /**
     * The root of the chord
     */
    protected Note.NoteName root;

    /**
     * For performing note calculations
     */
    private static final Guitar utilGuitar = new Guitar();

    public Note.NoteName[] getBigNotes() {
        return bigNotes;
    }

    public Note.NoteName[] getOtherNotes() {
        return otherNotes;
    }

    /**
     * Generate a new chord given the root and the type
     *
     * @param root The root note of the chord
     * @param type The type of the chord (see source code for options)
     */
    public Chord(Note.NoteName root, String type) {
        this.root = root;
        ArrayList<ChordDegree> otherDegreesList = new ArrayList<>(), bigDegreesList = new ArrayList<>();

        switch (type) {
            case "major triad":
                bigDegreesList.addAll(Arrays.asList(ChordDegree.ROOT, ChordDegree.THIRD));
                otherDegreesList.add(ChordDegree.FIFTH);
                break;
            case "minor triad":
                bigDegreesList.addAll(Arrays.asList(ChordDegree.ROOT, ChordDegree.FLAT_THIRD));
                otherDegreesList.add(ChordDegree.FIFTH);
                break;
            case "augmented triad":
                bigDegreesList.addAll(Arrays.asList(ChordDegree.ROOT, ChordDegree.THIRD));
                otherDegreesList.add(ChordDegree.SHARP_FIFTH);
                break;
            case "diminished triad":
                bigDegreesList.addAll(Arrays.asList(ChordDegree.ROOT, ChordDegree.FLAT_THIRD));
                otherDegreesList.add(ChordDegree.FLAT_FIFTH);
                break;
            default: throw new IllegalArgumentException("Unexpected chord type: " + type);
        }

        ArrayList<Note.NoteName> bigNotesList = new ArrayList<>(), otherNotesList = new ArrayList<>();
        for (ChordDegree degree : bigDegreesList)   bigNotesList.add(getNoteAtChordDegree(degree));
        for (ChordDegree degree : otherDegreesList) otherNotesList.add(getNoteAtChordDegree(degree));
        this.bigNotes   = (Note.NoteName[]) otherNotesList.toArray();
        this.otherNotes = (Note.NoteName[]) bigNotesList.toArray();
    }

    /**
     * Generate a new chord given an array of all the notes in the chord, and an array of the most important notes
     * (ie. ones the must be present in the chord for it to still be considered that chord)
     *
     * @param bigNotes The mandatory notes of the chord
     * @param otherNotes Non-mandatory notes in the chord
     */
    public Chord(Note.NoteName root, Note.NoteName[] bigNotes, Note.NoteName[] otherNotes) {
        this.otherNotes = otherNotes;
        this.bigNotes = bigNotes;
        this.root = root;
    }


    public Note.NoteName getRoot() {
        return this.root;
    }

    /**
     * @return the interval between the two given notes
     */
    public static ChordDegree getInterval(Note.NoteName note1, Note.NoteName note2) {
        int pitch1 = utilGuitar.findLowestPitch(note1);
        int pitch2 = utilGuitar.findLowestPitch(note2);
        int pitchDiff = Math.abs(pitch1 - pitch2);

        switch (pitchDiff) {
            case 0:
                return ChordDegree.ROOT;
            case 1:
                return ChordDegree.FLAT_NINTH;
            case 2:
                return ChordDegree.NINTH;
            case 3:
                return ChordDegree.FLAT_THIRD;
            case 4:
                return ChordDegree.THIRD;
            case 5:
                return ChordDegree.ELEVENTH;
            case 6:
                return ChordDegree.FLAT_FIFTH;
            case 7:
                return ChordDegree.FIFTH;
            case 8:
                return ChordDegree.SHARP_FIFTH;
            case 9:
                return ChordDegree.THIRTEENTH;
            case 10:
                return ChordDegree.FLAT_SEVENTH;
            case 11:
                return ChordDegree.SEVENTH;
            default:
                throw new RuntimeException("Error: found difference between notes of " + pitchDiff + " - unable to " +
                        "parse");
        }
    }

    public Note.NoteName[] allNotes() {
        int arrLength = bigNotes.length + otherNotes.length;
        Note.NoteName[] allNotes = new Note.NoteName[arrLength];
        System.arraycopy(bigNotes, 0, allNotes, 0, bigNotes.length);
        System.arraycopy(otherNotes, 0, allNotes, bigNotes.length, otherNotes.length);
        System.out.println("Debug message: bigNotes = " + bigNotes + " otherNotes = " + otherNotes +
                           " allNotes = " + allNotes);
        return allNotes;
    }

    /**
     * Return the chord degree of the given note in this chord
     *
     * @param note The note whose degree is to be determined
     * @return The chord degree of the note
     */
    public ChordDegree getChordDegree(Note.NoteName note) {
        return getInterval(this.root, note);
    }

    /**
     * Return the note that is the given interval away from the given note
     *
     * @param note The starting note
     * @param interval The interval between the given note and the returned note
     * @return The note at the given interval from the starting note
     */
    public static Note.NoteName getNoteAtInterval(Note.NoteName note, ChordDegree interval) {
        int intervalOffset;
        switch (interval) {
            case ROOT:
                intervalOffset = 0;  break;
            case FLAT_NINTH:
                intervalOffset = 1;  break;
            case NINTH:
                intervalOffset = 2;  break;
            case FLAT_THIRD:
                intervalOffset = 3;  break;
            case THIRD:
                intervalOffset = 4;  break;
            case ELEVENTH:
                intervalOffset = 5;  break;
            case FLAT_FIFTH:
                intervalOffset = 6;  break;
            case FIFTH:
                intervalOffset = 7;  break;
            case SHARP_FIFTH:
                intervalOffset = 8;  break;
            case THIRTEENTH:
                intervalOffset = 9;  break;
            case FLAT_SEVENTH:
                intervalOffset = 10; break;
            case SEVENTH:
                intervalOffset = 11; break;
            default:
                throw new RuntimeException("Unknown Error");  // to satisfy the compiler
        }
        return utilGuitar.calcNoteName(utilGuitar.findLowestPitch(note) + intervalOffset);
    }

    /**
     * Get the note at the given chord degree
     *
     * @param degree The chord degree of the returned note
     * @return The note at the given chord degree
     */
    public Note.NoteName getNoteAtChordDegree(ChordDegree degree) {
        return getNoteAtInterval(this.root, degree);
    }

    public static ChordDegree getIntervalFromPitchDiff(int pitchDiff) {
        switch (pitchDiff % 12) {
            case 0: return ChordDegree.ROOT;
            case 1: return ChordDegree.FLAT_NINTH;
            case 2: return ChordDegree.NINTH;
            case 3: return ChordDegree.FLAT_THIRD;
            case 4: return ChordDegree.THIRD;
            case 5: return ChordDegree.ELEVENTH;
            case 6: return ChordDegree.FLAT_FIFTH;
            case 7: return ChordDegree.FIFTH;
            case 8: return ChordDegree.SHARP_FIFTH;
            case 9: return ChordDegree.THIRTEENTH;
            case 10: return ChordDegree.FLAT_SEVENTH;
            case 11: return ChordDegree.SEVENTH;
            default: throw new RuntimeException("Error: found pitch difference: " + pitchDiff % 12);
        }
    }
}
