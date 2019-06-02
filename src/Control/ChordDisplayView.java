package Control;

import SwingElements.ChordChart;
import SwingElements.ChordFrame;

public class ChordDisplayView implements ModelListener {

    private Model model;

    ChordFrame chordFrame = new ChordFrame();

    public void modelChanged() {
        chordFrame.setVisible(model.getMode() == Mode.DISPLAY);
        chordFrame.setNextEnabled(! model.cursorAtLastChord());
        chordFrame.setPrevEnabled(! model.cursorAtFirstChord());
        chordFrame.displayChart(new ChordChart(model.getCurrentChord()));
    }

}
