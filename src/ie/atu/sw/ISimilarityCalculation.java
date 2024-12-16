package ie.atu.sw;

/*
* Interface used to create new word similarity strategies.
* Each class that implements this interface requires a way to calculate the similarity score between
* two arrays of weights and getName method for easy identifications
*/

import java.util.List;

public interface ISimilarityCalculation {
    public double calculateSimilarity(List<Double> targetWeights, List<Double> testWeights);
    public String getName();
}
