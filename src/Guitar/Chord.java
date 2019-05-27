package Guitar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that represents a guitar chord
 *
 * @author redekopp
 */
public class Chord {

    /**
     * Mandatory notes in the chord (must be present in all voicings)
     */
    protected NoteName[] bigNotes;

    /**
     * Non-mandatory notes in the chord
     */
    protected NoteName[] otherNotes;

    /**
     * The root of the chord
     */
    protected NoteName root;

    /**
     * For performing note calculations
     */
    private static final Guitar utilGuitar = new Guitar();

    public NoteName[] getBigNotes() {
        return bigNotes;
    }

    public NoteName[] getOtherNotes() {
        return otherNotes;
    }

    /**
     * Generate a new chord given the root and the type
     *
     * @param root The root note of the chord
     * @param type The type of the chord (see source code for options)
     */
    public Chord(NoteName root, String type) {
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

        ArrayList<NoteName> bigNotesList = new ArrayList<>(), otherNotesList = new ArrayList<>();
        for (ChordDegree degree : bigDegreesList)   bigNotesList.add(getNoteAtChordDegree(degree));
        for (ChordDegree degree : otherDegreesList) otherNotesList.add(getNoteAtChordDegree(degree));
        this.bigNotes   = (NoteName[]) otherNotesList.toArray();
        this.otherNotes = (NoteName[]) bigNotesList.toArray();
    }

    /**
     * Generate a new chord given an array of all the notes in the chord, and an array of the most important notes
     * (ie. ones the must be present in the chord for it to still be considered that chord)
     *
     * @param bigNotes The mandatory notes of the chord
     * @param otherNotes Non-mandatory notes in the chord
     */
    public Chord(NoteName root, NoteName[] bigNotes, NoteName[] otherNotes) {
        this.otherNotes = otherNotes;
        this.bigNotes = bigNotes;
        this.root = root;
    }


    public NoteName getRoot() {
        return this.root;
    }

