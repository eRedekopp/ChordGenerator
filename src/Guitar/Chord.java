package Guitar;

/**
 * A class that represents a guitar chord
 *
 * @author redekopp
 */
public class Chord {

    public enum ChordDegree {ROOT, THIRD, FLAT_THIRD, FLAT_FIFTH, FIFTH, SHARP_FIFTH, FLAT_SEVENTH, SEVENTH,
                              FLAT_NINTH, NINTH, ELEVENTH, THIRTEENTH}



    // TODO : populate these lists in the constructor
    /**
     * All notes contained within this chord
     */
    protected Note.NoteName[] allNotes;

    /**
     * Most important notes of the chord (must be present in all voicings)
     */
    protected Note.NoteName[] bigNotes;

    /**
     * The root of the chord
     */
    protected Note.NoteName root;

    protected String type;

    /**
     * For performing note calculations
     */
    private static final Guitar utilGuitar = new Guitar();

    public Chord(Note.NoteName root, String type) {
        this.type = type;
        this.root = root;
    }

    public String getType() {
        return this.type;
    }

    public Note.NoteName getRoot() {
        return this.root;
    }

    /**
     * Return the chord degree of the given note in this chord
     *
     * @param note The note whose degree is to be determined
     * @return The chord degree of the note
     */
    public ChordDegree getChordDegree(Note.NoteName note) {
        int rootPitch = utilGuitar.findLowestPitch(this.root);
        int notePitch = utilGuitar.findLowestPitch(note);
        int pitchDiff = Math.abs(rootPitch - notePitch);

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
}
