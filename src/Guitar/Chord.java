package Guitar;

/**
 * A class that represents a guitar chord
 *
 * @author redekopp
 */
public class Chord {

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

}
