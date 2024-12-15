package ie.atu.sw;

import java.util.*;

public class Simplifier {

    private KeyStringMap<String> simplifiedWordMatching;
    private String outputLocation;
    private String inputFileLocation;

    // Create a new simplifier based on the inputs given by user
    public Simplifier(String outputLocation, String inputFileLocation, ISimilarityCalculation algorithm,
                      KeyStringMap<List<Double>> wordEmbeddings, KeyStringMap<List<Double>> topWords){

        this.outputLocation = outputLocation;
        this.inputFileLocation = inputFileLocation;

        // Now go through the lengthy process of creating a WordMatchMap for processing
        this.simplifiedWordMatching = new WordMatchMap(wordEmbeddings, topWords, algorithm);
    }

    public void startSimplifying() {
        // used to search for punctuation at the start and end of word
        // We are using a HashSet as searching is done in O(1) time complexity
        Set<String> punctuationSet = new HashSet<String>(
                Arrays.asList(new String[]{"!", "?", ";", ",", "\"", "'", "."}));

        // TODO: create one to one mapping
        // TODO: for each word - check if [0] index and [-1] index are punctuations? if yes - save them and add back later!
        // TODO: Grab each line - create an array - iterate through array - create a new string - place in output file

        System.out.println(LogLevels.INFO.getMessage() + "Simplification completed!");
    }


}
