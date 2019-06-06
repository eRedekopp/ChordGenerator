package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChordGenerator {

    public ChordGenerator() {

        // construct MVC elements
        Controller controller = new Controller();
        ChordSelectView view = new ChordSelectView();
        Model model = new Model();
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


    }

}
