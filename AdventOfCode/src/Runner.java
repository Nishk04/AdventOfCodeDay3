import java.io.*;
import java.util.regex.*;

public class Runner {

    public static void main(String[] args) {
        // Path to the text file containing corrupted memory
        String filePath = "input.txt"; // Ensure to replace this path with the correct one

        // Read the file and process it
        String fileContent = readFile(filePath);

        // Now, find and sum the mul(X,Y) results considering do() and don't() instructions
        int sum = processMulInstructionsWithControl(fileContent);
        System.out.println("Sum of valid multiplications with control: " + sum);
    }

    // Reads the entire content of the file into a String
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // Processes the content and calculates the sum of valid multiplications considering the do() and don't() instructions
    public static int processMulInstructionsWithControl(String content) {
        int sum = 0;
        boolean mulEnabled = true; // Initially, mul instructions are enabled

        // Regex patterns to match valid mul(X,Y), do(), and don't() instructions
        Pattern mulPattern = Pattern.compile("mul\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)");
        Pattern doPattern = Pattern.compile("do\\s*\\(\\s*\\)"); // Matches do() with optional spaces
        Pattern dontPattern = Pattern.compile("don\\'t\\s*\\(\\s*\\)"); // Matches don't() with optional spaces

        // Matcher to find all matches in the content
        Matcher matcher = Pattern.compile(mulPattern.pattern() + "|" + doPattern.pattern() + "|" + dontPattern.pattern()).matcher(content);

        // Iterate over all the matches found
        while (matcher.find()) {
            if (matcher.group(1) != null) { // Found a mul instruction
                if (mulEnabled) { // Only process if mul is enabled
                    int x = Integer.parseInt(matcher.group(1)); // First number
                    int y = Integer.parseInt(matcher.group(2)); // Second number
                    sum += x * y; // Add the multiplication result to the sum
                }
            } else if (matcher.group(0).matches(doPattern.pattern())) { // Found a do() instruction
                mulEnabled = true; // Enable mul instructions
            } else if (matcher.group(0).matches(dontPattern.pattern())) { // Found a don't() instruction
                mulEnabled = false; // Disable mul instructions
            }
        }

        return sum;
    }
}
