package ie.atu.sw;

import java.util.Map;

public interface KeyStringMap<T>{
    // Here, a WeightMaps interface requires a method to access the actual map
    // note that the key is always a string, but our value is a generic type (e.g. string or List<double>)
    public Map<String, T> getWeightsMap();
    public int getWordCount();
}
