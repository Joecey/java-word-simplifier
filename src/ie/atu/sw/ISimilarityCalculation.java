package ie.atu.sw;

/*
* Interface used to create new word similarity strategies.
* Each class that implements this interface requires a way to calculate the similarity score between
* two arrays of weights and getName method for easy identifications
*/

public interface ISimilarityCalculation {
    public double calculateSimilarity(double[] targetWeights, double[] testWeights);
    public String getName();
}
