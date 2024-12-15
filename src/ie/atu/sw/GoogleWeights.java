package ie.atu.sw;

import java.util.List;
import java.util.Map;

/*
* Here, we want to go through every word in the top 1000 words list and find
* the corresponding weight array from our ModelWeights class in order to
* create a new map of words which will be used later on
 */

public class GoogleWeights implements KeyStringMap<List<Double>>{


    @Override
    public Map<String, List<Double>> getWeightsMap() {
        return null;
    }

    @Override
    public int getWordCount() {
        return 0;
    }
}
