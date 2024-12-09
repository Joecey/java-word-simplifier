package ie.atu.sw;

import java.util.List;
import java.util.Map;

public class EuclideanImpl implements ISimilarityCalculation {


    public EuclideanImpl() {

    }

    @Override
    public double calculateSimilarity(double[] targetWeights, double[] testWeights) {
        double similarityScore = 0.0d;

        for (int weight = 0; weight < targetWeights.length; weight++) {
            similarityScore += Math.pow(targetWeights[weight] - testWeights[weight], 2);
        }
        similarityScore = Math.sqrt(similarityScore);

        // here is a bit different, the smaller the value the better (i.e. vectors are closer together)
        // for this, we can return the negative similarityScore. i.e. larger value will imply shorter distance
        return -similarityScore;
    }
}
