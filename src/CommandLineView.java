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

        // Todo : finish this
        int[] frets = chordVoicing.getVoicingTab();
        int minFret = min(frets), maxFret = max(frets);
        String[] strings = new String[frets.length];

    }

    private static int min(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Cannot find smallest item in empty array");
        int smallest = Integer.MAX_VALUE;
        for (int i : arr) if (i < smallest) smallest = i;
        return smallest;
    }

    private static int max(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Cannot find largest item in empty array");
        int largest = Integer.MIN_VALUE;
        for (int i : arr) if (i > largest) largest = i;
        return largest;
    }

}
