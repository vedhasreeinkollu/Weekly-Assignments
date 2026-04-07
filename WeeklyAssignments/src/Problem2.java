import java.util.Arrays;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + "(risk=" + riskScore + ", bal=$" + accountBalance + ")";
    }
}

public class Problem2 {

    static int totalSwaps = 0;

    static void bubbleSortAscending(Client[] clients) {
        int n = clients.length;
        totalSwaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    totalSwaps++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    static void insertionSortDescWithBalance(Client[] clients) {
        int n = clients.length;

        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0) {
                boolean shouldShift;

                if (clients[j].riskScore < key.riskScore) {
                    shouldShift = true;
                } else if (clients[j].riskScore == key.riskScore) {
                    shouldShift = clients[j].accountBalance < key.accountBalance;
                } else {
                    shouldShift = false;
                }

                if (shouldShift) {
                    clients[j + 1] = clients[j];
                    j--;
                } else {
                    break;
                }
            }

            clients[j + 1] = key;
        }
    }

    static Client[] getTopN(Client[] sortedDescClients, int n) {
        return Arrays.copyOfRange(sortedDescClients, 0, Math.min(n, sortedDescClients.length));
    }

    static void printClients(Client[] clients) {
        for (Client c : clients) {
            System.out.print("[" + c.name + ":score=" + c.riskScore + "] ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        System.out.println("===== SAMPLE DEMO (3 Clients) =====");

        Client[] sample = {
                new Client("clientC", 80, 15000.0),
                new Client("clientA", 20, 5000.0),
                new Client("clientB", 50, 8000.0)
        };

        System.out.print("Input:  ");
        printClients(sample);

        bubbleSortAscending(sample);
        System.out.print("Bubble (asc): ");
        printClients(sample);
        System.out.println("Swaps: " + totalSwaps);

        Client[] sampleDesc = {
                new Client("clientC", 80, 15000.0),
                new Client("clientA", 20, 5000.0),
                new Client("clientB", 50, 8000.0)
        };

        insertionSortDescWithBalance(sampleDesc);
        System.out.print("Insertion (desc): ");
        printClients(sampleDesc);

        Client[] top3 = getTopN(sampleDesc, 3);
        System.out.print("Top 3 risks: ");
        for (Client c : top3) System.out.print(c.name + "(" + c.riskScore + ") ");
        System.out.println();

        System.out.println();
        System.out.println("===== FULL SIMULATION (500 Clients) =====");

        Client[] clients500 = new Client[500];
        String[] names = {"Alice","Bob","Carol","Dave","Eve","Frank","Grace","Hank","Iris","Jack"};

        for (int i = 0; i < 500; i++) {
            String name = "Client_" + (i + 1);
            int risk = (int)(Math.random() * 100) + 1;
            double balance = Math.round(Math.random() * 100000 * 100.0) / 100.0;
            clients500[i] = new Client(name, risk, balance);
        }

        Client[] bubbleInput = Arrays.copyOf(clients500, clients500.length);
        bubbleSortAscending(bubbleInput);
        System.out.println("Bubble Sort (asc) complete. Total swaps: " + totalSwaps);
        System.out.print("First 5 (lowest risk): ");
        for (int i = 0; i < 5; i++) System.out.print(bubbleInput[i].name + ":" + bubbleInput[i].riskScore + " ");
        System.out.println();

        Client[] insertionInput = Arrays.copyOf(clients500, clients500.length);
        insertionSortDescWithBalance(insertionInput);
        System.out.println("Insertion Sort (desc + balance) complete.");
        System.out.print("First 5 (highest risk): ");
        for (int i = 0; i < 5; i++) System.out.print(insertionInput[i].name + ":" + insertionInput[i].riskScore + " ");
        System.out.println();

        Client[] top10 = getTopN(insertionInput, 10);
        System.out.println();
        System.out.println("===== TOP 10 HIGHEST RISK CLIENTS =====");
        for (int i = 0; i < top10.length; i++) {
            System.out.println((i + 1) + ". " + top10[i]);
        }

        System.out.println();
        System.out.println("===== TIE-BREAKING STABILITY CHECK =====");

        Client[] tieClients = {
                new Client("X", 75, 20000.0),
                new Client("Y", 75, 50000.0),
                new Client("Z", 75, 35000.0)
        };

        System.out.print("Before: ");
        printClients(tieClients);

        insertionSortDescWithBalance(tieClients);

        System.out.print("After (desc risk + desc balance): ");
        printClients(tieClients);

        System.out.println();
        System.out.println("===== COMPLEXITY SUMMARY =====");
        System.out.println("Algorithm       | Best   | Worst  | Space | Stable");
        System.out.println("Bubble Sort     | O(n)   | O(n^2) | O(1)  | Yes");
        System.out.println("Insertion Sort  | O(n)   | O(n^2) | O(1)  | Yes");
    }
}