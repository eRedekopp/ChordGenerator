package SwingElements;

public class ChordChartDialog {

    public static int showDialog(ChordChart chart) {
        ChordFrame cFrame = new ChordFrame(chart);
        while (cFrame.getResult() == ChordFrame.RESULT_NULL)
            cFrame.setVisible(true);
        return cFrame.getResult();
    }

}
