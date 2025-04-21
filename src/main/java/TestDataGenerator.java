import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestDataGenerator {
    private static final String TEST_DIR = "Sem1_Patience_Sort/test_data";
    private static final int MIN_SIZE = 100;
    private static final int MAX_SIZE = 10000;
    private static final int TEST_COUNT = 100;
    private static final Random random = new Random();

    public static void main(String[] args) {
        createTestDirectory();
        generateTestFiles();
    }

    private static void createTestDirectory() {
        File dir = new File(TEST_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static void generateTestFiles() {
        for (int i = 0; i < TEST_COUNT; i++) {
            int size = MIN_SIZE + (i * ((MAX_SIZE - MIN_SIZE) / (TEST_COUNT - 1)));
            if (i == TEST_COUNT - 1) {
                size = MAX_SIZE;
            }
            generateTestFile(size);
        }
    }

    private static void generateTestFile(int size) {
        String filename = TEST_DIR + File.separator + "test_" + size + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            for (int j = 0; j < size; j++) {
                writer.write(random.nextInt(-100000, 100000) + (j < size-1 ? " " : ""));
            }
        } catch (IOException e) {
            System.err.println("Error generating " + filename + ": " + e.getMessage());
        }
    }
}