package milton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName, outputFile;
        Integer wordCount = 0;
        HashMap<String, Integer> wordBagCount = new HashMap<>();

        Integer maxCount = 0;
        List<String> maxWord = new LinkedList<>();

        // Output error message if no argument is given
        if (args.length == 2) {
            fileName = args[0];
            outputFile = args[1];
        } else {
            System.out.println("Error! Invalid input!\n");
            return;
        }

        try {
            // Opens the file
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            System.out.println("Processing " + fileName + " ...\n");

            // FileOutputSteam + DataOutputStream
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);

            String line;
            // Reads the first 100 lines
            for ( int i = 1; i <= 100 ; i++) {
                line = br.readLine();
                // Break out the loop if end of file is reached
                if (line == null) {
                    System.out.println("\n\n======= THE END =======\n");
                    break;
                }
                // System.out.printf("%d.\t%s%n", i, line.trim());

                if (i == 100) {
                    System.out.println("\n\nto be continued...\n");
                }

                String[] words = line.trim().split(" ");
                wordCount += words.length;

                for (String word : words) {
                    word = word.toLowerCase().trim().replaceAll(",", "");
                    if (word.equals("")) continue;
                    // int val = wordBagCount.containsKey(word) ? wordBagCount.get(word) + 1 : 1;
                    int val = wordBagCount.getOrDefault(word, 0) + 1;
                    wordBagCount.put(word, val);

                }
            }
            br.close();

            // 
            // int val = 0;
            bw.write("Word,Count\n");
            for (String word : wordBagCount.keySet()) {
                int count = wordBagCount.get(word);
                String outputLine = String.format("%s,%d%n", word, count);
                bw.write(outputLine);

                // System.out.printf(">> %s -> %d%n", word, count);
                // val += count;

                if (count > maxCount) {
                    maxCount = count;
                    maxWord.clear(); 
                    maxWord.add(word); 
                } else if ( count == maxCount) {
                    maxWord.add(word);
                }
            }
            bw.flush();
            fw.close();

            System.out.println("Total word count is: " + wordCount);
            // System.out.println(val);
            System.out.printf("Most frequent words are %s with a count of %d%n", maxWord, maxCount);
            System.out.println("Number of unique words: " + wordBagCount.size() + "\n");


        } catch (FileNotFoundException e) {
            System.err.println(fileName + " could not be found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
