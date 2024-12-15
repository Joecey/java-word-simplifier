package ie.atu.sw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordMatchMap implements KeyStringMap<String> {
    private Map<String, String> weightsMap = new HashMap<String, String>();
    private int wordCount;

    public WordMatchMap(KeyStringMap<List<Double>> wordEmbeddings,
                        KeyStringMap<List<Double>> topWords,
                        ISimilarityCalculation algo) {

        for (String key: wordEmbeddings.getWeightsMap().keySet()){

            // TODO: for each word -> check if in 1000? -> if yes, map it to itself -> if not, perform calculation
            // TODO: against every word in 1000 until you find the largest value -> then map result as value
            System.out.println(wordEmbeddings.getWeightsMap().get(key));
        }
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
