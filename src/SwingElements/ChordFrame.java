package SwingElements;

import Guitar.ChordVoicing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChordFrame extends JFrame {

    private Button nextButton, prevButton, cancelButton;

    public ChordFrame() {
        super("Chord Display");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.nextButton = new Button("Next");
        this.cancelButton = new Button("Cancel");
        this.prevButton = new Button("Previous");
    }

    public ChordFrame(ChordChart chart) {
        super("Chord Display");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(chart.getWidth() + 100, chart.getHeight() + 100);
        this.nextButton = new Button("Next");
        this.cancelButton = new Button("Cancel");
        this.prevButton = new Button("Previous");

        this.displayChart(chart);
    }

    public void displayChart(ChordChart chart) {
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        cancelButton.setPreferredSize(new Dimension(80, 40));
        nextButton  .setPreferredSize(new Dimension(80, 40));
        prevButton  .setPreferredSize(new Dimension(80, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(this.cancelButton);
        buttonPanel.add(this.prevButton);
        buttonPanel.add(this.nextButton);

        rootPanel.add(chart);
        rootPanel.add(buttonPanel);

        this.getContentPane().removeAll();
        this.repaint();
        this.add(rootPanel);
    }

    public void setNextEnabled(boolean b) {
        this.nextButton.setEnabled(b);
    }

    public void setPrevEnabled(boolean b) {
        this.prevButton.setEnabled(b);
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
