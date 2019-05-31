package SwingElements;

import Guitar.ChordVoicing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChordFrame extends JFrame {

    private int result;
    public static int RESULT_NULL = -1, RESULT_CONTINUE = 0, RESULT_CANCEL = 1;

    public ChordFrame(ChordChart chart) {
        super("Chord Display");

        this.result = RESULT_NULL;
        this.setSize(500, 500);
//        this.setSize(chart.getWidth(), chart.getHeight() + 100);
        // todo get it to size everything properly ( setBounds() ? )
        Button nextButton = new Button("Next");
        Button cancelButton = new Button("Cancel");

        JPanel panel = new JPanel();
        panel.setLayout(null);

        nextButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = RESULT_CONTINUE;
            }
        });
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = RESULT_CANCEL;
            }
        });

        cancelButton.setBounds(20, chart.getHeight() + 20, 80, 20);
        nextButton.setBounds(120, chart.getHeight() + 20, 80, 20);

//        panel.add(chart);
//        panel.add(cancelButton);
//        panel.add(nextButton);

        this.add(chart);

    }

    public int getResult() {
        return this.result;
    }

    public static void main(String[] args) {
        ChordChart cc = new ChordChart(ChordVoicing.E_COWBOY);
        ChordFrame c = new ChordFrame(cc);
        c.setVisible(true);

//        Frame f = new Frame();
//        f.add(cc);
//        f.setVisible(true);
    }
}
