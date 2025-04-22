import java.util.*;

public class PatienceSort {
    private static int countOfIterations;

    public static void patienceSort(int[] array) {
        countOfIterations = 0;

        if (array == null || array.length <= 1) return;

        List<Stack<Integer>> piles = new ArrayList<>();
        for (int e : array) {
            int index = binarySearch(piles, e);

            if (index == piles.size()) {
                Stack<Integer> newPile = new Stack<>();
                newPile.push(e);
                piles.add(newPile);
            } else {
                piles.get(index).push(e);
            }
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
                (a, b) -> piles.get(a).peek() - piles.get(b).peek()
        );

        for (int i = 0; i < piles.size(); i++) {
            minHeap.offer(i);
            countOfIterations += (int)(Math.log(minHeap.size())/Math.log(2)) + 1;
        }

        int index = 0;
        while (!minHeap.isEmpty()) {
            Integer min = minHeap.poll();
            countOfIterations += (int)(Math.log(minHeap.size()+1)/Math.log(2)) + 1;

            array[index++] = piles.get(min).pop();
            if (!piles.get(min).isEmpty()) {
                minHeap.offer(min);
                countOfIterations += (int)(Math.log(minHeap.size())/Math.log(2)) + 1;
            }
        }
    }

    private static int binarySearch(List<Stack<Integer>> array, Integer value) {
        int l = 0;
        int r = array.size();

        while (l < r) {
            countOfIterations++;

            int m = (l + r) / 2;
            if (value <= array.get(m).peek()) r = m;
            else l = m + 1;
        }

        return l;
    }

    public static int getCountOfIterations() {
        return countOfIterations;
    }
}


