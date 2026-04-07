import java.util.Arrays;

class Trade {
    String id;
    int volume;

    Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class Problem3 {

    static void mergeSort(Trade[] trades, int left, int right, Trade[] temp) {
        if (left >= right) return;

        int mid = (left + right) / 2;
        mergeSort(trades, left, mid, temp);
        mergeSort(trades, mid + 1, right, temp);
        merge(trades, left, mid, right, temp);
    }

    static void merge(Trade[] trades, int left, int mid, int right, Trade[] temp) {
        for (int k = left; k <= right; k++) {
            temp[k] = trades[k];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (temp[i].volume <= temp[j].volume) {
                trades[k++] = temp[i++];
            } else {
                trades[k++] = temp[j++];
            }
        }

        while (i <= mid) trades[k++] = temp[i++];
        while (j <= right) trades[k++] = temp[j++];
    }

    static void quickSort(Trade[] trades, int low, int high) {
        if (low >= high) return;

        int pivotIndex = medianOfThree(trades, low, high);
        pivotIndex = lomutoPartition(trades, low, high, pivotIndex);

        quickSort(trades, low, pivotIndex - 1);
        quickSort(trades, pivotIndex + 1, high);
    }

    static int medianOfThree(Trade[] trades, int low, int high) {
        int mid = (low + high) / 2;
        int a = trades[low].volume;
        int b = trades[mid].volume;
        int c = trades[high].volume;

        if ((a <= b && b <= c) || (c <= b && b <= a)) return mid;
        if ((b <= a && a <= c) || (c <= a && a <= b)) return low;
        return high;
    }

    static int lomutoPartition(Trade[] trades, int low, int high, int pivotIndex) {
        Trade pivot = trades[pivotIndex];
        swap(trades, pivotIndex, high);

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (trades[j].volume > pivot.volume) {
                i++;
                swap(trades, i, j);
            }
        }

        swap(trades, i + 1, high);
        return i + 1;
    }

    static void swap(Trade[] trades, int a, int b) {
        Trade temp = trades[a];
        trades[a] = trades[b];
        trades[b] = temp;
    }

    static Trade[] mergeSortedSessions(Trade[] morning, Trade[] afternoon) {
        Trade[] merged = new Trade[morning.length + afternoon.length];
        int i = 0, j = 0, k = 0;

        while (i < morning.length && j < afternoon.length) {
            if (morning[i].volume <= afternoon[j].volume) {
                merged[k++] = morning[i++];
            } else {
                merged[k++] = afternoon[j++];
            }
        }

        while (i < morning.length) merged[k++] = morning[i++];
        while (j < afternoon.length) merged[k++] = afternoon[j++];

        return merged;
    }

    static long computeTotalVolume(Trade[] trades) {
        long total = 0;
        for (Trade t : trades) total += t.volume;
        return total;
    }

    static void printTrades(Trade[] trades) {
        System.out.print("[");
        for (int i = 0; i < trades.length; i++) {
            System.out.print(trades[i]);
            if (i < trades.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    static void printTrades(Trade[] trades, int limit) {
        System.out.print("[");
        for (int i = 0; i < Math.min(limit, trades.length); i++) {
            System.out.print(trades[i]);
            if (i < Math.min(limit, trades.length) - 1) System.out.print(", ");
        }
        if (trades.length > limit) System.out.print(", ...");
        System.out.println("]");
    }

    public static void main(String[] args) {

        System.out.println("===== SAMPLE DEMO (3 Trades) =====");

        Trade[] sample = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        System.out.print("Input:          ");
        printTrades(sample);

        Trade[] mergeSample = Arrays.copyOf(sample, sample.length);
        Trade[] tempMerge = new Trade[mergeSample.length];
        mergeSort(mergeSample, 0, mergeSample.length - 1, tempMerge);
        System.out.print("MergeSort (asc): ");
        printTrades(mergeSample);

        Trade[] quickSample = Arrays.copyOf(sample, sample.length);
        quickSort(quickSample, 0, quickSample.length - 1);
        System.out.print("QuickSort (desc): ");
        printTrades(quickSample);

        System.out.println("Total volume: " + computeTotalVolume(mergeSample));

        System.out.println();
        System.out.println("===== SESSION MERGE DEMO =====");

        Trade[] morning = {
                new Trade("M1", 150),
                new Trade("M2", 300),
                new Trade("M3", 450)
        };

        Trade[] afternoon = {
                new Trade("A1", 200),
                new Trade("A2", 350),
                new Trade("A3", 500)
        };

        System.out.print("Morning session:   ");
        printTrades(morning);
        System.out.print("Afternoon session: ");
        printTrades(afternoon);

        Trade[] fullDay = mergeSortedSessions(morning, afternoon);
        System.out.print("Merged (asc):      ");
        printTrades(fullDay);
        System.out.println("Total volume: " + computeTotalVolume(fullDay));

        System.out.println();
        System.out.println("===== LARGE SCALE SIMULATION (1,000,000 Trades) =====");

        int size = 1_000_000;
        Trade[] largeTrades = new Trade[size];

        for (int i = 0; i < size; i++) {
            int volume = (int)(Math.random() * 1_000_000) + 1;
            largeTrades[i] = new Trade("T" + (i + 1), volume);
        }

        Trade[] mergeInput = Arrays.copyOf(largeTrades, size);
        Trade[] temp = new Trade[size];

        long mergeStart = System.currentTimeMillis();
        mergeSort(mergeInput, 0, size - 1, temp);
        long mergeEnd = System.currentTimeMillis();

        System.out.println("MergeSort (1M trades): " + (mergeEnd - mergeStart) + "ms");
        System.out.print("First 5 (lowest vol):  ");
        printTrades(mergeInput, 5);
        System.out.print("Last 5 (highest vol):  ");
        Trade[] last5 = Arrays.copyOfRange(mergeInput, size - 5, size);
        printTrades(last5);

        Trade[] quickInput = Arrays.copyOf(largeTrades, size);

        long quickStart = System.currentTimeMillis();
        quickSort(quickInput, 0, size - 1);
        long quickEnd = System.currentTimeMillis();

        System.out.println("QuickSort (1M trades): " + (quickEnd - quickStart) + "ms");
        System.out.print("First 5 (highest vol): ");
        printTrades(quickInput, 5);

        long totalVolume = computeTotalVolume(mergeInput);
        System.out.println("Total volume (1M trades): " + totalVolume);

        System.out.println();
        System.out.println("===== STABILITY CHECK (MergeSort) =====");

        Trade[] stabTrades = {
                new Trade("X", 300),
                new Trade("Y", 300),
                new Trade("Z", 300)
        };

        System.out.print("Before: ");
        printTrades(stabTrades);

        Trade[] stabTemp = new Trade[stabTrades.length];
        mergeSort(stabTrades, 0, stabTrades.length - 1, stabTemp);

        System.out.print("After MergeSort (equal volumes, original order preserved): ");
        printTrades(stabTrades);

        System.out.println();
        System.out.println("===== COMPLEXITY SUMMARY =====");
        System.out.println("Algorithm   | Best       | Average    | Worst      | Space     | Stable");
        System.out.println("MergeSort   | O(n log n) | O(n log n) | O(n log n) | O(n)      | Yes");
        System.out.println("QuickSort   | O(n log n) | O(n log n) | O(n^2)     | O(log n)  | No");
    }
}
