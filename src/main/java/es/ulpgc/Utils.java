package es.ulpgc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String eliminateMetadata(File book){
        try {
            boolean foundFirstOccurrence = false;
            List<String> contentWithoutMetadata = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(book))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("*** START OF ") && !foundFirstOccurrence) {
                        foundFirstOccurrence = true;
                    } else if (foundFirstOccurrence) {
                        if (line.contains("*** END OF ")) {
                            break;
                        }
                        contentWithoutMetadata.add(line);
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            for (String contentLine : contentWithoutMetadata) {
                result.append(contentLine).append("\n");
            }

            return result.toString();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static String readFile(File book) {
        try {
            List<String> content = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(book))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.add(line);
                }
            }

            StringBuilder result = new StringBuilder();
            for (String contentLine : content) {
                result.append(contentLine).append("\n");
            }

            return result.toString();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static String normalize(String book){
        book = book.toLowerCase();
        book = book.replaceAll("[^a-zA-Z]+", " ");
        return book;
    }

    public static String[] fileCleaner(File file){
        String content = eliminateMetadata(file);
        String clean = normalize(content);
        return clean.split(" ");
    }

    public static String[] fileCleaner_no_metadata(File file){
        String content = readFile(file);
        String clean = normalize(content);
        return clean.split(" ");
    }
}

