import java.io.*;
import java.util.*;

// constructor
abstract class LZ77Compression {


    public abstract void compress(String inputFilePath, String outputFilePath) throws IOException;

    public abstract void decompress(String inputFilePath, String outputFilePath) throws IOException;
}

class LZ77 extends LZ77Compression {

    // search buffer and lookahead buffer
    private static final int SEARCH_BUFFER_SIZE = 256;
    private static final int LOOKAHEAD_BUFFER_SIZE = 15; 


    @Override
    public void compress(String inputFilePath, String outputFilePath) throws IOException {
        File file = new File(inputFilePath);
    
        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("File does not exist or is empty.");
            return;
        }
    
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
    
        StringBuilder textBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            textBuilder.append(line).append("\n"); // Preserve spaces and newlines
        }
        reader.close();
    
        // Convert the StringBuilder to a String and trim any trailing whitespace
        String text = textBuilder.toString().trim();
        
        // Check if the text is empty after trimming
        if (text.isEmpty()) {
            System.out.println("The input file is empty.");
            return;
        }
    
        List<String> tags = new ArrayList<>();
        int i = 0;
        
        // Main iteration through the input text
        while (i < text.length()) {

            // sliding window
            int searchStart = Math.max(0, i - SEARCH_BUFFER_SIZE); 
            String searchBuffer = text.substring(searchStart, i);
            int matchLength = 0, matchPosition = 0;
    
            // Iterate through the lookahead buffer to find the longest match
            for (int j = 1; j <= LOOKAHEAD_BUFFER_SIZE && i + j <= text.length(); j++) {

                String pattern = text.substring(i, i + j);
                int position = searchBuffer.lastIndexOf(pattern);
    
                // If a match is found, update the match length and position
                if (position != -1) {
                    matchLength = j;
                    matchPosition = i - (searchStart + position);

                } else {
                    break;
                }
            }
            char nextChar = '\0';

            if (i + matchLength < text.length()){
                nextChar = text.charAt(i + matchLength);
            }else{nextChar = '\0';}
            
            tags.add(String.format("(%d,%d,%s)", matchPosition, matchLength, nextChar == '\n' ? "\\n" : Character.toString(nextChar)));
            
            i += matchLength + 1;
        }
    
        for (String tag : tags) {
            writer.write(tag + " ");
        }
        writer.close();
    }
    
    @Override
    public void decompress(String inputFilePath, String outputFilePath) throws IOException {
        File file = new File(inputFilePath);
    
        if (!file.exists() || file.length() == 0) {
            System.out.println("Compressed file does not exist or is empty.");
            return;
        }
    
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
    
        StringBuilder compressedData = new StringBuilder();
        String line;
    
        while ((line = reader.readLine()) != null) {
            compressedData.append(line).append(" ");
        }
        reader.close();
    
        String[] tags = compressedData.toString().trim().split(" ");
        StringBuilder output = new StringBuilder();
    
        // Process each tag to reconstruct the original text
        for (String tag : tags) {
            // Remove parentheses from the tag
            tag = tag.replaceAll("[()]", "");
            String[] parts = tag.split(",");
    
            // Skip malformed tags
            if (parts.length < 2) {
                System.out.println("Skipping malformed tag" + tag);
                continue;
            }
    
            int position = Integer.parseInt(parts[0]);
            int length = Integer.parseInt(parts[1]);
            String nextCharString = (parts.length == 3) ? parts[2] : "";
    
            // Handle newline characters and null characters
            char nextChar = nextCharString.equals("\\n") ? '\n' : (nextCharString.isEmpty() ? '\0' : nextCharString.charAt(0));
    
            if (position > output.length()) {
                System.out.println("Skipping invalid reference in tag" + tag);
                continue;
            }
    
            int start = output.length() - position;
            for (int j = 0; j < length; j++) {
                output.append(output.charAt(start + j));
            }
    

            if (nextChar != '\0') {
                output.append(nextChar);
            }
        }
    
        writer.write(output.toString());
        writer.close();
    }
}