import Guitar.ChordVoicing;

/**
 * A view class that outputs to the command line
 */
public class CommandLineView {

    private Model model;

    public CommandLineView() {
        this.model = new Model();
    }

    public void displayChordVoicing(ChordVoicing chordVoicing) {
        System.out.println(chordVoicing.toString());
    }


}
