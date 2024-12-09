package ie.atu.sw;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

public class CLIMenu {
    private String simplifiedTextLocation = "./out.txt";
    private String inputFile = null;

    // TODO: both of these can be created from the ModelWeights class
    private ModelWeights wordEmbeddingsModel = null;
    private ModelWeights googleWords = null;
    private boolean running = true;

    // TODO: create new instance vars
    // Choose model from ModelComparators (which both can be implemented
    // from ISimilarityCalculation

    // CLIMenu constructor
    public CLIMenu() {
    }

    public void start() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        out.println("Starting CLI Menu...");
        do {
            try {
                showOptions();
                Scanner choiceScanner = new Scanner(in);
                int choice = choiceScanner.nextInt();
                switch (choice) {
                    case 1 -> out.println();
                    case 2 -> out.println();
                    case 3 -> out.println();
                    case 4 -> out.println();
                    case 5 -> out.println();
                    case 6 -> out.println();
                    case 7 -> {
                        running = false;
                        out.println("Exiting program...");
                    }
                    default -> out.println("Not an option. Please try again");
                }

            } catch (InputMismatchException err) {
                // This is used to catch potential mismatches in the input
                out.println(LogLevels.WARN.getMessage() + "Input provided is not a valid integer. Please try again");
            }
        } while (running);
    }

    // this method displays all the available actions
    // TODO: make a TreeMap with index keys and store a string and corresponding function as values????
    private void showOptions() {
        out.println();
        out.println(Colours.ANSI_PURPLE + "^^^^\t^^^^\t^^^^\t^^^^\t^^^^\t^^^^");
        out.println("\t\t\tWord Simplifier CLI MENU \uD83D\uDD25 \uD83D\uDD25 \t\t");
        out.println("^^^^\t^^^^\t^^^^\t^^^^\t^^^^\t^^^^");
        out.println(Colours.ANSI_BLUE + "1) Choose Similarity Algorithm");
        out.println(Colours.ANSI_GREEN + "2) Choose path for word embeddings");
        out.println(Colours.ANSI_GREEN + "3) Choose path for simplified word embeddings");
        out.println(Colours.ANSI_PURPLE + "4) Choose output path for simplified text");
        out.println(Colours.ANSI_PURPLE + "5) Choose input path to be simplified");
        out.println(Colours.ANSI_YELLOW + "6) Enable timer to record amount of time taken to process text");
        out.println(Colours.ANSI_RED + "7) Quit" + Colours.ANSI_RESET);
        out.print("Select an option [1-7]: ");
    }
}
