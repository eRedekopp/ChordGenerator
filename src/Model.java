import Guitar.*;

public class Model {

    private Guitar guitar;

    public Model() {
        guitar = new Guitar();
    }

    public ChordVoicing[] getAllChordVoicings(Chord chord) {
        return guitar.findChords(chord);
    }

}
