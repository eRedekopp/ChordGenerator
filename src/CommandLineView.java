import Guitar.*;

import java.util.Scanner;

/**
 * A view class that outputs to the command line
 */
public class CommandLineView implements ModelListener {

    private Model model;

    public void modelChanged() {
        switch (model.getMode()) {
            case GET:
                model.setVoicings(this.getChordFromUser());
                break;
            case DISPLAY:
                this.displayChordVoicings(model.getVoicings());
                break;
        }
    }

    public void setModel(Model model) {
        this.model = model;
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
