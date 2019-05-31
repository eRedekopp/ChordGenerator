package Control;

import javax.swing.*;

import Guitar.*;
import SwingElements.ChordChart;
import SwingElements.ChordChartDialog;

public class DialogView implements ModelListener {

    private Model model;

    private Controller controller;

    public void modelChanged() {

        switch (model.getMode()) {
            case GET_CHORD:
                this.getChordAndUpdateModel();
                model.setMode(Mode.DISPLAY);
                break;
            case GET_MODE:
                model.setMode(this.getModeFromUser());
                break;
            case DISPLAY:
                this.displayChordVoicings();
                break;
            case SETTINGS:
                this.settingsMenu();
                break;
            case EXIT:
                controller.quit();
        }
    }

    protected void getChordAndUpdateModel() {

        // todo custom chords


        boolean inputAcceptable = false;
        NoteName root = NoteName.A;
        String rootInput = "A";

        while (!inputAcceptable) {
            rootInput = JOptionPane.showInputDialog(null, "Enter the root of the chord", "Enter Root",
                    JOptionPane.PLAIN_MESSAGE);
            switch (rootInput) {
                case "A":
                    inputAcceptable = true;
                    root = NoteName.A;
                    break;
                case "Bb":
                case "A#":
                    inputAcceptable = true;
                    root = NoteName.A_SHARP;
                    break;
                case "Cb":
                case "B":
                    inputAcceptable = true;
                    root = NoteName.B;
                    break;
                case "B#":
                case "C":
                    inputAcceptable = true;
                    root = NoteName.C;
                    break;
                case "Db":
                case "C#":
                    inputAcceptable = true;
                    root = NoteName.C_SHARP;
                    break;
                case "D":
                    inputAcceptable = true;
                    root = NoteName.C_SHARP;
                    break;
                case "Eb":
                case "D#":
                    inputAcceptable = true;
                    root = NoteName.D_SHARP;
                case "Fb":
                case "E":
                    inputAcceptable = true;
                    root = NoteName.E;
                    break;
                case "E#":
                case "F":
                    inputAcceptable = true;
                    root = NoteName.F;
                    break;
                case "Gb":
                case "F#":
                    inputAcceptable = true;
                    root = NoteName.F_SHARP;
                    break;
                case "G":
                    inputAcceptable = true;
                    root = NoteName.G;
                case "Ab":
                case "G#":
                    inputAcceptable = true;
                    root = NoteName.G_SHARP;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unable to parse input", "Invalid Input",
                                                  JOptionPane.ERROR_MESSAGE);
            }
        }

        String[] chordOptions = {"Major Triad", "Minor Triad", "Augmented Triad", "Diminished Triad"};
        String chordChoice = ((String) JOptionPane.showInputDialog(
                null,
                "Select a chord quality",
                "Select Quality",
                JOptionPane.PLAIN_MESSAGE,
                null,
                chordOptions,
                "Major Triad"));
        model.setVoicings(new Chord(root, chordChoice.toLowerCase()));
        model.setChordName(rootInput + ' ' + chordChoice);
    }

    protected Mode getModeFromUser() {
        String[] options = {"Find Chords", "Change Settings", "Exit"};
        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Select an action",
                "Main Menu",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                "Find Chords");

        switch (choice) {
            case "Find Chords": return Mode.GET_CHORD;
            case "Change Settings": return Mode.SETTINGS;
            case "Exit": return Mode.EXIT;
            default: throw new RuntimeException("Found unexpected choice: " + choice);
        }
    }

    protected void displayChordVoicings() {

        boolean displaying = true;
        for (ChordVoicing v : model.getVoicings()) {
            if (!displaying) break;
            int selection = JOptionPane.showConfirmDialog(
                    null,
                    v.toString(),
                    model.getChordName(),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null);
//            int selection = ChordChartDialog.showDialog(new ChordChart(v));
            if (selection == JOptionPane.CANCEL_OPTION) displaying = false;
        }
    }

    protected void settingsMenu() {
        JOptionPane.showMessageDialog(null, "Settings not yet available", "Settings", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
