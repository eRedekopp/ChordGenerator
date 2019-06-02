package Control;

public class ChordDisplayController {

    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public void handleNextClick() {
        model.goNextChord();
    }

    public void handlePrevClick() {
        model.goPrevChord();
    }

    public void handleCancelClick() {
        model.setMode(Mode.GET_MODE);
    }
}
