package exercise;

public class Main {
    public static void main(String[] args) {
        // Check if a file name is passed to the program
        if (args.length != 2) {
            System.err.println("Error! Invalid argument passed!");
            return;
        }

        // Instantiate the file object to read from it
        CsvFileHandler file = new CsvFileHandler(args[0], args[1]);
        // file.ViewHeader();   // For initial check
        if (file.ReadCSV()) {
            System.out.println(args[0] + " has been loaded successfully!");
        }

        file.WriteOutputGroup();

        System.out.println();
    }

    
}
