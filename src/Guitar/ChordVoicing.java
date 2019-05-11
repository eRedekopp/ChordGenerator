package Guitar;

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

    private boolean equals(ChordVoicing o) {
        if (this.voicing.length != o.voicing.length) return false;
        for (int i = 0; i < voicing.length; i++)
            if (this.voicing[i] == null && o.voicing[i] != null
                    || this.voicing[i] != null && (o.voicing[i] == null || !this.voicing[i].equals(o.voicing[i])))
                return false;
        return true;
    }

}
