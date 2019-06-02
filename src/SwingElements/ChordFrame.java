package SwingElements;

import Guitar.ChordVoicing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChordFrame extends JFrame {

    private int result;
    public static int RESULT_NULL = -1, RESULT_CONTINUE = 0, RESULT_CANCEL = 1;

    private Button nextButton, prevButton, cancelButton;

    private JPanel rootPanel;

    public ChordFrame(ChordChart chart) {
        super("Chord Display");

        this.result = RESULT_NULL;
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(chart.getWidth() + 100, chart.getHeight() + 100);
        this.nextButton = new Button("Next");
        this.cancelButton = new Button("Cancel");
        this.prevButton = new Button("Previous");

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        cancelButton.setPreferredSize(new Dimension(80, 40));
        nextButton  .setPreferredSize(new Dimension(80, 40));
        prevButton  .setPreferredSize(new Dimension(80, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(cancelButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        rootPanel.add(chart);
        rootPanel.add(buttonPanel);

        this.add(rootPanel);
    }

    public void displayChart() {


    }

    public int getResult() {
        return this.result;
    }

    public void setCancelListener(ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }

    public void setNextListener(ActionListener listener) {
        this.nextButton.addActionListener(listener);
    }

    public void setPrevListener(ActionListener listener) {
        this.prevButton.addActionListener(listener);
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
