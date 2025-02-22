import java.util.Scanner;
import java.io.IOException;

public class LZ77Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        LZ77 lz77 = new LZ77();

        while (true) {
            System.out.println("Choose an option: 1-Compress, 2-Decompress, 3-Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            if (choice == 1) {
                System.out.print("Enter input file path: ");
                String inputFilePath = scanner.nextLine();
                System.out.print("Enter output file path: ");
                String outputFilePath = scanner.nextLine();
                lz77.compress(inputFilePath, outputFilePath);
                System.out.println("Compression complete!");
            } else if (choice == 2) {
                System.out.print("Enter input file path: ");
                String inputFilePath = scanner.nextLine();
                System.out.print("Enter output file path: ");
                String outputFilePath = scanner.nextLine();
                lz77.decompress(inputFilePath, outputFilePath);
                System.out.println("Decompression complete!");
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}
