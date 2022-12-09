package exercise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CsvFileHandler {
    /*
     * Functions:
     * - read CSV file and split them appropriately
     * - read headers
     * - groups unique data in a column to an output file
     */

    // Constants
    private static final String DIR_PATH = "publishers-output/"; 

    // Variables
    private String inputFile, outputFile;
    private Map<String, List<String>> publishers = new HashMap<>();
    // private Map<String, Integer> publishers = new HashMap<>();

    // Constructor
    public CsvFileHandler(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    // Methods
    public void ViewHeader() {
        /* 
         * Utility function to view headers of given CSV 
         */

        // Open the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;

            line = br.readLine();
            String[] colData = line.split(",");
            for (int i = 0; i < colData.length; i++) {
                System.out.printf("Col %d -> %s%n", i + 1, colData[i]);
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error! File does not exist!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean ReadCSV() {
        /*
         * Read the given CSV file and store appropriate column data into memory
         */

        // Open the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;

            // Get the publisher data from file
            while ((line = br.readLine()) != null) {
                String[] colData = line.split(",");
                // Publisher is always the last column
                String pub = colData[colData.length - 1].strip().toUpperCase();
                String title = colData[1];

                // If publisher is in our list, get its value + 1, else set it to 1
                // int val = publishers.getOrDefault(pub, 0);
                // val++;
                // publishers.put(pub, val);
                List<String> titles = publishers.getOrDefault(pub, new LinkedList<>());
                titles.add(title);
                publishers.put(pub, titles);
            }
            // Done looping the file
            br.close();

            System.out.printf("There are %d unique publishers in the dataset%n", publishers.size());
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            for (String publisher : publishers.keySet()) {
                String toOut = String.format("%s,%d%n", publisher, publishers.get(publisher).size());
                bw.write(toOut);
            }
            bw.flush();
            System.out.println(outputFile + " successfully updated");
            bw.close();

            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Error! File does not exist!");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void WriteOutputGroup() {
        /*
         * Write an output file for each publisher
         */

        // Create output directory if it doesn't exist yet
        File outPath = new File(DIR_PATH);
        if (!outPath.exists()) {
            outPath.mkdir();
        }

        for ( String publisher : publishers.keySet()) {
            String outFile = String.format("%s%s.txt", DIR_PATH, publisher);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

                // List out book titles by each publisher
                for (String title : publishers.get(publisher)) {
                    String text = String.format("%s%n", title);
                    bw.write(text);
                }
                bw.flush();
                bw.close();
            } catch (IOException e) {
            }
        }
        System.out.println("Publisher files updated.");
    }
}
