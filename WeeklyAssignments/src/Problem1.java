import java.util.ArrayList;
import java.util.List;

public class Problem1 {
    static class Transaction {
        String id;
        double fee;
        String timestamp;

        Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return id + ":$" + fee + "@" + timestamp;
        }
    }

    static int bubbleSortPasses = 0;
    static int bubbleSortSwaps = 0;

    static void bubbleSort(ArrayList<Transaction> transactions) {
        int n = transactions.size();
        bubbleSortPasses = 0;
        bubbleSortSwaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            bubbleSortPasses++;

            for (int j = 0; j < n - i - 1; j++) {
                if (transactions.get(j).fee > transactions.get(j + 1).fee) {
                    Transaction temp = transactions.get(j);
                    transactions.set(j, transactions.get(j + 1));
                    transactions.set(j + 1, temp);
                    bubbleSortSwaps++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    static void insertionSort(ArrayList<Transaction> transactions) {
        int n = transactions.size();

        for (int i = 1; i < n; i++) {
            Transaction key = transactions.get(i);
            int j = i - 1;

            while (j >= 0) {
                Transaction current = transactions.get(j);
                boolean shouldShift;

                if (current.fee > key.fee) {
                    shouldShift = true;
                } else if (current.fee == key.fee) {
                    shouldShift = current.timestamp.compareTo(key.timestamp) > 0;
                } else {
                    shouldShift = false;
                }

                if (shouldShift) {
                    transactions.set(j + 1, current);
                    j--;
                } else {
                    break;
                }
            }

            transactions.set(j + 1, key);
        }
    }

    static List<Transaction> flagOutliers(ArrayList<Transaction> transactions, double threshold) {
        List<Transaction> outliers = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.fee > threshold) {
                outliers.add(t);
            }
        }
        return outliers;
    }

    static void sortByBatch(ArrayList<Transaction> transactions) {
        int size = transactions.size();
        if (size <= 100) {
            bubbleSort(transactions);
            System.out.println("Used Bubble Sort (batch size " + size + " <= 100)");
        } else if (size <= 1000) {
            insertionSort(transactions);
            System.out.println("Used Insertion Sort (batch size " + size + " in range 100-1000)");
        } else {
            System.out.println("Batch size " + size + " exceeds 1000. Use an advanced algorithm.");
        }
    }

    public static void main(String[] args) {

        System.out.println("===== BUBBLE SORT DEMO (Small Batch) =====");

        ArrayList<Transaction> smallBatch = new ArrayList<>();
        smallBatch.add(new Transaction("id1", 10.5, "10:00"));
        smallBatch.add(new Transaction("id2", 25.0, "09:30"));
        smallBatch.add(new Transaction("id3", 5.0, "10:15"));

        System.out.print("Before: ");
        for (Transaction t : smallBatch) System.out.print(t + " ");
        System.out.println();

        bubbleSort(smallBatch);

        System.out.print("After BubbleSort: ");
        for (Transaction t : smallBatch) System.out.print(t + " ");
        System.out.println();
        System.out.println("Passes: " + bubbleSortPasses + ", Swaps: " + bubbleSortSwaps);

        List<Transaction> outliers1 = flagOutliers(smallBatch, 50.0);
        System.out.println("High-fee outliers (>$50): " + (outliers1.isEmpty() ? "none" : outliers1));

        System.out.println();
        System.out.println("===== INSERTION SORT DEMO (Medium Batch) =====");

        ArrayList<Transaction> mediumBatch = new ArrayList<>();
        mediumBatch.add(new Transaction("id1", 10.5, "10:00"));
        mediumBatch.add(new Transaction("id2", 25.0, "09:30"));
        mediumBatch.add(new Transaction("id3", 5.0, "10:15"));
        mediumBatch.add(new Transaction("id4", 25.0, "08:45"));
        mediumBatch.add(new Transaction("id5", 75.0, "11:00"));
        mediumBatch.add(new Transaction("id6", 5.0,  "07:30"));

        System.out.print("Before: ");
        for (Transaction t : mediumBatch) System.out.print(t + " ");
        System.out.println();

        insertionSort(mediumBatch);

        System.out.print("After InsertionSort (fee+ts): ");
        for (Transaction t : mediumBatch) System.out.print(t + " ");
        System.out.println();

        List<Transaction> outliers2 = flagOutliers(mediumBatch, 50.0);
        System.out.println("High-fee outliers (>$50): " + outliers2);

        System.out.println();
        System.out.println("===== DUPLICATE STABILITY CHECK =====");

        ArrayList<Transaction> dupBatch = new ArrayList<>();
        dupBatch.add(new Transaction("A", 15.0, "09:00"));
        dupBatch.add(new Transaction("B", 15.0, "08:00"));
        dupBatch.add(new Transaction("C", 15.0, "10:00"));

        insertionSort(dupBatch);

        System.out.print("Stable order for equal fees: ");
        for (Transaction t : dupBatch) System.out.print(t + " ");
        System.out.println();

        System.out.println();
        System.out.println("===== AUTO BATCH ROUTING =====");

        ArrayList<Transaction> autoBatch = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            autoBatch.add(new Transaction("tx" + i, Math.round(Math.random() * 100 * 10) / 10.0, "09:" + String.format("%02d", i % 60)));
        }

        sortByBatch(autoBatch);

        List<Transaction> autoOutliers = flagOutliers(autoBatch, 50.0);
        System.out.println("High-fee outliers (>$50): " + autoOutliers.size() + " found");

        System.out.println();
        System.out.println("===== COMPLEXITY SUMMARY =====");
        System.out.println("Bubble Sort  | Best: O(n)  | Worst: O(n^2) | Space: O(1) | Stable: Yes");
        System.out.println("Insertion Sort | Best: O(n)  | Worst: O(n^2) | Space: O(1) | Stable: Yes");
    }
}

