import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        // Test ke liye 2 sample log files ke naam
        String[] logFiles = {"log1.txt", "log2.txt"};
        String keyword = "ERROR"; 
        createDummyFiles(logFiles);

        long totalLinesAllFiles = 0;
        long totalKeywordCount = 0;

        System.out.println("=== Log Analysis Report ===\n");

        for (String fileName : logFiles) {
            Path path = Paths.get(fileName);
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            long fileLineCount = 0;
            long keywordCountInFile = 0;

            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileLineCount++;
                    if (line.contains(keyword)) {
                        keywordCountInFile++;
                    }
                }
            }

            totalLinesAllFiles += fileLineCount;
            totalKeywordCount += keywordCountInFile;

            System.out.println("File: " + fileName);
            System.out.println("  - Size: " + attributes.size() + " bytes");
            System.out.println("  - Total Lines: " + fileLineCount);
            System.out.println("  - Lines containing '" + keyword + "': " + keywordCountInFile);
            System.out.println("-----------------------------------");
        }

        System.out.println("\n=== Summary ===");
        System.out.println("Total lines across all files: " + totalLinesAllFiles);
        System.out.println("Total lines containing '" + keyword + "': " + totalKeywordCount);
    }

    // Helper method to create sample files so you don't have to manually create them
    private static void createDummyFiles(String[] files) throws IOException {
        Files.write(Paths.get(files[0]), List.of(
            "INFO: Application started successfully.",
            "ERROR: Database connection failed.",
            "DEBUG: Checking user session."
        ));
        Files.write(Paths.get(files[1]), List.of(
            "INFO: User logged in.",
            "ERROR: NullPointerException in module X.",
            "ERROR: Timeout while fetching API.",
            "INFO: Application shutting down."
        ));
    }
}