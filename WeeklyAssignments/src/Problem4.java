import java.util.Random;

public class Problem4 {

    // ================= Asset Class =================
    static class Asset {
        String name;
        double returnRate;
        double volatility;

        public Asset(String name, double returnRate, double volatility) {
            this.name = name;
            this.returnRate = returnRate;
            this.volatility = volatility;
        }

        @Override
        public String toString() {
            return name + ":" + returnRate + "% (vol=" + volatility + ")";
        }
    }

    // ================= Merge Sort (Stable) =================
    static class MergeSort {

        public static void mergeSort(Asset[] arr) {
            if (arr.length <= 1) return;
            Asset[] temp = new Asset[arr.length];
            divide(arr, temp, 0, arr.length - 1);
        }

        private static void divide(Asset[] arr, Asset[] temp, int left, int right) {
            if (left >= right) return;

            int mid = (left + right) / 2;
            divide(arr, temp, left, mid);
            divide(arr, temp, mid + 1, right);
            merge(arr, temp, left, mid, right);
        }

        private static void merge(Asset[] arr, Asset[] temp, int left, int mid, int right) {
            int i = left, j = mid + 1, k = left;

            while (i <= mid && j <= right) {
                // Stable: preserves order when equal
                if (arr[i].returnRate <= arr[j].returnRate) {
                    temp[k++] = arr[i++];
                } else {
                    temp[k++] = arr[j++];
                }
            }

            while (i <= mid) temp[k++] = arr[i++];
            while (j <= right) temp[k++] = arr[j++];

            for (int x = left; x <= right; x++) {
                arr[x] = temp[x];
            }
        }
    }

    // ================= Quick Sort =================
    static class QuickSort {

        private static final int INSERTION_SORT_THRESHOLD = 10;
        private static Random rand = new Random();

        public static void quickSort(Asset[] arr) {
            quickSort(arr, 0, arr.length - 1);
        }

        private static void quickSort(Asset[] arr, int low, int high) {
            if (high - low <= INSERTION_SORT_THRESHOLD) {
                insertionSort(arr, low, high);
                return;
            }

            int pivotIndex = medianOfThree(arr, low, high);
            Asset pivot = arr[pivotIndex];

            int i = low, j = high;

            while (i <= j) {
                while (compare(arr[i], pivot) < 0) i++;
                while (compare(arr[j], pivot) > 0) j--;

                if (i <= j) {
                    swap(arr, i, j);
                    i++;
                    j--;
                }
            }

            if (low < j) quickSort(arr, low, j);
            if (i < high) quickSort(arr, i, high);
        }

        // Return DESC, Volatility ASC
        private static int compare(Asset a, Asset b) {
            if (a.returnRate != b.returnRate) {
                return Double.compare(b.returnRate, a.returnRate); // DESC
            }
            return Double.compare(a.volatility, b.volatility); // ASC
        }

        private static int medianOfThree(Asset[] arr, int low, int high) {
            int mid = (low + high) / 2;

            if (compare(arr[low], arr[mid]) > 0) swap(arr, low, mid);
            if (compare(arr[low], arr[high]) > 0) swap(arr, low, high);
            if (compare(arr[mid], arr[high]) > 0) swap(arr, mid, high);

            return mid;
        }

        private static void insertionSort(Asset[] arr, int low, int high) {
            for (int i = low + 1; i <= high; i++) {
                Asset key = arr[i];
                int j = i - 1;

                while (j >= low && compare(arr[j], key) > 0) {
                    arr[j + 1] = arr[j];
                    j--;
                }
                arr[j + 1] = key;
            }
        }

        private static void swap(Asset[] arr, int i, int j) {
            Asset temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // ================= Main =================
    public static void main(String[] args) {

        Asset[] assets = {
                new Asset("AAPL", 12, 5),
                new Asset("TSLA", 8, 9),
                new Asset("GOOG", 15, 4)
        };

        // Merge Sort (Ascending)
        Asset[] mergeArr = assets.clone();
        MergeSort.mergeSort(mergeArr);

        System.out.println("Merge Sort (ASC):");
        for (Asset a : mergeArr) {
            System.out.println(a);
        }

        // Quick Sort (Descending + Volatility ASC)
        Asset[] quickArr = assets.clone();
        QuickSort.quickSort(quickArr);

        System.out.println("\nQuick Sort (DESC + Vol ASC):");
        for (Asset a : quickArr) {
            System.out.println(a);
        }
    }
}
