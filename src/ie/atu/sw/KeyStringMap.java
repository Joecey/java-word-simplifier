package ie.atu.sw;

import java.util.Map;

/**
 *  Here, a WeightMaps interface requires a method to access the actual map
 *  Note that the key is always a string, but our value is a generic type (e.g. string or List<double>)
 * @param <T> Value type to map to String key
 */
public interface KeyStringMap<T>{

    public Map<String, T> getWeightsMap();
    public int getWordCount();
}
