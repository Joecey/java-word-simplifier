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
    private ModelWeights topWords = null;
    private boolean running = true;
    private ISimilarityCalculation similarityAlgorithm = null;

    public CLIMenu() {
    }

    // this method begins a while loop which acts as our CLI Menu to navigate through the program
    public void start() {
        out.println("Starting CLI Menu...");
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                showOptions();
                Scanner choiceScanner = new Scanner(in);
                int choice = choiceScanner.nextInt();
                switch (choice) {
                    case 1 -> chooseSimilarityAlgorithm();
                    case 2 -> chooseWordEmbeddingsPath();
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
        out.println(Colours.ANSI_YELLOW + "6) Enable timer to record amount of time taken to process text");
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


    private void chooseWordEmbeddingsPath() throws Exception {
        // TODO: add input for file path!
        out.println("Please type the location of word embeddings file");
        Scanner wordEmbeddingsScanner = new Scanner(in);
        String wordEmbeddingsPath = wordEmbeddingsScanner.next().toLowerCase();
        try {
            out.println("Creating word embeddings model...");
            this.wordEmbeddingsModel = new ModelWeights(wordEmbeddingsPath);
            out.println(LogLevels.INFO.getMessage() + wordEmbeddingsModel.getWordCount() + " words have been processed");

        } catch (Exception err) {
            throw new Exception(err.getMessage());
        }

    }



    private void chooseTopWordsPath() {
        if (wordEmbeddingsModel == null) {
            out.println("Please choose a weighted model before processing simplified word list");
        } else {
            // TODO: add input for file path!
            out.println("Please type the location of simplified words list text file");
            out.println("Creating top google words model...");
        }
    }


    private enum SetFilePath {
        OUTPUT, INPUT
    }

    private void chooseFilePath(SetFilePath pathToConfigure) {
    }


    private void enableTimer() {
    }


    private void startSimplifying() {
    }


}