    /**
     * @return the interval between the two given notes
     */
    public static ChordDegree getInterval(NoteName note1, NoteName note2) {
        int pitch1 = utilGuitar.findLowestPitch(note1);
        int pitch2 = utilGuitar.findLowestPitch(note2);
        int pitchDiff = pitch2 - pitch1;
        while (pitchDiff < 0)  pitchDiff += 12;
        while (pitchDiff > 11) pitchDiff -= 12;

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

    public NoteName[] allNotes() {
        int arrLength = bigNotes.length + otherNotes.length;
        NoteName[] allNotes = new NoteName[arrLength];
        System.arraycopy(bigNotes, 0, allNotes, 0, bigNotes.length);
        System.arraycopy(otherNotes, 0, allNotes, bigNotes.length, otherNotes.length);
        return allNotes;
    }

    /**
     * Return the chord degree of the given note in this chord
     *
     * @param note The note whose degree is to be determined
     * @return The chord degree of the note
     */
    public ChordDegree getChordDegree(NoteName note) {
        return getInterval(this.root, note);
    }

    /**
     * Return the note that is the given interval away from the given note
     *
     * @param note The starting note
     * @param interval The interval between the given note and the returned note
     * @return The note at the given interval from the starting note
     */
    public static NoteName getNoteAtInterval(NoteName note, ChordDegree interval) {
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
    public NoteName getNoteAtChordDegree(ChordDegree degree) {
        return getNoteAtInterval(this.root, degree);
    }

    /**
     * Return the name of an interval given the distance in semitones from the first note to the second note going
     * upward only (eg. major 7th is 11 and not 1)
     *
     * @param pitchDiff The distance in semitones from the first note to the second note going upward only
     * @return The name of the interval corresponding to the pitch difference
     */
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

    public static int getPitchDiffFromInterval(ChordDegree interval) {
        switch (interval) {
            case ROOT:          return 0;
            case FLAT_NINTH:    return 1;
            case NINTH:         return 2;
            case FLAT_THIRD:    return 3;
            case THIRD:         return 4;
            case ELEVENTH:      return 5;
            case FLAT_FIFTH:    return 6;
            case FIFTH:         return 7;
            case SHARP_FIFTH:   return 8;
            case THIRTEENTH:    return 9;
            case FLAT_SEVENTH:  return 10;
            case SEVENTH:       return 11;
            default: throw new RuntimeException("There should be no possible way for this to happen but I still need " +
                                                "to include a default case for some reason");
        }
    }

    public static void main(String[] args) {

        // testing getInterval

        NoteName[] getIntervalInputs = {
                NoteName.A, NoteName.A,               // Unison
                NoteName.C, NoteName.C_SHARP,         // min2
                NoteName.F_SHARP, NoteName.G_SHARP,   // maj2
                NoteName.A_SHARP, NoteName.C_SHARP,   // min3
                NoteName.E, NoteName.G_SHARP,         // maj3
                NoteName.D, NoteName.G,               // per4
                NoteName.B, NoteName.F,               // TT
                NoteName.D_SHARP, NoteName.A_SHARP,   // per5
                NoteName.G_SHARP, NoteName.E,         // min6
                NoteName.A, NoteName.F_SHARP,         // maj6
                NoteName.F, NoteName.D_SHARP,         // min7
                NoteName.F_SHARP, NoteName.F          // maj7
        };
        ChordDegree[] getIntervalExpected = {
                ChordDegree.ROOT,
                ChordDegree.FLAT_NINTH,
                ChordDegree.NINTH,
                ChordDegree.FLAT_THIRD,
                ChordDegree.THIRD,
                ChordDegree.ELEVENTH,
                ChordDegree.FLAT_FIFTH,
                ChordDegree.FIFTH,
                ChordDegree.SHARP_FIFTH,
                ChordDegree.THIRTEENTH,
                ChordDegree.FLAT_SEVENTH,
                ChordDegree.SEVENTH
        };
        for (int i = 0; i < getIntervalInputs.length; i+=2) {
            ChordDegree result = Chord.getInterval(getIntervalInputs[i], getIntervalInputs[i+1]);
            ChordDegree expected = getIntervalExpected[i/2];
            if (result != expected) {
                System.out.println(String.format(
                   "getInterval error -- inputs: %s %s | expected: %s | actual: %s",
                    getIntervalInputs[i], getIntervalInputs[i+1], expected, result
                ));
            }
        }

        // testing allNotes

        NoteName[] allNotesInputsBig = {NoteName.E, NoteName.G_SHARP};
        NoteName[] allNotesInputsOther = {NoteName.B};
        NoteName[] allNotesExpected = {NoteName.E, NoteName.G_SHARP, NoteName.B};
        Chord c = new Chord(NoteName.E, allNotesInputsBig, allNotesInputsOther);
        if (! Arrays.equals(allNotesExpected, c.allNotes()))
            System.out.println(String.format(
                    "allNotes error -- inputs: %s %s | expected: %s | actual: %s",
                    Arrays.toString(allNotesInputsBig), Arrays.toString(allNotesInputsOther),
                    Arrays.toString(allNotesExpected), Arrays.toString(c.allNotes())
            ));

        // testing getChordDegree

        if (c.getChordDegree(NoteName.F_SHARP) != ChordDegree.NINTH)
            System.out.println(String.format(
                    "getChordDegree error -- inputs: %s | expected: %s | actual: %s",
                    NoteName.F_SHARP, ChordDegree.NINTH, c.getChordDegree(NoteName.F_SHARP)
            ));

        // testing getNoteAtInterval

        ChordDegree[] getNoteAtIntervalInput = {
                ChordDegree.ROOT,
                ChordDegree.FLAT_NINTH,
                ChordDegree.NINTH,
                ChordDegree.FLAT_THIRD,
                ChordDegree.THIRD,
                ChordDegree.ELEVENTH,
                ChordDegree.FLAT_FIFTH,
                ChordDegree.FIFTH,
                ChordDegree.SHARP_FIFTH,
                ChordDegree.THIRTEENTH,
                ChordDegree.FLAT_SEVENTH,
                ChordDegree.SEVENTH
        };
        NoteName[] getNoteAtIntervalExpected = {
                NoteName.E,
                NoteName.F,
                NoteName.F_SHARP,
                NoteName.G,
                NoteName.G_SHARP,
                NoteName.A,
                NoteName.A_SHARP,
                NoteName.B,
                NoteName.C,
                NoteName.C_SHARP,
                NoteName.D,
                NoteName.D_SHARP,
        };

        for (int i = 0; i < getNoteAtIntervalExpected.length; i++)
            if (c.getNoteAtChordDegree(getNoteAtIntervalInput[i]) != getNoteAtIntervalExpected[i])
                System.out.println(String.format(
                        "getNoteAtChordDegree error -- root: %s | inputs: %s | expected: %s | actual: %s",
                        c.getRoot(), getNoteAtIntervalInput[i], getNoteAtIntervalExpected[i],
                        c.getNoteAtChordDegree(getNoteAtIntervalInput[i])
                ));

        System.out.println("##### Testing Complete #####");
    }
}
