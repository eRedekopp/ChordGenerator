package Control;

import Guitar.*;

import java.util.Scanner;

/**
 * A view class that outputs to the command line
 */
public class CommandLineView implements ModelListener {

    private Model model;

    private Controller controller;

    public void modelChanged() {
        switch (model.getMode()) {
            case GET_CHORD:
                model.setVoicings(this.getChordFromUser());
                this.displayChordVoicings(model.getVoicings());
                break;
            case GET_MODE:
                model.setMode(this.getModeFromUser());
                break;
            case SETTINGS:
                this.settingsMenu();
                break;
            case DISPLAY:
                this.displayChordVoicings(model.getVoicings());
                break;
            case EXIT:
                controller.quit();
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Mode getModeFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "### Main Menu ###\n" +
                "1) Find Chords\n" +
                "2) Change Settings\n" +
                "3) Exit"
        );
        String input = null;
        int inptInt;
        while (input == null) {
            input = scanner.nextLine();
            try {
                inptInt = Integer.parseInt(input);
            } catch (RuntimeException e) {
                System.out.println("Unable to parse input");
                input = null;
                continue;
            }
            switch (inptInt) {
                case 1: return Mode.GET_CHORD;
                case 2: return Mode.SETTINGS;
                case 3: return Mode.EXIT;
                default: input = null;
            }
        }
        throw new RuntimeException("Unknown error");
    }

    private Chord getChordFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the root of the chord");
        NoteName root = NoteName.A;
        String input;
        boolean inputAcceptable = false;
        while (!inputAcceptable) {
            input = scanner.nextLine();
            switch (input) {
                case "A":
                    inputAcceptable = true;
                    root = NoteName.A;
                    break;
                case "Bb":
                case "A#":
                    inputAcceptable = true;
                    root = NoteName.A_SHARP;
                    break;
                case "Cb":
                case "B":
                    inputAcceptable = true;
                    root = NoteName.B;
                    break;
                case "B#":
                case "C":
                    inputAcceptable = true;
                    root = NoteName.C;
                    break;
                case "Db":
                case "C#":
                    inputAcceptable = true;
                    root = NoteName.C_SHARP;
                    break;
                case "D":
                    inputAcceptable = true;
                    root = NoteName.C_SHARP;
                    break;
                case "Eb":
                case "D#":
                    inputAcceptable = true;
                    root = NoteName.D_SHARP;
                case "Fb":
                case "E":
                    inputAcceptable = true;
                    root = NoteName.E;
                    break;
                case "E#":
                case "F":
                    inputAcceptable = true;
                    root = NoteName.F;
                    break;
                case "Gb":
                case "F#":
                    inputAcceptable = true;
                    root = NoteName.F_SHARP;
                    break;
                case "G":
                    inputAcceptable = true;
                    root = NoteName.G;
                case "Ab":
                case "G#":
                    inputAcceptable = true;
                    root = NoteName.G_SHARP;
                    break;
                default:
                    System.out.println("Unable to parse note name");
            }
        }
        inputAcceptable = false;
        System.out.println("Choose a chord quality:\n1)\tMajor Triad\n2)\tMinor Triad\n" +
                            "3)\tAugmented Triad\n4)\tDiminished Triad");
        String chordQuality = "";
        while (!inputAcceptable) {
            input = scanner.nextLine();
            int inptInt;
            try {
                inptInt = Integer.parseInt(input);
            } catch (RuntimeException e) {
                System.out.println("Unable to parse input");
                continue;
            }
            switch (inptInt) {
                case 1: chordQuality = "major triad"; inputAcceptable = true; break;
                case 2: chordQuality = "minor triad"; inputAcceptable = true; break;
                case 3: chordQuality = "augmented triad"; inputAcceptable = true; break;
                case 4: chordQuality = "diminished triad"; inputAcceptable = true; break;
                default: System.out.println("Unable to parse input");
            }
        }
        return new Chord(root, chordQuality);
    }

    private void settingsMenu() {
        System.out.println("Not Yet Implemented");
    }

    private void displayChordVoicings(ChordVoicing[] voicings) {
        Scanner scanner = new Scanner(System.in);
        for (ChordVoicing v : voicings) {
            System.out.println(v.toString());
            System.out.println("Press ENTER to continue");
            scanner.nextLine();
        }
    }

    private void displayChordVoicing(ChordVoicing chordVoicing) {
        System.out.println(chordVoicing.toString());
    }
}
