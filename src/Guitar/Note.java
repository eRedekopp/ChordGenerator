package Guitar;

/**
 *
 * A class which represents a single note-string combination on the fretboard
 *
 * @author redekopp
 */
public class Note {

    /**
     * The string of the note: lowest string = 0, second-lowest = 1, ...
     */
    public final int string;

    /**
     * The fret of the note: Open = 0, 1st fret = 1, ...
     */
    public final int fret;

    /**
     * Measured as an offset from the lowest note of the guitar
     */
    private final int pitch;

    /**
     * The guitar on which the note is to be played
     */
    private Guitar guitar;

    /**
     * The name of this note
     */
    public final NoteName noteName;

    public Note(int string, int fret, Guitar guitar) {
        this.string = string;
        this.fret = fret;
        this.guitar = guitar;
        pitch = guitar.calcPitch(string, fret);
        noteName = guitar.calcNoteName(pitch);
    }

    /**
     * Return true if this note has the same string and fret as note o, else return false
     *
     * @param o The other note to compare
     * @return true if this note has the same string and fret as note o, else return false
     */
    public boolean equals(Note o) {
        return this.string == o.string && this.fret == o.fret;
    }

}
