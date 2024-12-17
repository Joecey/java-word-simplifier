package ie.atu.sw;

import java.util.List;

public class CosineImpl implements ISimilarityCalculation {
    public CosineImpl() {}

    /**
     * This method calculateSimilarity will be in O(n) time, where n is the length
     * of the targetWeights/testWeights as it has to traverse the entire array
     * in order to get a similarityScore calculation
     */
    @Override
    public double calculateSimilarity(List<Double> targetWeights, List<Double> testWeights) {
        // dot product of vector with itself == vector magnitude squared

        DotImpl dotProductInstance = new DotImpl();

        double magnitudeTargetSquared = dotProductInstance.calculateSimilarity(targetWeights, targetWeights);
        double magnitudeTestSquared = dotProductInstance.calculateSimilarity(testWeights, testWeights);
        double dotProduct = dotProductInstance.calculateSimilarity(targetWeights, testWeights);

        // here, the closer to 1, the better the value (i.e. should be okay to assume bigger value the better
        return (dotProduct) / Math.sqrt(magnitudeTargetSquared * magnitudeTestSquared);
    }

    @Override
    public String getName() {
        return "Cosine";
    }
}
