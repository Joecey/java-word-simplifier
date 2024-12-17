package ie.atu.sw;



import java.io.*;
import java.util.*;

/**
 * Purpose of this class is to take a 50d file input and convert it into a ModelWeights class
 *
 */
public class ModelWeights implements KeyStringMap<List<Double>>{
    private final Map<String, List<Double>> weightsMap = new HashMap<String, List<Double>>();
    private int wordCount;

    public ModelWeights(String filePath) throws Exception {
        File wordFile = new File(filePath);
        if (!wordFile.exists()) throw new Exception(LogLevels.ERROR.getMessage() + "This is not a valid file path");
        try {
            /*
            * In combination, processing the file will is in O(n * m), with n being the amount of words
            * that are in the file, and m being the length of the weights array (so in our case, it will be m = 50)
             */
            processFilePath(wordFile);

        } catch (Exception err) {
            System.out.println(LogLevels.ERROR.getMessage() + "An error has occurred - " + err.getMessage());
        }
    }

    ///// Getter methods ////
    @Override
    // O(1) since we pre-calculate the word count during the processing step
    public int getWordCount() {
        return wordCount;
    }

    // Lookups in our weightsMap will always be O(1) since we are using a hashMap for key indexing
    @Override
    public Map<String, List<Double>> getWeightsMap() {
        return weightsMap;
    }

    ///// File processing and model creation ////

    /*
     * processFilePath will be in O(n) time, where n is the number of lines in the given file
     */
    private void processFilePath(File modelFile) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(modelFile)));
            String currentLine;
            int count = 0;
            System.out.println("Loading dataset... This may take some time");
            while ((currentLine = br.readLine()) != null) {
                processLine(currentLine);
                count++;
            }
            br.close();

            this.wordCount = count;
        } catch (Exception err) {
            throw new Exception(LogLevels.ERROR.getMessage() + "There was an issue processing the file" + err);
        }
    }

    // here for each line, there is a time complexity of O(m - 1), with m being the length of the trimmedSplit
    // We say -1 here as we do not need the first index of the split
    private void processLine(String currentLine) throws Exception {
        try {
            String trimmed = currentLine.replaceAll("\\s+", "");
            String[] trimmedSplit = trimmed.split(",");

            // throw an error in the event that the text file does not have corresponding models
            if (trimmedSplit.length == 1) throw new Exception("This model is incomplete and possibly has no weights!");

            String currentWord = trimmedSplit[0];
            List<Double> currentWeights = new ArrayList<Double>();

            // for each value in the trimmed split, we  convert to a double and add to currentWeights
            for (int weightIndex = 1; weightIndex < trimmedSplit.length; weightIndex++) {
                String stringDouble = trimmedSplit[weightIndex] + 'd';
                currentWeights.add(Double.parseDouble(stringDouble));
            }

            // then we set the currentWord as our new key, and our array of doubles as our value
            weightsMap.put(currentWord, currentWeights);

        } catch (Exception err) {
            throw new Exception(err);
        }


    }
}
