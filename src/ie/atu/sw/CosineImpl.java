package ie.atu.sw;

public class CosineImpl implements ISimilarityCalculation {
    public CosineImpl() {}

    @Override
    public double calculateSimilarity(double[] targetWeights, double[] testWeights) {
        // dot product of vector with itself == vector magnitude squared

        DotImpl dotProductInstance = new DotImpl();

        double magnitudeTargetSquared = dotProductInstance.calculateSimilarity(targetWeights, targetWeights);
        double magnitudeTestSquared = dotProductInstance.calculateSimilarity(testWeights, testWeights);
        double dotProduct = dotProductInstance.calculateSimilarity(targetWeights, testWeights);

        // here, the closer to 1, the better the value (i.e. should be okay to assume bigger value the better
        return (dotProduct) / Math.sqrt(magnitudeTargetSquared * magnitudeTestSquared);
    }
}
