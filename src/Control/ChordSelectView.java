package Control;

import SwingElements.ChordConfigFrame;

import javax.swing.*;

public class ChordSelectView implements ModelListener {

    private ChordConfigFrame configFrame;

    private Model model;

    private ChordSelectInteractionModel iModel;

    public void setModel(Model model) {
        this.model = model;
    }

    public ChordSelectView() {
        configFrame.getGuitarTuningTextArea().setEnabled(false);
        configFrame.getOtherNotesTextArea().setEnabled(false);
        configFrame.getReqNotesTextArea().setEnabled(false);
        configFrame.getReqStringTextArea().setEnabled(false);


    }

    public void modelChanged() {

    }
}
