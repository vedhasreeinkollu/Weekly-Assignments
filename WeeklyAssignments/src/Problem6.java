import java.util.Arrays;

public class Problem6 {

    // ================= Linear Search =================
    static void linearSearch(int[] arr, int target) {
        int comparisons = 0;
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear Search: Found at index " + i);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Linear Search: Not found");
        }

        System.out.println("Comparisons: " + comparisons);
        System.out.println("Time Complexity: O(n)\n");
    }

    // ================= Binary Search: Insertion Point =================
    static int binaryInsertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                System.out.println("Binary: Exact match at index " + mid);
                System.out.println("Comparisons: " + comparisons);
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary: Not found, insertion index = " + low);
        System.out.println("Comparisons: " + comparisons);
        return low;
    }

    // ================= Floor (largest <= target) =================
    static Integer floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        Integer floorVal = null;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] < target) {
                floorVal = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return floorVal;
    }

    // ================= Ceiling (smallest >= target) =================
    static Integer ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        Integer ceilVal = null;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                ceilVal = arr[mid];
                high = mid - 1;
            }
        }
        return ceilVal;
    }

    // ================= Main =================
    public static void main(String[] args) {

        int[] risks = {10, 25, 50, 100};

        // Unsorted version for linear search
        int[] unsorted = {50, 10, 100, 25};

        int target = 30;

        // Linear Search
        linearSearch(unsorted, target);

        // Sort for Binary Search
        Arrays.sort(risks);
        System.out.println("Sorted Risks: " + Arrays.toString(risks));

        // Binary Search (Insertion Point)
        int index = binaryInsertionPoint(risks, target);

        // Floor & Ceiling
        Integer floorVal = floor(risks, target);
        Integer ceilVal = ceiling(risks, target);

        System.out.println("\nFloor(" + target + "): " + floorVal);
        System.out.println("Ceiling(" + target + "): " + ceilVal);
    }
}