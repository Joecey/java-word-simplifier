package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Here, we want to go through every word in the top 1000 words list and find
 * the corresponding weight array from our ModelWeights class in order to
 * create a new map of words which will be used later on
 */
public class GoogleWeights implements KeyStringMap<List<Double>> {
    private int wordCount;
    private final Map<String, List<Double>> weightsMap = new HashMap<String, List<Double>>();

    /**
     * @param filePath - path of top words (should be line-by-line)
     * @param modelWeights - word embeddings model used to search for matching words
     * @throws Exception
     */
    public GoogleWeights(String filePath, ModelWeights modelWeights) throws Exception {
        File wordFile = new File(filePath);
        if (!wordFile.exists()) throw new Exception(LogLevels.ERROR.getMessage() + "This is not a valid file path");
        try {
            processFilePath(wordFile, modelWeights);

        } catch (Exception err) {
            System.out.println(LogLevels.ERROR.getMessage() + "An error has occurred - " + err.getMessage());
        }
    }

    private void processFilePath(File file, ModelWeights modelWeights) throws Exception {
        // O(n) time complexity as we want to go through each line in the file and map a list of doubles to it
        // note: need to account for empty array words - probably will just skip it if needs be?

        Map<String, List<Double>> wordEmbeddingsMap = modelWeights.getWeightsMap();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String currentLine;
            int count = 0;
            int missingWords = 0;
            System.out.println("Finding matching lines...");
            while ((currentLine = br.readLine()) != null) {
                // Need to check if there is only one word in the line - otherwise skip
                String trimmed = currentLine.replaceAll("\\s+", "");
                String[] trimmedSplit = trimmed.split(",");
                if (trimmedSplit.length != 1) {
                    System.out.printf("%s More than 1 word found on line %2d ... skipping... \n",
                            LogLevels.WARN.getMessage(), (count + 1));
                    System.out.println(Arrays.toString(trimmedSplit));
                    continue;
                }

                String newWord = trimmedSplit[0].toLowerCase();
                List<Double> newWordWeights = wordEmbeddingsMap.get(newWord) != null
                        ? wordEmbeddingsMap.get(newWord) : new ArrayList<Double>();
                this.weightsMap.put(newWord, newWordWeights);
                count++;
            }
            br.close();
            System.out.printf("%sMapped %2d out of %2d words in file \n",
                    LogLevels.INFO.getMessage(), (count - missingWords), count);
            this.wordCount = count - missingWords;
        } catch (Exception err) {
            throw new Exception(LogLevels.ERROR.getMessage() + "There was an issue processing the file" + err);
        }

    }

    @Override
    public Map<String, List<Double>> getWeightsMap() {
        return weightsMap;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }
}
