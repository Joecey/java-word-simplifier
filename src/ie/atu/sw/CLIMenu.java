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
                    case 1 -> chooseSimilarityAlgorithm();
                    case 2 -> chooseWordEmbeddingsPath();
                    case 3 -> chooseTopWordsPath();
                    case 4 -> chooseOutputFilePath();
                    case 5 -> chooseFileInputPath();
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
            } catch (Exception err){
                out.println(LogLevels.ERROR.getMessage() + "There was an uncaught error - " + err.getMessage());
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
        out.println(Colours.ANSI_GREEN + "3) Choose path for top 1000 google words");
        out.println(Colours.ANSI_PURPLE + "4) Choose output path for simplified text");
        out.println(Colours.ANSI_PURPLE + "5) Choose input path to be simplified");
        out.println(Colours.ANSI_YELLOW + "6) Enable timer to record amount of time taken to process text");
        out.println(Colours.ANSI_GREEN + "7) Begin word simplification \uD83D\uDD25 \uD83D\uDD25");
        out.println(Colours.ANSI_RED + "8) Quit" + Colours.ANSI_RESET);
        out.print("Select an option [1-8]: ");
    }

    private void chooseSimilarityAlgorithm (){};
    private void chooseWordEmbeddingsPath () throws Exception {
        // TODO: add input for file path!
        out.println("Please type the location of word embeddings file");
        try{
            out.println("Creating word embeddings model...");
            this.wordEmbeddingsModel  = new ModelWeights("./embeddings.txt");
            out.println(LogLevels.INFO.getMessage()+ wordEmbeddingsModel.getWordCount() + " words have been processed");

            // THIS WORKS!
            out.println(this.wordEmbeddingsModel.getWeightsMap().get("riposte"));
        } catch (Exception err){
            out.println("There was an error processing the new word embeddings!");
        }

    };
    private void chooseTopWordsPath (){
        if (wordEmbeddingsModel == null){
            out.println("Please choose a weighted model before processing simplified word list");
        } else{
            // TODO: add input for file path!
            out.println("Please type the location of simplified words list text file");
            out.println("Creating top google words model...");
        }


    };
    private void chooseOutputFilePath (){};
    private void chooseFileInputPath (){};
    private void enableTimer (){};
    private void startSimplifying (){};
}
