package ie.atu.sw;
import java.util.List;

/**
 * Interface used to create new word similarity strategies.
 * Each class that implements this interface requires a way to calculate the similarity score between
 * two arrays of weights and getName method for easy identifications
 */
public interface ISimilarityCalculation {
    /**
     *
     * @param targetWeights array of double from target word embeddings model
     * @param testWeights array of double from test word embeddings model
     * @return returns similarity score
     */
    public double calculateSimilarity(List<Double> targetWeights, List<Double> testWeights);
    public String getName();
}
