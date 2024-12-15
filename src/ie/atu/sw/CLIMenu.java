package ie.atu.sw;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

public class CLIMenu {
    private String simplifiedTextLocation = "./out.txt";
    private String inputFile = null;

    private KeyStringMap<List<Double>> wordEmbeddingsModel = null;
    private KeyStringMap<List<String>> topWords = null;
    private boolean running = true;
    private ISimilarityCalculation similarityAlgorithm = null;
    private boolean timerOn = false;

    private enum SetFilePath {
        WORD_EMBED, OUTPUT, INPUT
    }

    public CLIMenu() {
        out.println(LogLevels.INFO.getMessage() + "New Text Simplifier Created");
    }

    // this method begins a while loop which acts as our CLI Menu to navigate through the program
    public void start() {
        out.println(LogLevels.INFO.getMessage() + "Starting CLI Menu...");
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                showOptions();
                Scanner choiceScanner = new Scanner(in);
                int choice = choiceScanner.nextInt();
                switch (choice) {
                    case 1 -> chooseSimilarityAlgorithm();
                    case 2 -> chooseFilePath(SetFilePath.WORD_EMBED);
                    case 3 -> chooseTopWordsPath();
                    case 4 -> chooseFilePath(SetFilePath.OUTPUT);
                    case 5 -> chooseFilePath(SetFilePath.INPUT);
                    case 6 -> enableTimer();
                    case 7 -> startSimplifying();
                    case 8 -> {
                        running = false;
                        out.println(LogLevels.INFO.getMessage() + "Exiting program...");
                    }
                    default -> out.println("Not an option. Please try again");
                }

            } catch (InputMismatchException err) {
                // This is used to catch potential mismatches in the input
                out.println(LogLevels.WARN.getMessage() + "Input provided is not a valid integer. Please try again");
            } catch (Exception err) {
                out.println(err.getMessage());
            }
        } while (running);
    }

    // this method displays all the available actions
    private void showOptions() {
        out.println();
        out.println(Colours.ANSI_PURPLE + "^^^^\t^^^^\t^^^^\t^^^^\t^^^^\t^^^^");
        out.println("\t\t\tWord Simplifier CLI MENU \uD83D\uDD25 \uD83D\uDD25 \t\t");
        out.println("^^^^\t^^^^\t^^^^\t^^^^\t^^^^\t^^^^");
        out.println(Colours.ANSI_BLUE + "1) Choose Similarity Algorithm " + (this.similarityAlgorithm != null
                ? "(currently selected: " + this.similarityAlgorithm.getName() + ")" : "")); // DONE
        out.println(Colours.ANSI_GREEN + "2) Choose path for word embeddings");
        out.println(Colours.ANSI_GREEN + "3) Choose path for top 1000 google words");
        out.println(Colours.ANSI_PURPLE + "4) Choose output path for simplified text");
        out.println(Colours.ANSI_PURPLE + "5) Choose input text to be simplified");
        out.println(Colours.ANSI_YELLOW + "6) Enable timer to record amount of time taken to process text: " +
                (this.timerOn ? String.format("Timer %s ON", Colours.ANSI_GREEN) :
                        String.format("Timer %s OFF", Colours.ANSI_RED)));
        out.println(Colours.ANSI_GREEN + "7) Begin word simplification \uD83D\uDD25 \uD83D\uDD25");
        out.println(Colours.ANSI_RED + "8) Quit" + Colours.ANSI_RESET); // DONE
        out.print("Select an option [1-8]: ");
    }

    private void chooseSimilarityAlgorithm() {
        out.println("Please type the similarity algorithm you would like to use");
        out.print("(C)osine, (D)ot, (E)uclidean: ");
        Scanner algoScanner = new Scanner(in);
        String algoChoice = algoScanner.next().toLowerCase();

        switch (algoChoice) {
            case "euclidean", "e" -> {
                out.println("You have chosen" + Colours.ANSI_YELLOW + " EUCLIDEAN " + Colours.ANSI_RESET);
                this.similarityAlgorithm = new EuclideanImpl();
            }
            case "dot", "d" -> {
                out.println("You have chosen" + Colours.ANSI_CYAN + " DOT PRODUCT " + Colours.ANSI_RESET);
                this.similarityAlgorithm = new DotImpl();
            }
            case "cosine", "c" -> {
                out.println("You have chosen" + Colours.ANSI_RED + " COSINE " + Colours.ANSI_RESET);
                this.similarityAlgorithm = new CosineImpl();
            }
            default -> {
                out.println("Sorry, that is not a valid choice!");
            }
        }
    }

    private void processWordEmbed(String filePath) throws Exception {
        try {
            out.println("Creating word embeddings model...");
            this.wordEmbeddingsModel = new ModelWeights(filePath);
            out.println(LogLevels.INFO.getMessage() + wordEmbeddingsModel.getWordCount() + " words have been processed");

        } catch (Exception err) {
            throw new Exception(err.getMessage());
        }
    }

    private void chooseTopWordsPath() {
        if (wordEmbeddingsModel == null) {
            out.println("Please choose a weighted model before processing simplified word list");
        } else {
            // TODO: create top google words model
            out.println("Please type the location of simplified words list text file");
            out.println("Creating top google words model...");
        }
    }

    private void chooseFilePath(SetFilePath pathToConfigure) throws Exception {
        Scanner filePathScanner = new Scanner(in);
        String filePath = filePathScanner.next().toLowerCase();

        String pathChosen = switch (pathToConfigure) {
            case INPUT -> "Input File location";
            case OUTPUT -> "Simplified Output File target location";
            case WORD_EMBED -> "Location of 50d word embeddings file";
            case null -> "No method chosen";
        };

        out.printf("Please select file path to configure: %s%n", pathChosen);

        switch (pathToConfigure) {
            case INPUT -> this.inputFile = filePath;
            case OUTPUT -> this.simplifiedTextLocation = filePath;
            case WORD_EMBED -> processWordEmbed(filePath);
            case null -> out.println(LogLevels.WARN.getMessage() + "Method not chosen. Please try again");
        }
    }


    private void enableTimer() {
        this.timerOn = !this.timerOn;
    }


    private void startSimplifying() {
        if (this.simplifiedTextLocation == null || this.inputFile == null || this.similarityAlgorithm == null ||
                this.topWords == null || this.wordEmbeddingsModel == null) {
            out.println(LogLevels.INFO.getMessage() + "Simplifier is missing configurations. Please try again...");
        }

        // Once all is ready, create a new simplifier class with configurations, and run the actual simplifier
        Simplifier googleTop1000Simplifier = new Simplifier();

    }


}
