import Guitar.ChordVoicing;

/**
 * A view class that outputs to the command line
 */
public class CommandLineView implements ModelListener {

    private Model model;
    private CommandLineController controller;

    public void modelChanged() {

    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setController(CommandLineController controller) {
        this.controller = controller;
    }

    public void displayChordVoicing(ChordVoicing chordVoicing) {
        System.out.println(chordVoicing.toString());
    }
}
