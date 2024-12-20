package ie.atu.sw;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

public class CLIMenu {
    private String simplifiedTextLocation = "./out.txt";
    private String inputFile = null;

    private KeyStringMap<List<Double>> wordEmbeddingsModel = null;
    private KeyStringMap<List<Double>> topWords = null;
    private boolean running = true;
    private ISimilarityCalculation similarityAlgorithm = null;

    private enum SetFilePath {
        WORD_EMBED, TOP_WORDS, OUTPUT, INPUT
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
                    case 3 -> chooseFilePath(SetFilePath.TOP_WORDS);
                    case 4 -> chooseFilePath(SetFilePath.OUTPUT);
                    case 5 -> chooseFilePath(SetFilePath.INPUT);
                    case 6 -> startSimplifying();
                    case 7 -> {
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
        out.println(Colours.ANSI_GREEN + "2) Choose path for word embeddings " + (this.wordEmbeddingsModel != null
                ? Colours.ANSI_GREEN + "READY" : Colours.ANSI_RED + "NOT READY"));
        out.println(Colours.ANSI_GREEN + "3) Choose path for top 1000 google words " + (this.topWords != null
                ? Colours.ANSI_GREEN + "READY" : Colours.ANSI_RED + "NOT READY"));
        out.println(Colours.ANSI_PURPLE + "4) Choose output path for simplified text. " + (this.simplifiedTextLocation != null
                ? String.format("Currently: %s", this.simplifiedTextLocation) : ""));
        out.println(Colours.ANSI_PURPLE + "5) Choose input text to be simplified" + (this.inputFile != null
                ? String.format("Currently: %s", this.inputFile) : ""));
        out.println(Colours.ANSI_GREEN + "6) Begin word simplification \uD83D\uDD25 \uD83D\uDD25");
        out.println(Colours.ANSI_RED + "7) Quit" + Colours.ANSI_RESET); // DONE
        out.print("Select an option [1-7]: ");
    }

    /**
     * Choose the similarity algorithm for creating simplified word mappings
     */
    private void chooseSimilarityAlgorithm() {
        out.println("Please type the similarity algorithm you would like to use");
        out.print("(C)osine, (D)ot: ");
        Scanner algoScanner = new Scanner(in);
        String algoChoice = algoScanner.next().toLowerCase();

        switch (algoChoice) {
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

    /**
     * Process the word embeddings file
     *
     * @param filePath the path of the word embeddings file
     * @throws Exception
     */
    private void processWordEmbed(String filePath) throws Exception {
        try {
            out.println("Creating word embeddings model...");
            this.wordEmbeddingsModel = new ModelWeights(filePath);
            out.println(LogLevels.INFO.getMessage() + wordEmbeddingsModel.getWordCount() + " words have been processed");

        } catch (Exception err) {
            throw new Exception(err.getMessage());
        }
    }

    /**
     * Process top words file by searching for existing weight array. Requires wordEmbeddingsModel to be non-null
     *
     * @param inputFile the path of the top words .txt file
     * @throws Exception
     */

    private void processTopWordsPath(String inputFile) throws Exception {
        if (wordEmbeddingsModel == null) {
            out.println("Please choose a weighted model before processing simplified word list");
        } else {
            try {
                out.println("Creating top google words model...");
                this.topWords = new GoogleWeights(inputFile, (ModelWeights) this.wordEmbeddingsModel);
                out.println(LogLevels.INFO.getMessage() + topWords.getWordCount() + " words have been processed");

            } catch (Exception err) {
                throw new Exception(err.getMessage());
            }
        }
    }


    /**
     * General function used to invoke various functions that require a file path input
     *
     * @param pathToConfigure enum value which determines what function uses the path input
     * @throws Exception
     */
    private void chooseFilePath(SetFilePath pathToConfigure) throws Exception {
        String pathChosen = switch (pathToConfigure) {
            case INPUT -> "Input File location";
            case OUTPUT -> "Simplified Output File target location";
            case WORD_EMBED -> "Location of 50d word embeddings file";
            case TOP_WORDS -> "Location of top 1000 google words file";
            case null -> "No method chosen";
        };

        out.printf("Please select file path to configure: %s%n", pathChosen);

        Scanner filePathScanner = new Scanner(in);
        String filePath = filePathScanner.next().toLowerCase();
        final String textExtensionCheck = filePath.endsWith(".txt") ? filePath : filePath + ".txt";

        switch (pathToConfigure) {
            case OUTPUT -> this.simplifiedTextLocation = textExtensionCheck;
            case INPUT -> {
                if (!new File(textExtensionCheck).exists())
                    out.println(LogLevels.ERROR.getMessage() + "This is not a valid input file!");
                else this.inputFile = textExtensionCheck;

            }
            case WORD_EMBED -> processWordEmbed(filePath);
            case TOP_WORDS -> processTopWordsPath(filePath);
            case null -> out.println(LogLevels.WARN.getMessage() + "Method not chosen. Please try again");
        }
    }


    private void startSimplifying() throws Exception {
        if (this.simplifiedTextLocation == null || this.inputFile == null || this.similarityAlgorithm == null ||
                this.topWords == null || this.wordEmbeddingsModel == null) {
            out.println(LogLevels.INFO.getMessage() + "Simplifier is missing configurations. Please try again...");
        }

        // Once all is ready, create a new simplifier class with configurations, and run the actual simplifier
        Simplifier googleTop1000Simplifier = new Simplifier(
                this.simplifiedTextLocation,
                this.inputFile,
                this.similarityAlgorithm,
                this.wordEmbeddingsModel,
                this.topWords);

        googleTop1000Simplifier.startSimplifying();

    }


}
