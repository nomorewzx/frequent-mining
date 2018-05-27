import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPMax;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPMaxNew;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FPMaxTests {

    List<List<Integer>> originalTransactionSet = new ArrayList<>();
    String inputFilePath = "src/test/resources/contextPasquier99.txt";

    @Before
    public void setUp() throws FileNotFoundException {
        File inputFile = new File(inputFilePath);

        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] items = line.split(" ");
            List<Integer> itemParsed = Arrays.stream(items).map(item -> Integer.parseInt(item)).collect(Collectors.toList());
            originalTransactionSet.add(itemParsed);
        }
    }

    @Test
    public void testFPMaxNewEqualsFPMaxWhenScanForOriginalMapSuuport() throws IOException {


        AlgoFPMaxNew newFPMax = new AlgoFPMaxNew();
        Map<Integer, Integer> originalMapSupport = newFPMax.scanDatabaseToDetermineFrequencyOfSingleItems(originalTransactionSet);

        AlgoFPMax algoFPMax = new AlgoFPMax();
        Map<Integer, Integer> oldAlgoOriginalMapSupport = algoFPMax.scanDatabaseToDetermineFrequencyOfSingleItems(inputFilePath);

        assertEquals(oldAlgoOriginalMapSupport, originalMapSupport);
    }

    @Test
    public void testFPMaxNewEqualsFPMaxWhenRunAlgo() throws IOException {
        AlgoFPMaxNew newAlgo = new AlgoFPMaxNew();
        Itemsets patterns = newAlgo.runAlgorithm(originalTransactionSet, null, 0.3);

        AlgoFPMax oldAlgo = new AlgoFPMax();
        Itemsets oldAlgoPatterns = oldAlgo.runAlgorithm(inputFilePath, null, 0.3);

        newAlgo.printStats();
        patterns.printItemsets(newAlgo.getDatabaseSize());

        System.out.println("++++++++++++++++++++++++++++++++++++");

        oldAlgo.printStats();
        oldAlgoPatterns.printItemsets(oldAlgo.getDatabaseSize());
    }

    @Test
    public void testFPMax() throws IOException {
        AlgoFPMax algoFPMax = new AlgoFPMax();
        Itemsets patterns = algoFPMax.runAlgorithm("src/test/resources/contextPasquier99.txt", null, 0.2);
        algoFPMax.printStats();
        patterns.printItemsets(algoFPMax.getDatabaseSize());


    }
}
