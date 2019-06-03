package Control;

import SwingElements.ChordConfigFrame;

import javax.swing.*;

public class ChordSelectView implements ModelListener {

    private ChordConfigFrame configFrame;

    private ChordSelectInteractionModel iModel;

    public ChordSelectView() {
        configFrame.getGuitarTuningTextArea().setEnabled(false);
        configFrame.getOtherNotesTextArea().setEnabled(false);
        configFrame.getReqNotesTextArea().setEnabled(false);
        configFrame.getReqStringTextArea().setEnabled(false);


    }

    public void modelChanged() {

    }
}
