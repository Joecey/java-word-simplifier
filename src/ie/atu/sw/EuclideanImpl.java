package ie.atu.sw;

public class EuclideanImpl implements ISimilarityCalculation {
    public EuclideanImpl() {}

    /*
    * This method calculateSimilarity will be in O(n) time, where n is the length
    * of the targetWeights/testWeights as it has to traverse the entire array
    * in order to get a similarityScore calculation
    */
    @Override
    public double calculateSimilarity(double[] targetWeights, double[] testWeights) {
        double similarityScore = 0.0d;

        for (int weight = 0; weight < targetWeights.length; weight++) {
            similarityScore += Math.pow(targetWeights[weight] - testWeights[weight], 2);
        }
        similarityScore = Math.sqrt(similarityScore);

        // here is a bit different, the smaller the value the better (i.e. vectors are closer together)
        // for this, we can return the negative similarityScore. i.e. larger value will imply shorter distance
        // e.g. raw numbers -> 0.2, 0.9 (0,2 is better)
        // convert to negative -> -0.2, -0.9 (-0.2 is still better)
        return -similarityScore;
    }

    @Override
    public String getName() {
        return "Euclidean";
    }
}
