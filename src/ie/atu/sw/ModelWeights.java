package ie.atu.sw;

/*
Purpose of this class is to take a 50d file input and convert it into
a ModelWeights class
 */

import java.io.*;
import java.util.*;


public class ModelWeights {
    private final Map<String, List<Double>> weightsMap = new HashMap<String, List<Double>>();
    private int wordCount;

    public ModelWeights(String filePath) throws Exception {
        File wordFile = new File(filePath);
        if (!wordFile.exists()) throw new Exception(LogLevels.ERROR.getMessage() + "This is not a valid file path");
        try {
            processFilePath(wordFile);

        } catch (Exception err) {
            System.out.println(LogLevels.ERROR.getMessage() + "An error has occurred - " + err.getMessage());
        }
    }

    ///// Getter methods ////
    public int getWordCount() {
        return wordCount;
    }

    public Map<String, List<Double>> getWeightsMap() {
        return weightsMap;
    }

    ///// File processing and model creation ////
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

    private void processLine(String currentLine) throws Exception {
        try {
            String trimmed = currentLine.replaceAll("\\s+", "");
            String[] trimmedSplit = trimmed.split(",");
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
