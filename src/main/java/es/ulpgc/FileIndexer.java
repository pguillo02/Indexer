package es.ulpgc;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.*;
import java.util.regex.Pattern;

public class FileIndexer {
    private HazelcastInstance hazelcastInstance;
    private IMap<String, Word> wordMap;

    public FileIndexer(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        this.wordMap = hazelcastInstance.getMap("wordMap");
    }

    public void indexFile(File file, String fileName) {
        String[] words = Utils.fileCleaner_no_metadata(file);

        for (String word : words) {
            word = word.replace(" ", "");

            if ((word.length() >= 3 && !Pattern.matches("^(nul|con|aux|prn).*", word))) {
                routeManager(fileName, word);
            }
        }
    }

    public void routeManager(String fileName, String word) {
        Word cWord = wordMap.get(word);

        if (cWord != null) {
            cWord.incrementReference(fileName);
        } else {
            cWord = new Word(word);
        }

        wordMap.put(word, cWord);
    }
}
