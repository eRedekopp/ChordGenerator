package Control;

import SwingElements.ChordConfigFrame;
import SwingElements.ChordFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main config and setup for MVC
 */
public class ChordGenerator {

    // mvc elements
    private ChordSelectController selectController;
    private ChordSelectView selectView;
    private ChordSelectInteractionModel selectInteractionModel;
    private ChordDisplayView displayView;
    private ChordDisplayController displayController;
    private Model model;


    @SuppressWarnings("unchecked")
    public ChordGenerator() {

        // todo finish chordselect setup

        // construct MVC elements
        this.selectView = new ChordSelectView();
        this.selectController = new ChordSelectController();
        this.selectView = new ChordSelectView();
        this.model = new Model();
        this.displayController = new ChordDisplayController();
        this.displayView = new ChordDisplayView();
        this.selectInteractionModel = new ChordSelectInteractionModel();

        // add relations
        this.selectController.setiModel(selectInteractionModel);
        this.selectView.setModel(model);
        this.displayController.setModel(model);
        this.model.addSubscriber(selectView);
        this.model.addSubscriber(displayView);





    }

    private void selectViewInitialSetup() {


    }





    public static void main(String[] args) {


    }

}
