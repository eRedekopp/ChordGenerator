package SwingElements;

import Guitar.*;

import javax.swing.*;
import java.awt.*;

public class ChordChart extends Canvas {

    private ChordVoicing chord;

    private static final int WIDTH = 400, HEIGHT = 400, MARGIN = 20;

    public ChordChart(ChordVoicing chord) {
        this.chord = chord;
    }

    @Override
    public void paint(Graphics g) {
        int marginTop = 3 * MARGIN;
        int lineTopY = marginTop;
        int lineBottomY = HEIGHT - MARGIN;
        int usableWidth = WIDTH - 2 * MARGIN;
        int usableHeight = HEIGHT - MARGIN - marginTop;
        int fretHeight = usableHeight / chord.getGuitarMaxFretSpan();
        int lineSpacing = usableWidth / chord.numberOfStrings();
        int circleRadius = (int) Math.floor((float) lineSpacing / 2.3f);
        int[] frets = chord.getVoicingTab();
        int[] lineXCoords = new int[chord.numberOfStrings()];
        int[] circleYCoords = new int[chord.numberOfStrings()];
        int[] fretYCoords = new int[chord.getGuitarMaxFretSpan()];

        // calculate x coords for each line
        for (int i = MARGIN, j = 0; j < chord.numberOfStrings(); i += lineSpacing, j++)
            lineXCoords[j] = i;
        // calculate coords for each circle centre
        for (int i = 0; i < chord.numberOfStrings(); i++)
            if (frets[i] > 0)
                circleYCoords[i] =
                        marginTop - circleRadius + fretHeight / 2 + (frets[i] - chord.getMinFret()) * fretHeight;
            else circleYCoords[i] = -1;  // set muted or open strings to y coord -1
        // calculate coords for frets
        for (int i = 0; i < chord.getGuitarMaxFretSpan(); i++)
            fretYCoords[i] = marginTop + i * fretHeight;

        // draw string lines
        ((Graphics2D)g).setStroke(new BasicStroke((float)lineSpacing / 12));
        g.setColor(Color.BLACK);
        for (int i = 0; i < chord.numberOfStrings(); i++)
            g.drawLine(lineXCoords[i], lineTopY, lineXCoords[i], lineBottomY);
        // draw fret lines
        for (int i = 0; i < chord.getGuitarMaxFretSpan(); i++)
            g.drawLine(MARGIN, fretYCoords[i], WIDTH - MARGIN - lineSpacing, fretYCoords[i]);
        // draw circles
        g.setColor(Color.RED);
        for (int i = 0; i < chord.numberOfStrings(); i++)
            if (circleYCoords[i] != -1)
                g.fillOval(
                        lineXCoords[i] - circleRadius,
                        circleYCoords[i],
                        circleRadius * 2,
                        circleRadius * 2
                );
        // draw opens/mutes
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, MARGIN));
        for (int i = 0; i < chord.numberOfStrings(); i++)
            if (frets[i] == 0) g.drawString("O", lineXCoords[i], marginTop/2);
            else if (frets[i] == -1) g.drawString("X", lineXCoords[i], marginTop/2);
        // draw fret numbers
        for (int i = 0; i < chord.getGuitarMaxFretSpan(); i++) {
            if ((chord.getMinFret() + i) % 2 == 1)
                g.drawString(Integer.toString(chord.getMinFret() + i), 0, marginTop + fretHeight / 2 + i * fretHeight);
        }
    }

//    public static void main(String[] args) {
//
//        Guitar guitar = new Guitar();
//
//        NoteName[] bigNotes = {NoteName.E, NoteName.G_SHARP};
//        NoteName[] otherNotes = {NoteName.B};
//        // e major cowboy-style
//        Note[] voiceNotes = {new Note(0, 0, guitar), new Note(1, 2, guitar), new Note(2, 2, guitar),
//                             new Note(3, 1, guitar), new Note(4, 0, guitar), new Note(5, 0, guitar)};
//        ChordVoicing voicing = new ChordVoicing(
//                NoteName.E,
//                bigNotes,
//                otherNotes,
//                voiceNotes,
//                guitar
//        );
//
//        JFrame frame = new JFrame();
//        ChordChart c = new ChordChart(voicing);
//
//        frame.add(c);
//        frame.setSize(500, 500);
//
//        frame.setVisible(true);
//    }

}
