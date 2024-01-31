package es.ulpgc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Word implements Serializable {
    private String word;
    private Map<String, Integer> references;

    public Word(String word) {
        this.word = word;
        this.references = new HashMap<>();
    }

    public void incrementReference(String fileName) {
        references.merge(fileName, 1, Integer::sum);
    }

    public String getWord() {
        return word;
    }

    public Map<String, Integer> getReferences() {
        return references;
    }
}
