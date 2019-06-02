package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChordGenerator {

    public ChordGenerator() {

        // construct MVC elements
        Controller controller = new Controller();
        Model model = new Model();
        DialogView view = new DialogView();
        ChordDisplayController chordDisplayController = new ChordDisplayController();
        ChordDisplayView chordDisplayView = new ChordDisplayView();

        // add relations
        controller.setModel(model);
        view.setModel(model);
        chordDisplayController.setModel(model);
        model.addSubscriber(view);
        model.addSubscriber(chordDisplayView);

        // add listeners to chordFrame
        chordDisplayView.chordFrame.setCancelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chordDisplayController.handleCancelClick();
            }
        });
        chordDisplayView.chordFrame.setPrevListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chordDisplayController.handlePrevClick();
            }
        });
        chordDisplayView.chordFrame.setNextListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chordDisplayController.handleNextClick();
            }
        });


    }





    public static void main(String[] args) {
////        Control.Model model = new Control.Model();
////        Control.CommandLineView view = new Control.CommandLineView();
////        Control.Controller controller = new Control.Controller();
//
//        Guitar guitar = new Guitar();
//
//        NoteName[] bigNotes = {NoteName.E, NoteName.G_SHARP};
//        NoteName[] otherNotes = {NoteName.B};
//        // e major cowboy-style
//        Note[] voiceNotes = {new Note(0, 0, guitar), new Note(1, 2, guitar), new Note(2, 2, guitar),
//                             new Note(3, 1, guitar), new Note(4, 0, guitar), new Note(5, 0, guitar)};
//        ChordVoicing voicing = new ChordVoicing(
//                NoteName.E,
//                bigNotes,
//                otherNotes,
//                voiceNotes,
//                guitar
//        );
//
////        System.out.println(voicing.toString());
//
//        Chord chord = new Chord(NoteName.E, bigNotes, otherNotes);
//
//        ChordVoicing[] allEMajorChords = guitar.findChords(chord);
//
//        for (ChordVoicing v : allEMajorChords) {
//            System.out.println(v.toString());
//        }

        Model model = new Model();
        DialogView view = new DialogView();
        Controller controller = new Controller();

        model.addSubscriber(view);
        view.setModel(model);
        view.setController(controller);
        controller.setModel(model);
        controller.start();
    }

}
