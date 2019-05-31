package SwingElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChordChartDialog {

    // todo finish this (look into modal dialogs?)

    public static int showDialog(ChordChart chart) {
        return -1;
    }

    private static Panel getPanel(ChordChart chart) {
        Panel panel = new Panel();
        panel.add(chart);
        panel.setVisible(true);
        return panel;
    }

    private class DialogFrame extends JDialog {

        public int RESULT_CONTINUE = 0, RESULT_CANCEL = 1;

        private int returnValue;

        private Button nextButton;
        private Button cancelButton;

        private DialogFrame(ChordChart chart) {
            super();
            this.setSize(chart.getSize());
            this.nextButton = new Button("Next");
            this.cancelButton = new Button("Cancel");
            nextButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    returnValue = RESULT_CONTINUE;
                }
            });
        }

    }
}
