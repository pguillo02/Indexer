package es.ulpgc;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.*;
import java.util.Set;

public class TreeIndexer implements Indexer {
    private static HazelcastInstance hazelcastInstance;
    private IMap<String, Set<String>> wordIndexMap;

    public TreeIndexer(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        this.wordIndexMap = hazelcastInstance.getMap("wordMap");
    }

    @Override
    public void indexGenerator() {
        String datalakePath = "Datalake";  // Esto dependerá de donde tengamos los libros
        File datalake = new File(datalakePath);

        if (datalake.exists() && datalake.isDirectory()) {
            exploreDirectory(datalake);

        } else {
            System.out.println("Empty directory");
        }
    }

    @Override
    public String wordBrowser(String word) {
        Set<String> fileNames = wordIndexMap.get(word);

        if (fileNames != null && !fileNames.isEmpty()) {
            StringBuilder result = new StringBuilder("Word: " + word + "\nReferences:\n");

            for (String fileName : fileNames) {
                result.append("- ").append(fileName).append("\n");
            }
            return result.toString();
        } else {
            return "Word not found";
        }

    }

    public void clearData() {
        wordIndexMap.clear();
    }


    private void exploreDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    exploreDirectory(file);
                } else {
                    if (file.getName().endsWith(".txt")) {
                        String fileName = file.getName();
                        new FileIndexer(hazelcastInstance).indexFile(file, fileName);
                    }
                }
            }
        }
    }
}