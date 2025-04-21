import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class PerfomanceTester {
    private static final String RESULTS_DIR = "Sem1_Patience_Sort/results";
    private static final String RESULTS_FILE = "Sem1_Patience_Sort/results/performance_results.csv";
    private static final String TEST_DIR = "Sem1_Patience_Sort/test_data";

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        new File(RESULTS_DIR).mkdirs();

        try (FileWriter writer = new FileWriter(RESULTS_FILE)) {
            writer.write("Elements, Time (ns), Iterations\n");

            File testDir = new File(TEST_DIR);
            File[] testFiles = testDir.listFiles();

            if (testFiles == null) {
                System.out.println("No test files found.");
                return;
            }

            Arrays.stream(testFiles)
                    .sorted((f1, f2) ->
                            Integer.parseInt(f1.getName().replaceAll("\\D+", ""))
                                    - Integer.parseInt(f2.getName().replaceAll("\\D+", "")))
                    .forEach(f -> processTestFile(f, writer));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processTestFile(File file, FileWriter writer) {
        try {
            String content = Files.readString(file.toPath());
            int[] array = Arrays.stream(content.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            long totalTime = 0;
            long totalIterations = 0;
            for (int i = 0; i < 10; i++) {
                int[] copy = array.clone();
                long startTime = System.nanoTime();
                PatienceSort.patienceSort(copy);
                totalTime += System.nanoTime() - startTime;
                totalIterations += PatienceSort.getCountOfIterations();
            }

            int size = array.length;
            String resultLine = String.format("%d, %d, %d\n", size, totalTime / 10, totalIterations / 10);

            writer.write(resultLine);

        } catch (Exception exception) {
            System.err.println("Error processing " + file.getName());
        }
    }


}
