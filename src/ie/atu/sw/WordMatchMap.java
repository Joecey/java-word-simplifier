package ie.atu.sw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordMatchMap implements KeyStringMap<String> {
    private Map<String, String> weightsMap = new HashMap<String, String>();
    private int wordCount;

    /* Here we want to intialise a new WordMatchMap class where we will iterate through every word in our
    * word embeddings model. For each word we will check if it is already in the topWords model. If it is, we can
    * associate it with itself in the map. If it is not, then we have to perform a comparison between it and every
    * word in topWords, keeping track of the highest score i.e. most similar word. Then, we can map that found word
    * to our new key
    *
    * The time complexity is, at a high level, O(n*m) where n is the amount of keys in wordEmbeddings and m is the
    * amount of keys in topWords. This assumes the worst case scenario where no word in wordEmbeddings is in the
    * topWords model
    * */
    public WordMatchMap(KeyStringMap<List<Double>> wordEmbeddings,
                        KeyStringMap<List<Double>> topWords,
                        ISimilarityCalculation algo) {
        System.out.println(LogLevels.INFO.getMessage() + "Creating Similarity Word Matcher...This may take some time");
        Map<String, List<Double>> wordEmbeddingsModel = wordEmbeddings.getWeightsMap();
        Map<String, List<Double>> topWordsModel = topWords.getWeightsMap();
        System.out.println(wordEmbeddings.getWordCount());
        System.out.println(topWords.getWordCount());
        int count = 0;
        for (String key: wordEmbeddingsModel.keySet()){
            if (topWordsModel.get(key) != null){
                // if word is in topWords
                weightsMap.put(key, key);
            } else {
                // if word is not in topWords - create a new localMax and iterate through each word in topWords
                double localMax = Double.MIN_VALUE;
                String localWord = null;

                for (String topKey: topWordsModel.keySet()){
                    double calculatedSimilarityScore = algo.calculateSimilarity(
                            topWordsModel.get(topKey),
                            wordEmbeddingsModel.get(key));
                    if (calculatedSimilarityScore > localMax){
                        localMax = calculatedSimilarityScore;
                        localWord = topKey;
                    }
                }
                weightsMap.put(key, localWord);
            }
            count++;
        }
        this.wordCount = count;
        System.out.println(LogLevels.INFO.getMessage() + "Word Matcher Created...processed words = " + wordCount);

    }

    @Override
    public Map<String, String> getWeightsMap() {
        return weightsMap;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }
}
