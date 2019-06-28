package Control;

import SwingElements.ChordConfigFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChordSelectView implements ModelListener {

    public static final String[] NOTENAMES_FORMATTED = {
            "A", "Bb/A#", "B", "C", "Db/C#", "D", "Eb/D#", "E", "F", "Gb/F#", "G", "Ab/G#"
    };

    public static final String[] SUPPORTED_CHORD_QUALITIES = {
            "Major Triad", "Minor Triad", "Augmented Triad", "Diminished Triad"
    };

    public static final String[] SUPPORTED_GUITAR_TUNINGS = {
        "E Standard"
    };

    ChordConfigFrame configFrame;

    private Model model;

    private ChordSelectInteractionModel iModel;

    private ChordSelectController chordSelectController;

    public void setModel(Model model) {
        this.model = model;
    }

    public void setiModel(ChordSelectInteractionModel iModel) {
        this.iModel = iModel;
    }

    @SuppressWarnings("unchecked")
    public ChordSelectView() {

        // disable editing text areas
        this.configFrame.getGuitarTuningTextArea().setEnabled(false);
        this.configFrame.getOtherNotesTextArea().setEnabled(false);
        this.configFrame.getReqNotesTextArea().setEnabled(false);
        this.configFrame.getReqStringTextArea().setEnabled(false);

        // set values for boxes that never get updated
        this.configFrame.getChordQuickNoteBox().setModel(new DefaultComboBoxModel(NOTENAMES_FORMATTED));
        this.configFrame.getChordQuickQualityBox().setModel(new DefaultComboBoxModel(SUPPORTED_CHORD_QUALITIES));
        this.configFrame.getGuitarQuickBox().setModel(new DefaultComboBoxModel(SUPPORTED_GUITAR_TUNINGS));
        this.configFrame.getGuitarTuningBox().setModel(new DefaultComboBoxModel(NOTENAMES_FORMATTED));
        this.configFrame.getReqStringNoteBox().setModel(new DefaultComboBoxModel(NOTENAMES_FORMATTED));

    }

    /**
     * @return All integers in from a to b (non inclusive), or null if a > b
     */
    public static int[] range(int a, int b) {
        if (a > b) return null;
        if (a == b) return new int[]{a};

        else {
            int[] outArray = new int[b - a];
            int i = 0;
            while (a < b) outArray[i++] = a++;
            return outArray;
        }
    }

    @SuppressWarnings("unchecked")
    public void modelChanged() {
        // update all dynamic combo boxes, text areas, and spinners
        this.configFrame.getReqNoteAddBox().setModel(
                new DefaultComboBoxModel(this.iModel.getReqNoteAddBoxValues().toArray()));
        this.configFrame.getReqNoteRemBox().setModel(
                new DefaultComboBoxModel((this.iModel.getReqNoteRemBoxValues().toArray())));
        this.configFrame.getReqNotesTextArea().setText(
                this.iModel.getReqNoteTextAreaValue());
        this.configFrame.getOtherNotesAddBox().setModel(
                new DefaultComboBoxModel(this.iModel.getOtherNotesAddBoxValues().toArray()));
        this.configFrame.getOtherNotesRemBox().setModel(
                new DefaultComboBoxModel(this.iModel.getOtherNotesRemBoxValues().toArray()));
        this.configFrame.getOtherNotesTextArea().setText(
                this.iModel.getOtherNoteTextAreaValue());
        this.configFrame.getReqStringStringBox().setModel(
                new DefaultComboBoxModel(this.iModel.getReqStringStringBoxValues().toArray()));
        this.configFrame.getReqStringTextArea().setText(
                this.iModel.getReqStringTextAreaValue());
        this.configFrame.getReqStringRemBox().setModel(
                new DefaultComboBoxModel(this.iModel.getReqStringRemBoxValues().toArray()));
        this.configFrame.getAllowDuplicateNotesCheckBox().setSelected(
                this.iModel.duplicatesAllowed());
        this.configFrame.getGuitarTuningTextArea().setText(
                this.iModel.getGuitarTuningTextAreaValue());
        this.configFrame.getTopFretSpinner().setValue(
                this.iModel.getTopFretSpinnerValue());
        this.configFrame.getMaxSpanSpinner().setValue(
                this.iModel.getMaxSpanSpinnerValue());
    }

    public void displayDialogMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @return all elements of list1 which are not in list2
     */
    public static ArrayList removeAllMatchingElements(List<?> list1, List<?> list2) {
        ArrayList<?> outList = new ArrayList<>(list1);
        outList.forEach((i) -> {
            if (list2.contains(i)) outList.remove(i);
        });
        return outList;
    }
}
