package ie.atu.sw;

/*
Interface used to create new word similarity strategies
*/
import java.util.*;

public interface ISimilarityCalculation {
    public double calculateSimilarity(double[] targetWeights, double[] testWeights);
}
