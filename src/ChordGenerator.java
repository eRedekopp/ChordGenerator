import Guitar.*;

import java.util.Arrays;
import java.util.List;

public class ChordGenerator {

    public static void main(String[] args) {
//        Model model = new Model();
//        CommandLineView view = new CommandLineView();
//        CommandLineController controller = new CommandLineController();

        Guitar guitar = new Guitar();

        Note.NoteName[] bigNotes = {Note.NoteName.E, Note.NoteName.G_SHARP};
        Note.NoteName[] otherNotes = {Note.NoteName.B};
        // e major cowboy-style
        Note[] voiceNotes = {null, new Note(2, 2, guitar), new Note(3, 2, guitar), new Note(4, 2, guitar), null, null};
        ChordVoicing voicing = new ChordVoicing(
                Note.NoteName.E,
                bigNotes,
                otherNotes,
                voiceNotes,
                guitar
        );

        System.out.println(voicing.toString());

    }

}
