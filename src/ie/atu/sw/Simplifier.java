package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Simplifier {

    private KeyStringMap<String> simplifiedWordMatching;
    private String outputLocation;
    private String inputFileLocation;
    private ISimilarityCalculation algo;

    // Create a new simplifier based on the inputs given by user
    public Simplifier(String outputLocation, String inputFileLocation, ISimilarityCalculation algorithm,
                      KeyStringMap<List<Double>> wordEmbeddings, KeyStringMap<List<Double>> topWords) {

        this.algo = algorithm;
        this.outputLocation = outputLocation;
        this.inputFileLocation = inputFileLocation;

        // Now go through the lengthy process of creating a WordMatchMap for processing
        System.out.println(LogLevels.INFO.getMessage() + "Creating Word map...");
        this.simplifiedWordMatching = new WordMatchMap(wordEmbeddings, topWords, this.algo);
    }

    public void startSimplifying() throws Exception {
        System.out.println(LogLevels.INFO.getMessage() + "Performing simplification to " + outputLocation);

        // used to search for punctuation at the start and end of word
        // We are using a HashSet as searching is done in O(1) time complexity
        Set<String> punctuationSet = new HashSet<String>(
                Arrays.asList(new String[]{"!", "?", ";", ",", "\"", "'", "."}));
        try {

            // NOTE:  max number of characters a FileWriter can contain at once is about 1216
            BufferedWriter out = new BufferedWriter(new FileWriter(outputLocation));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFileLocation))));
            String line;

            // get line and split into an array - if the line is empty then we can skip it
            // for each word - check if [0] index and [-1] index are punctuations? if yes - save them and add back later!

            while((line = br.readLine()) != null){
                out.write(line);
                out.newLine();
            }
            out.newLine();
            out.write(String.format("SIMPLIFIED TEXT WITH %s", this.algo.getName()));
            out.flush();
            out.close();
            br.close();
            System.out.println(LogLevels.INFO.getMessage() + "Simplification completed!");

        } catch (Exception err) {
            throw new Exception(err);
        }

    }


}
