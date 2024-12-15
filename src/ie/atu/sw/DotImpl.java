package ie.atu.sw;

public class DotImpl implements ISimilarityCalculation {
    public DotImpl() {}

    /*
     * This method calculateSimilarity will be in O(n) time, where n is the length
     * of the targetWeights/testWeights as it has to traverse the entire array
     * in order to get a similarityScore calculation
     */
    @Override
    public double calculateSimilarity(double[] targetWeights, double[] testWeights) {
        /*
         * if perpendicular = value is 0 meaning zero similarity
         * the largest value = point in same direction
         * the lowest value = points in opposite direction
         * */

        double similarityScore = 0.0d;

        for (int weight = 0; weight < targetWeights.length; weight++) {
            similarityScore += (targetWeights[weight] * testWeights[weight]);
        }

        return similarityScore;
    }

    @Override
    public String getName() {
        return "Dot Product";
    }
}
