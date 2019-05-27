import Guitar.*;

public class ChordGenerator {





    public static void main(String[] args) {
//        Model model = new Model();
//        CommandLineView view = new CommandLineView();
//        CommandLineController controller = new CommandLineController();

        Guitar guitar = new Guitar();

        NoteName[] bigNotes = {NoteName.E, NoteName.G_SHARP};
        NoteName[] otherNotes = {NoteName.B};
        // e major cowboy-style
        Note[] voiceNotes = {new Note(0, 0, guitar), new Note(1, 2, guitar), new Note(2, 2, guitar),
                             new Note(3, 1, guitar), new Note(4, 0, guitar), new Note(5, 0, guitar)};
        ChordVoicing voicing = new ChordVoicing(
                NoteName.E,
                bigNotes,
                otherNotes,
                voiceNotes,
                guitar
        );

//        System.out.println(voicing.toString());

        Chord chord = new Chord(NoteName.E, bigNotes, otherNotes);

        ChordVoicing[] allEMajorChords = guitar.findChords(chord);

        System.out.println(allEMajorChords.length);

        for (ChordVoicing v : allEMajorChords) {
            System.out.println(v.toString());
        }

    }

}
